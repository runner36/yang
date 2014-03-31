package com.winchannel.core.service;

import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.winchannel.base.model.BaseJob;
import com.winchannel.base.model.BaseJobLog;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.dao.HibernatePersister;
import com.winchannel.core.ftp.FtpClient;
import com.winchannel.core.utils.ZipUtils;

public class FtpService implements Service {

	public static final Log log = LogFactory.getLog("FtpService");
	public static final Pattern PATTERN = Pattern
			.compile("(?<=\\$\\{)[\\w|\\.]*(?=\\})");

	protected FtpServiceConfigurator conf;
	protected Map<String, String> params;
	protected List<String> sourceFiles;
	protected List<String> targetFiles;

	protected HibernatePersister persister;
	protected BaseJobLog jobLog;

	/**
	 * 
	 * @param serviceCode
	 *            xml配置文件中的name
	 * @param params
	 * @throws DocumentException
	 */
	public FtpService(String serviceCode, Map<String, String> params)
			throws DocumentException {
		this.params = params;
		if (this.params == null) {
			this.params = new HashMap<String, String>();
		}
		init(serviceCode);
	}

	protected void init(String serviceCode) throws DocumentException {
		this.conf = new FtpServiceConfigurator(serviceCode);
	}

	protected void initPersister() {
		this.persister = new HibernatePersister();
	}

	protected void initJobLog() {
		jobLog = new BaseJobLog();
		jobLog.setBaseJob(conf.getCode());
		// jobLog.setBaseJob(this.persister.get(BaseJob.class, conf.getCode()));
		jobLog.setStartTime(new Date());
	}

	public void service() throws ServiceException {
		log.info("FTP service started ... <" + conf.getCode() + ">");
		this.initPersister();
		this.initJobLog();

		try {
			int fileCount = 0;
			for (FilesBuilderInfo fileInfo : conf.getFbi()) {
				this.sourceFiles = new ArrayList<String>();
				this.targetFiles = new ArrayList<String>();

				this.createFiles(fileInfo);
				this.sendFiles(fileInfo);
				fileCount += this.sourceFiles.size();
				this.after(fileInfo);
			}
			jobLog.setIsComplete("1");
			jobLog.setEndTime(new Date());
			jobLog.setExecResult("成功，总共传送了" + fileCount + "个文件");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jobLog.setIsComplete("0");
			jobLog.setErrorInfo(e.getMessage());
			throw new ServiceException(e.getMessage(), e);
		} finally {
			jobLog.setUpdated(new Date());
			this.persister.save(jobLog);
			this.persister.flush();
			this.persister.close();
		}
		log.info("FTP service end <" + conf.getCode() + ">");
	}

