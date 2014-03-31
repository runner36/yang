package com.winchannel.message.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.SpringContext;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.message.model.BaseAuthoritys;
import com.winchannel.message.model.BaseMessages;
import com.winchannel.message.model.BaseMessageOnShows;
import com.winchannel.message.model.BaseFileItem;
import com.winchannel.message.service.BaseMessageManager;

/**
 * 
 * @author caishang
 * 
 */
public class BaseMessageAction extends StrutsEntityAction<BaseMessages, BaseMessageManager> {
	private BaseMessageManager baseMessageManager;

	public void setBaseMessageManager(BaseMessageManager baseMessageManager) {
		this.baseMessageManager = baseMessageManager;
	}

	@Override
	protected void onInitPage(ActionForm form, HttpServletRequest request, Page page) {
//		page.put("$eq_CREATED", this.getCurrentUser(request).getBaseEmployee().getEmpName());
		page.put("sort", "isdel,createdby desc ");
		super.onInitPage(form, request, page);
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseMessages baseMessage) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		request.setAttribute("flag", "0");
		if (baseMessage.getTitle() != null) {
			form1.set("title", baseMessage.getTitle().toString());
		}
		if (baseMessage.getContent() != null) {
			String content = baseMessage.getContent().toString();
			//	String subContent=content.substring(0, 10)+"...";
			//	form1.set("subContent", subContent);
			form1.set("content", content);

		}
		if (baseMessage.getType() != null) {
			form1.set("type", baseMessage.getType());
			request.setAttribute("flag", "1");
			request.setAttribute("type", baseMessage.getType());

		}
		if (baseMessage.getNewId() != null) {
			form1.set("newId", baseMessage.getNewId().toString());
		}
		if (baseMessage.getBaseAuthorities() != null) {
			String[] org = baseMessageManager.getOrgIdByBaseAuthorities(baseMessage.getBaseAuthorities());
			form1.set("orgIds", org[0]);
			form1.set("orgNames", org[1]);
		}
	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseMessages baseMessage) {
		//String [] types=new String []{"gif","jpg","jpeg","png","bmp"};
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		if (StringUtils.isNotBlank(request.getParameter("newId"))) {
			long newId = Long.parseLong(request.getParameter("newId"));
			baseMessage.setUpdated(employee.getEmpName());
			baseMessage.setUpdatedby(new Date());
		} else {
			baseMessage.setCreatedby(new Date());
			baseMessage.setCreated(employee.getEmpName());
		}
		LazyValidatorForm baseMenssageForm = (LazyValidatorForm) form;

		Set<BaseAuthoritys> baseAuthorities = new HashSet<BaseAuthoritys>();
		Set<BaseFileItem> fileItems = new HashSet<BaseFileItem>();
		BaseFileItem fileItem = new BaseFileItem();

		FormFile file = (FormFile) baseMenssageForm.get("filename");
		int fsize = file.getFileSize();
		if (fsize > 3 * 1024 * 1024) {
			throw new BusinessException("上传文件过大，请重新上传");
		} else {
			String s = (String) baseMenssageForm.get("orgIds");
			if (s.length() > 0) {
				String str[] = s.split(",");
				baseAuthorities = new HashSet();
				baseAuthorities = baseMessage.getBaseAuthorities();
				if (!baseAuthorities.isEmpty()) {
					for (Iterator iterator = baseAuthorities.iterator(); iterator.hasNext();) {
						BaseAuthoritys baseAuth = (BaseAuthoritys) iterator.next();
						baseAuth.setFlag((long) 1);
						baseMessageManager.save(baseAuth);
					}
					baseMessage.setBaseAuthorities(null);
				}
				for (int i = 0; i < str.length; i++) {
					BaseAuthoritys authority = new BaseAuthoritys();
					authority.setOrgId(Long.valueOf(str[i]));
					authority.setFlag((long) 0);
					authority.setBaseMessage(baseMessage);
					baseAuthorities.add(authority);
				}
			}
			baseMessage.setBaseAuthorities(baseAuthorities);

			if (StringUtils.isNotBlank(file.getFileName())) {
				InputStream stream = null;
				FileOutputStream bos = null;
				try {
					stream = file.getInputStream();
					Calendar calendar = Calendar.getInstance();
					String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
					String filepath = SpringContext.getRealPath() + "/uploadImg/" + date;

					File f = new File(filepath);
					if (!f.exists()) {
						f.mkdirs();
					}
					String filename = file.getFileName();
//					int pos = filename.lastIndexOf(".");
//					String exFilename = filename.substring(pos + 1, filename.length());
					//	    if(!Arrays.<String>asList(types).contains(exFilename)){
					//	throw new BusinessException("对不起，上传文件的扩展名是不允许的类型！");
					//	}
//					Date da = new Date();
//					String realPath = f.getAbsolutePath() + "/" + da.getTime() + "." + exFilename;
//					int cos = realPath.lastIndexOf("/");
//					String lastName = realPath.substring(cos + 1, realPath.length());

//					int cos=filename.lastIndexOf("/");
//				    String lastName=filename.substring(cos+1, filename.length());
				    
					String realPath = f.getAbsolutePath() +"\\"+ filename;
					
					bos = new FileOutputStream(realPath);
					int byteread = 0;
					byte[] buffer = new byte[8192];
					while ((byteread = stream.read(buffer, 0, 1892)) != -1) {
						bos.write(buffer, 0, byteread);
						bos.flush();
					}

					fileItems = new HashSet();
					fileItems = baseMessage.getFileItems();
					if (!fileItems.isEmpty()) {
						for (Iterator iterator = fileItems.iterator(); iterator.hasNext();) {
							BaseFileItem fiItem = (BaseFileItem) iterator.next();
							fiItem.setFlag((long) 1);
							baseMessageManager.save(fiItem);
						}
						baseMessage.setFileItems(null);
					}
					String lastPath = "uploadImg/" + date + "/" + filename;
					fileItem.setFileurl(lastPath);
					fileItem.setFlag((long) 0);
					fileItems.add(fileItem);
					baseMessage.setFileItems(fileItems);
					fileItem.setBaseMessage(baseMessage);

					stream.close();
					stream = null;
					bos.close();
					bos = null;

				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException("上传文件时发生错误：" + e.getMessage());
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String newId = request.getParameter("newId");
		BaseMessages baseMe = this.entityManager.get(new Long(newId));
		for (Iterator it = baseMe.getFileItems().iterator(); it.hasNext();) {
			BaseFileItem fileItem = (BaseFileItem) it.next();
			String url = fileItem.getFileurl();
			String fileurl = url.replaceAll("/", "\\\\");
			String fileName = SpringContext.getRealPath() + fileurl;

			String[] tmpFileName = fileurl.split("\\\\");
			String name = tmpFileName[tmpFileName.length - 1];

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("GBK"), "ISO-8859-1"));
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setDateHeader("Expires", (System.currentTimeMillis() + 100000));

			OutputStream os = null;
			InputStream is = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			try {
				os = response.getOutputStream();
				is = new BufferedInputStream(new FileInputStream(fileName));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					os.write(buf, 0, readLen);

				}
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		//else
		return null;
	}

	public ActionForward showMess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ECPage page = new ECPage(request, this.getPageName());
		page.put(Page.AUTH_PREFIX, this.getAuthPrefix());
		String subCode = this.getCurrentOrgSubCode(request);
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
//		List<BaseMessageOnShows> list = this.baseMessageManager.getAllBaseMessageOnShow(subCode); // shijingru - 20120203
		List<BaseMessageOnShows> list = this.baseMessageManager.getAllBaseMessageOnShowAll(subCode);
		long empId = employee.getEmpId();
		List<BaseMessageOnShows> item = new ArrayList<BaseMessageOnShows>();//提取公告
		item = this.baseMessageManager.getBaseOpnionByEmpId(empId); //根据用户提取意见
		if (item.size() == 0) { //判断意见模块的内容是否为空
			request.setAttribute("showOpinion", 0);
		} else {
			request.setAttribute("showOpinion", 1);
			request.setAttribute("empId", employee.getEmpId());
			request.setAttribute("item", item);
		}

		request.setAttribute("list", list);
		response.setHeader("Cache-Control", "no-cache");
		return page.initForward(mapping.findForward("ListSp"));

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ECPage page = new ECPage(request);
		String flag = request.getParameter("flag");
		List<BaseMessageOnShows> list = new ArrayList<BaseMessageOnShows>();
//		if ("0".equals(flag)) {
//			String newId = request.getParameter("newId");
//			if (StringUtils.isNotBlank(newId)) {
//				list = this.baseMessageManager.getOnlyNew(Integer.valueOf(newId));
//				request.getSession().setAttribute("list", list);
//				request.getSession().setAttribute("state", 1);
//			} else {
//				String id = request.getParameter("id");
//				list = this.baseMessageManager.getOnlyOpnion(Long.valueOf(id));
//				request.getSession().setAttribute("list", list);
//			}
			
		if("0".equals(flag)){
			  String newId=request.getParameter("newId");
			  if(StringUtils.isNotBlank(newId)){
			   list= this.baseMessageManager.getOnlyNew(Integer.valueOf(newId));
			  request.getSession().setAttribute("list", list);
			  request.getSession().setAttribute("state", 0);
			  }else{
			    String id=request.getParameter("id");
			   list =this.baseMessageManager.getOnlyOpnion(Long.valueOf(id));
			    request.getSession().setAttribute("list", list);
			    request.getSession().setAttribute("state", 1);
			  }			
			  return mapping.findForward("view");
		
		} else if ("1".equals(flag)) {
			String type = request.getParameter("type");
			String subCode = this.getCurrentOrgSubCode(request);
//			list = this.baseMessageManager.getBaseMessageByType(Long.valueOf(type), subCode);
			list = this.baseMessageManager.getBaseMessageByTypeAll(Long.valueOf(type), subCode);
			request.getSession().setAttribute("list", list);
			request.setAttribute("state", 0);
			return mapping.findForward("index");
		} else {
			BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
			list = this.baseMessageManager.getBaseOpnionByEmpId(employee.getEmpId()); //根据用户提取意见
			request.setAttribute("list", list);
			request.setAttribute("state", 1);
			return mapping.findForward("index");
		}

	}
}
