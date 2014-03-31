package com.winchannel.core.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.winchannel.core.importer.ImpConfigurator;
import com.winchannel.core.utils.StringUtils;

public class FtpServiceConfigurator {

	public static final Log log = LogFactory.getLog("FtpServiceConfigurator");
	public static final URL CONF_FILE_PATH = ImpConfigurator.class.getResource("/config/service/service.xml");
	
	private String code;
	private String host;
	private int port;
	private String username;
	private String password;
	
	private List<FilesBuilderInfo> fbi = new ArrayList<FilesBuilderInfo>();
	
	public FtpServiceConfigurator(String code) throws DocumentException {
		init(code);
	}
	
	private void init(String code) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(CONF_FILE_PATH);
		
		Element sroot = null;
		Iterator ftpIt = doc.getRootElement().elementIterator("ftp-service");
		
		
		//找到code对应的节点
		while (ftpIt.hasNext() && (sroot == null || !code.equals(sroot.attributeValue("code")))) {
			sroot = (Element) ftpIt.next();
		}
		
		this.code = sroot.attributeValue("code");
		
		this.host = sroot.elementText("host");
		this.port = StringUtils.isNotBlank(sroot.elementText("port")) ? Integer.parseInt(sroot.elementText("port")) : 21;
		this.username = sroot.elementText("username");
		this.password = sroot.elementText("password");
		
		Iterator fileEl = sroot.elementIterator("files-builder");
		while (fileEl.hasNext()) {
			Element el = (Element) fileEl.next();
			FilesBuilderInfo f = new FilesBuilderInfo();
		
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
			fbi.add(f);
		}
		
	}

	public String getCode() {
		return code;
	}

	public List<FilesBuilderInfo> getFbi() {
		return fbi;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

}