	protected void createFiles(FilesBuilderInfo fileInfo) throws Exception {
		log.info("Creating file ... <" + conf.getCode() + ">");
		ReportConfigurator rptConf = ReportConfigurator.getInstance(fileInfo
				.getReportName());
		String sql = rptConf.generateSql(params);

		int[] groupFields = fileInfo.getGroupFields();
		String ignoreFields = StringUtils
				.isNotBlank(fileInfo.getIgnoreFields()) ? ","
				+ fileInfo.getIgnoreFields() + "," : null;

		String tempDir = fileInfo.getSourceDir() + UUID.randomUUID().toString()
				+ "/";
		File td = new File(tempDir);
		td.mkdirs();

		String txtDir = fileInfo.getSourceDir();
		String zipDir = fileInfo.getSourceDir();
		String zipFileDir = fileInfo.getSourceDir();
		if (StringUtils.isNotBlank(fileInfo.getZipName())) {
			txtDir = tempDir;
		}
		if (StringUtils.isNotBlank(fileInfo.getZipFileName())) {
			txtDir = tempDir;
			zipDir = tempDir;
		}

		Statement stat = null;
		ResultSet rs = null;
		PrintWriter out = null;
		String txtPath = null;
		String zipPath = null;
		String key = null;
		boolean isWriteHeadLine = false;
		try {
			stat = persister.getSession().connection().createStatement();
			if (rptConf.isShowSql()) {
				log.info(sql);
			}
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				String currKey = "";
				if (groupFields != null) {
					for (int i : groupFields) {
						currKey += "_" + rs.getString(i);
					}
				}

				if (!currKey.equals(key)) {
					if (out != null) {
						out.close();
						out = null;
						if (zipPath != null) {
							ZipUtils.zip(txtPath, zipPath);
						}
					}
					System.out.print(".");
					txtPath = this.eval(txtDir + fileInfo.getName(), rs,
							params, rptConf);
					if (StringUtils.isNotBlank(fileInfo.getZipName())) {
						String txtName = fileInfo.getName().substring(0,
								fileInfo.getName().indexOf("."));
						String zipName = fileInfo.getZipName().substring(0,
								fileInfo.getZipName().indexOf("."));
						zipPath = this.eval(zipDir + fileInfo.getZipName(), rs,
								params, rptConf);
						if (txtName.equals(zipName)) {
							zipPath = txtPath.substring(0,
									txtPath.lastIndexOf("."))
									+ zipPath.substring(
											zipPath.lastIndexOf("."),
											zipPath.length());
						}
						this.sourceFiles.add(zipPath);
						this.targetFiles.add(this.eval(fileInfo.getTargetDir()
								+ fileInfo.getZipName(), rs, params, rptConf));
					} else {
						this.sourceFiles.add(txtPath);
						this.targetFiles.add(this.eval(fileInfo.getTargetDir()
								+ fileInfo.getName(), rs, params, rptConf));
					}

					out = new PrintWriter(txtPath, "UTF-8");
					key = currKey;
				}
				if (out != null && !isWriteHeadLine
						&& fileInfo.getHeadLine() != null
						&& fileInfo.getHeadLine().trim().length() > 0) {
					out.print(fileInfo.getHeadLine().trim());
					out.println();
					isWriteHeadLine = true;
				}
				int columnCount = rs.getMetaData().getColumnCount();
				int count = 0;
				for (int j = 0; j < columnCount; j++) {
					String str = rs.getString(j + 1);
					if (ignoreFields != null
							&& ignoreFields.indexOf("," + j + ",") != -1) {
						continue;
					}
					if (count > 0) {
						out.print(fileInfo.getDelimiter());
					}
					out.print(str == null ? "" : str.trim());
					count++;
				}
				out.println();
			}
			if (out != null) {
				out.close();
				out = null;
				if (zipPath != null) {
					ZipUtils.zip(txtPath, zipPath);
				}
			}

			if (StringUtils.isNotBlank(fileInfo.getZipFileName())) {
				String zipFilePath = this.eval(
						zipFileDir + fileInfo.getZipFileName(), rs, params,
						rptConf);
				ZipUtils.zip(this.sourceFiles, zipFilePath);
				this.sourceFiles.clear();
				this.targetFiles.clear();
				this.sourceFiles.add(zipFilePath);
				this.targetFiles.add(this.eval(fileInfo.getTargetDir()
						+ fileInfo.getZipFileName(), rs, params, rptConf));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			System.out.println();
			if (out != null) {
				out.close();
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (Exception e) {
					log.error(e);
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}

	}

	protected void sendFiles(FilesBuilderInfo fileInfo) {
		log.info("File transfer begin ... <" + conf.getCode() + ">");
		FtpClient ftpClient = null;
		for (int i = 0; i < sourceFiles.size(); i++) {
			String sourceFile = sourceFiles.get(i);
			String targetFile = targetFiles.get(i);
			for (int j = 0; j < 30; j++) {
				try {
					if (ftpClient == null) {
						ftpClient = new FtpClient(conf.getHost(),
								conf.getPort(), conf.getUsername(),
								conf.getPassword());
					}
					try {
						ftpClient.remove(targetFile);
					} catch (Exception e1) {
					}
					log.info("transfer : " + sourceFile + " ---> " + targetFile);
					ftpClient.put(sourceFile, targetFile);
					break;
				} catch (Exception e) {
					log.error(e);
					try {
						Thread.sleep(30000);
					} catch (Exception e1) {
					}

					continue;
				}
			}
		}
		try {
			if (ftpClient != null) {
				ftpClient.close();
			}
		} catch (Exception e1) {
		}
		ftpClient = null;
		log.info("File transfer end <" + conf.getCode() + ">");
	}

	protected void after(FilesBuilderInfo fileInfo) {
		if (StringUtils.isNotBlank(fileInfo.getAfterSql())) {
			ReportConfigurator rptConf = ReportConfigurator
					.getInstance(fileInfo.getReportName());
			String afterSql = rptConf.generateSql(fileInfo.getAfterSql(),
					params);
			log.info("After execute sql : " + afterSql);
			this.persister.executeSqlUpdate(afterSql);
		}
	}

	private String eval(String exp, ResultSet rs, Map<String, String> params,
			ReportConfigurator rptConf) {
		if (StringUtils.isBlank(exp)) {
			return "";
		}
		// 专门为nike的eis库存传输设置
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		exp = StringUtils
				.replace(exp, "${currDate112}", sdf.format(new Date()));

		Matcher matcher = PATTERN.matcher(exp);
		while (matcher.find()) {
			String name = matcher.group();
			Object result = null;
			if (params.containsKey(name)) {
				result = params.get(name);
			} else if ((result = rptConf.getParamValue(name)) != null) {
			} else {
				try {
					result = rs.getString(Integer.parseInt(name) + 1);
				} catch (Exception e) {
				}
			}
			exp = StringUtils.replace(exp, "${" + name + "}",
					(result == null ? "" : result.toString()));
		}

		return exp == null ? "" : exp;

	}

}
