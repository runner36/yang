package com.winchannel.base.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseUser;
import com.winchannel.core.importer.ImpInfo;
import com.winchannel.core.importer.Importer;
import com.winchannel.core.importer.ImporterFactory;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.utils.ZipUtils;
import com.winchannel.core.web.StrutsBaseAction;
import com.winchannel.mdm.util.i18n.BeanMessage;

/**
 * @author xianghui
 *
 */
public class ImportAction extends StrutsBaseAction {
	
	public static final String IMP_VIEW = "/commons/import.jsp";
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("save") == null) {
			return new ActionForward(IMP_VIEW);
		}
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		String test = request.getParameter("test");
		String testFile = request.getParameter("testFile");
		String impName = request.getParameter("impName");
		String configFile = impName + ".xml";
		
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String zip = request.getParameter("zip");
		request.setAttribute("zip", zip);
		
		InputStream is = null;
		try {
			Map<String, Object> params = this.initParams(request);
			Importer imp = ImporterFactory.createImporter(configFile, params);
			
			
			if ("1".equals(request.getParameter("template"))) {
				imp.template(response);
				return null;
			}
			
			String file = null;
			if (StringUtils.isNotBlank(testFile)) {
				file = testFile;
			}
			else {
				FormFile ff = (FormFile) form1.get("file");
				if (StringUtils.isBlank(ff.getFileName())) {
					request.setAttribute("mess", "["+BeanMessage.getLocaleMessage("i18n/messages", "common.prompt", request)+"]：<br>--"+BeanMessage.getLocaleMessage("i18n/messages", "common.selectDataFile", request)+"！");
					return new ActionForward(IMP_VIEW);
				}
				
				String filePath = System.getProperty("java.io.tmpdir");
				file = filePath + "/" + impName + ".sub";
				if ("1".equals(zip)) {
					String zipFile = filePath + "/" + impName + ".zip";
					ZipUtils.createFile(ff.getInputStream(), zipFile);
					ZipUtils.unzipFirstEntry(zipFile, file);
				}
				else if("2".equals(zip)){
					String gzFile = filePath + "/" + impName + ".gz";
					ZipUtils.createFile(ff.getInputStream(), gzFile);
					ZipUtils.ungzFirstEntry(gzFile, file);
				}
				else{
					ZipUtils.createFile(ff.getInputStream(), file);
				}
			}
			is = new FileInputStream(file);
			
			
			ImpInfo info = null;
			if ("1".equals(test)) {
				if ("xls".equals(type)) {
					info = imp.test(ImporterFactory.getExcelDataIterator(is));
				}
				else {
					info = imp.test(ImporterFactory.getTextDataIterator(is, type.charAt(0)));
				}
				request.setAttribute("mess", BeanMessage.getLocaleMessage("i18n/messages", "common.testImportSuccess1", request)+ info.getSuccNum() + BeanMessage.getLocaleMessage("i18n/messages", "common.testImportSuccess2", request) + info.getSkipNum() + BeanMessage.getLocaleMessage("i18n/messages", "common.testImportSuccess3", request));
				request.setAttribute("testFile", file);
			}
			else {
				if ("xls".equals(type)) {
					info = imp.imp(ImporterFactory.getExcelDataIterator(is));
				}
				else {
					info = imp.imp(ImporterFactory.getTextDataIterator(is, type.charAt(0)));
				}
				request.setAttribute("mess", BeanMessage.getLocaleMessage("i18n/messages", "common.importSuccess1", request) + info.getSuccNum() + BeanMessage.getLocaleMessage("i18n/messages", "common.importSuccess2", request) + info.getSkipNum() + BeanMessage.getLocaleMessage("i18n/messages", "common.importSuccess3", request));
			}
		}
		catch(Exception e) {
			request.setAttribute("mess", e.getMessage());
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch(IOException e){}
				is = null;
			}
		}
		
		return new ActionForward(IMP_VIEW);
	}
	
	private Map<String, Object> initParams(HttpServletRequest request) {
		BaseUser user = this.getCurrentUser(request);
		BaseEmployee emp = user.getBaseEmployee();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("_user", user);
		params.put("_emp", emp);
		
		Date date = new Date();
		params.put("_currDate", DateUtils.format(date, "yyyy-MM-dd"));
		params.put("_currTime", DateUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
		params.put("_currMillis", date.getTime() + "");
		params.put("_uuid", UUID.randomUUID().toString());
		
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement().toString();
			params.put(name, request.getParameter(name));
		}
		return params;
	}
	
}
