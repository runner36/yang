package com.winchannel.core.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.winchannel.core.bean.FtpBean;
import com.winchannel.core.bean.JdbcBean;
import com.winchannel.core.bean.MailBean;
import com.winchannel.core.importer.ImpConfigurator;
import com.winchannel.core.utils.StringUtils;

public class ReturnDataServiceConfigurator {

	public static final Log log = LogFactory.getLog("ReturnDataServiceConfigurator");
	public static final URL CONF_FILE_PATH = ImpConfigurator.class.getResource("/config/service/service.xml");
	
	private String code;
	//删除历史文件日期间隔
	private String delHistoryDay;
	private List<FilesBuilderInfo> fbi = new ArrayList<FilesBuilderInfo>();
	
	public ReturnDataServiceConfigurator(String code) throws DocumentException {
		init(code);
	}
	
	private void init(String code) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(CONF_FILE_PATH);
		Element sroot = null;
		Iterator<?> ftpIt = doc.getRootElement().elementIterator("job-service");
		//找到code对应的job
		while (ftpIt.hasNext() && (sroot == null || !code.equals(sroot.attributeValue("code")))) {
			sroot = (Element) ftpIt.next();
		}
		if(sroot!=null){
			this.code = sroot.attributeValue("code");
			this.delHistoryDay= sroot.attributeValue("delHistoryDay");
			//找到对应的几种全局任务节点
			Element mailRoot = getElement(sroot,"mail-service");
			Element ftpRoot = getElement(sroot,"ftp-service");
			Element jdbcRoot =getElement(sroot,"jdbc-servcie");
			Iterator<?> fileEl = sroot.elementIterator("files-builder");
			while (fileEl.hasNext()) {
				Element el = (Element) fileEl.next();
				FilesBuilderInfo f = new FilesBuilderInfo();
				//初始化部分节点信息/
				initAttr(el, f);
				//初始化发送方式
				FtpBean ftpbean=this.initFtp(el);
				MailBean mailbean=this.initMail(doc, el);
				JdbcBean jdbcBean=this.initJdbc(el);
				//局部的未设置则初始化全局的
				if(ftpbean==null){
					ftpbean=this.initFtp(ftpRoot);
				}
				if(mailbean==null){
					mailbean=this.initMail(doc, mailRoot);	
				}
				if(jdbcBean==null){
					jdbcBean=this.initJdbc(jdbcRoot);	
				}
				f.setMailBean(mailbean);
				f.setFtpBean(ftpbean);
				f.setJdbcBean(jdbcBean);
				
				/*if(ftpbean==null &&  mailbean==null &&  jdbcBean==null){
					ftpbean=this.initFtp(ftpRoot);
					mailbean=this.initMail(doc, mailRoot);
					jdbcBean=this.initJdbc(jdbcRoot);
				}*/
				//f.setFtpBean(ftpbean);
				//f.setMailBean(mailbean);
				//f.setJdbcBean(jdbcBean);
				fbi.add(f);
			}
		}
	}

	private void initAttr(Element el, FilesBuilderInfo f) {
		f.setType(el.attributeValue("type"));
		f.setReportName(el.attributeValue("report-name"));
		f.setZipFileName(el.attributeValue("zip-name"));
		f.setDelimiter(el.elementText("delimiter"));
		f.setName(el.elementText("name"));
		f.setZipName(el.elementText("zip-name"));
		f.setCreateHeader(Boolean.parseBoolean(el.elementText("create-header")));
		f.setSourceDir(el.elementText("source-dir"));
		f.setTargetDir(el.elementText("target-dir"));
		f.setAfterSql(el.elementText("alter-execute-sql"));
		f.setHeadLine(el.elementText("head-line"));
		f.setTempDir(el.elementText("temp-dir"));
		String fileCode=el.elementText("file-coding".intern());
		
		if(StringUtils.isNotBlank(fileCode)){
			f.setFileCoding(el.elementText("file-coding".intern()));
		}else{
			f.setFileCoding("UTF-8".intern());
		}
		String groupFields = el.elementText("group-fields");
		if (StringUtils.isNotBlank(groupFields)) {
			String[] ss = groupFields.split("\\,");
			int[] ii = new int[ss.length];
			for (int m = 0; m < ii.length; m++) {
				ii[m] = Integer.parseInt(ss[m]);
			}
			f.setGroupFields(ii);
		}
		f.setIgnoreFields(el.elementText("ignore-fields"));
	}

	private Element getElement(Element sroot,String eleStr) {
		Element eleRoot=null;
		Iterator<?> mailEle = sroot.elementIterator(eleStr);
		//如果配置了邮件
		if(mailEle.hasNext()){
			eleRoot=(Element)mailEle.next();
		}
		return eleRoot;
	}

	private JdbcBean initJdbc(Element elt){
		JdbcBean jdbcBean=null;
		if(elt!=null){
			String jdbcBeanId=elt.elementText("jdbc-beanid");
			String insertSql=elt.elementText("insert-sql");
			String maxIdSql=elt.elementText("maxid-sql");
			String idStr=elt.elementText("id-str");
			if(StringUtils.isNotBlank(jdbcBeanId) && StringUtils.isNotBlank(insertSql)){
				jdbcBean=new JdbcBean();
				jdbcBean.setInsertSql(insertSql);
				jdbcBean.setJdbcBeanId(jdbcBeanId);
				jdbcBean.setIdStr(idStr);
				jdbcBean.setMaxIdSql(maxIdSql);
			}
		}
		return jdbcBean;
	}
	private FtpBean initFtp(Element elt){
		FtpBean ftpBean=null;
		if(elt!=null){
			String ftpHost = elt.elementText("ftp-host");
			int ftpPort = StringUtils.isNotBlank(elt.elementText("ftp-port")) ? Integer.parseInt(elt.elementText("ftp-port")) : 21;
			String ftpUserName = elt.elementText("ftp-username");
			String ftpPassWord = elt.elementText("ftp-password");
			if(StringUtils.isNotBlank(ftpHost) && StringUtils.isNotBlank(ftpUserName) && StringUtils.isNotBlank(ftpPassWord)){
				ftpBean=new FtpBean();
				ftpBean.setFtpHost(ftpHost);
				ftpBean.setFtpPort(ftpPort);
				ftpBean.setFtpUserName(ftpUserName);
				ftpBean.setFtpPassWord(ftpPassWord);
			}
		}
		return ftpBean;
	}
	
	private MailBean initMail(Document doc,Element elt) {
		MailBean mailBean=null;
		if(elt!=null)
		{
			try{
				Element mailRoot = null;
				Iterator<?> mailIt = doc.getRootElement().elementIterator("mail-service");
				if(mailIt.hasNext() && mailRoot==null ){
					mailRoot=(Element) mailIt.next();
					String mailHostname = mailRoot.elementText("mail-hostname");
					String mailUserName = mailRoot.elementText("mail-username");
					String  mailPassWord = mailRoot.elementText("mail-password");
					String mailFrom=mailRoot.elementText("mail-from");
					String  mailTitle=elt.elementText("mail-title");
					String  mailAddTo=elt.elementText("mail-addTo");
					List<InternetAddress> mailCcto=getEmailAddress(elt.elementText("mail-ccto"));
					if(StringUtils.isNotBlank(mailHostname) && StringUtils.isNotBlank(mailUserName)
							&& StringUtils.isNotBlank(mailPassWord) && StringUtils.isNotBlank(mailFrom) 
							&& StringUtils.isNotBlank(mailAddTo)){
						mailBean=new MailBean();
						mailBean.setMailHostname(mailHostname);
						mailBean.setMailUserName(mailUserName);
						mailBean.setMailPassWord(mailPassWord);
						mailBean.setMailFrom(mailFrom);
						mailBean.setMailTitle(mailTitle);
						mailBean.setMailAddTo(mailAddTo);
						mailBean.setMailCcto(mailCcto);
					}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mailBean;
	}
	
	 private static List<InternetAddress> getEmailAddress(String toEmailAddress) throws Exception{
			if (toEmailAddress == null || toEmailAddress.length() <= 0) {
				return null;
			}
			List<InternetAddress> list = new ArrayList<InternetAddress>();
			String[] emails = toEmailAddress.split(",");
			StringBuffer validateMailAddress = new StringBuffer();
			for (String str : emails) {
				if (isNameAdressFormat(str))
					validateMailAddress.append(str).append(",");
			}
			InternetAddress[] address = InternetAddress.parse(validateMailAddress
					.toString());
			if (address != null && address.length > 0)
				for (InternetAddress adds : address) {
					list.add(adds);
				}
			return list;
		}
	    /**
	     * @param email
	     * @return
	     * 判断是否是有效的邮件地址
	     */
	    private static boolean isNameAdressFormat(String email){   
	        boolean isExist = false;   
	        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");   
	        java.util.regex.Matcher m = p.matcher(email);   
	        boolean b = m.matches();   
	        if(b) {   
	            isExist=true;   
	        } else {   
	        }   
	        return isExist;   
	    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public List<FilesBuilderInfo> getFbi() {
		return fbi;
	}

	public void setFbi(List<FilesBuilderInfo> fbi) {
		this.fbi = fbi;
	}

	public String getDelHistoryDay() {
		return delHistoryDay;
	}

	public void setDelHistoryDay(String delHistoryDay) {
		this.delHistoryDay = delHistoryDay;
	}
	
}
