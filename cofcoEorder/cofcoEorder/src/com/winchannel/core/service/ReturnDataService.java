package com.winchannel.core.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.tools.ant.util.DateUtils;
import org.dom4j.DocumentException;

import com.winchannel.base.model.BaseJobLog;
import com.winchannel.core.bean.FtpBean;
import com.winchannel.core.bean.MailBean;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.dao.HibernatePersister;
import com.winchannel.core.ftp.FtpClient;
import com.winchannel.core.utils.JdbcProxy;
import com.winchannel.core.utils.ZipUtils;

public class ReturnDataService implements Service {
	public static final Log log = LogFactory.getLog("ReturnDataService");
	public static final Pattern PATTERN = Pattern
			.compile("(?<=\\$\\{)[\\w|\\.]*(?=\\})");
	protected ReturnDataServiceConfigurator conf;
	protected Map<String, String> params;
	protected List<String> sourceFiles;
	protected List<String> targetFiles;
	protected List<String> tempFiles;
	protected HibernatePersister persister;
	protected BaseJobLog jobLog;

	public ReturnDataService(String serviceCode, Map<String, String> params)
			throws DocumentException {
		this.params = params;
		if (this.params == null) {
			this.params = new HashMap<String, String>();
		}
		init(serviceCode);
	}

	protected void init(String serviceCode) throws DocumentException {
		this.conf = new ReturnDataServiceConfigurator(serviceCode);
	}

	protected void initPersister() {
		this.persister = new HibernatePersister();
	}

	protected void initJobLog() {
		jobLog = new BaseJobLog();
		jobLog.setStartTime(new Date());
		jobLog.setBaseJob(conf.getCode());
	}

	public void service() throws ServiceException {
		log.info("send file Start... <" + conf.getCode() + ">");
		StringBuffer errorMsg=new StringBuffer();
		this.initPersister();
		this.initJobLog();
		try {
			int ftpSendCount = 0;
			int ftpSendSuccessCount = 0;
			int ftpsendFileCount = 0;
			int ftpSendFileSuccess = 0;
			int mailSendCount = 0;
			int mailSendSucces = 0;
			int mailSendFileCount = 0;
			int mailSendSuccesFile = 0;
			int jdbcSendCount = 0;
			int jdbcSendSucces = 0;
			int jdbcSendRecordCount = 0;
			int jdbcSendRecordScces = 0;

			for (FilesBuilderInfo fileInfo : conf.getFbi()) {
				this.sourceFiles = new ArrayList<String>();
				this.targetFiles = new ArrayList<String>();
				this.tempFiles=new ArrayList<String>();
				if (fileInfo.getMailBean() != null
						|| fileInfo.getFtpBean() != null)
					this.createFiles(fileInfo);
				if (fileInfo.getFtpBean() != null) {
					ftpSendCount++;
					ftpsendFileCount += this.sourceFiles.size();
					boolean sendFlag = this.sendFtpFiles(fileInfo,errorMsg);
					if (sendFlag) {
						ftpSendSuccessCount++;
						ftpSendFileSuccess += this.sourceFiles.size();
						if(!this.after(fileInfo)){
							errorMsg.append(fileInfo.getReportName()).append(" ftp after sql 执行失败");
						}
					}
				}
				if (fileInfo.getMailBean() != null) {
					mailSendCount++;
					mailSendFileCount += this.sourceFiles.size();
					boolean sendFlag = this.sendMailFiles(fileInfo, sourceFiles);
					if (sendFlag) {
						mailSendSucces++;
						mailSendSuccesFile += this.sourceFiles.size();
						if(!this.after(fileInfo)){
							errorMsg.append(fileInfo.getReportName()).append(" mail after sql 执行失败");
						}
					}else{
						errorMsg.append("mail失败文件：");
						if(sourceFiles.size()>0){
							for(String fileDir:sourceFiles)
							  errorMsg.append(fileDir).append(",");
						}
					}
				}
				if (fileInfo.getJdbcBean() != null) {
					jdbcSendCount++;
					List<String> insertSqls = this.genInsertSql(fileInfo);
					if (insertSqls != null && insertSqls.size() > 0) {
						jdbcSendRecordCount += insertSqls.size();
						boolean sendFlag = this.sendJdbc(fileInfo, insertSqls);
						if (sendFlag) {
							jdbcSendRecordScces += insertSqls.size();
							jdbcSendSucces++;
							if(!this.after(fileInfo))
								errorMsg.append(fileInfo.getReportName()).append(" jdbc after sql 执行失败");
						}else{
							errorMsg.append("jdbc执行失败：");
						    errorMsg.append(fileInfo.getReportName());
						}
					}
				}
			}
			jobLog.setIsComplete("1");
			jobLog.setEndTime(new Date());
			StringBuffer msg = new StringBuffer();
			msg.append("任务执行成功：ftp总共发送:(").append(ftpSendCount).append("次,共")
					.append(ftpsendFileCount).append("个文件),成功：(")
					.append(ftpSendSuccessCount).append("次,文件")
					.append(ftpSendFileSuccess).append("个),失败：(")
					.append(ftpSendCount - ftpSendSuccessCount).append("次,文件")
					.append(ftpsendFileCount - ftpSendFileSuccess).append("个)");
			msg.append("mail总共发送:(").append(mailSendCount).append("封,共")
					.append(mailSendFileCount).append("个文件),成功：(")
					.append(mailSendSucces).append("封,文件")
					.append(mailSendSuccesFile).append("个),失败：(")
					.append(mailSendCount - mailSendSucces).append("封,文件")
					.append(mailSendFileCount - mailSendSuccesFile)
					.append("个)");
			msg.append("jdbc总共传送:(").append(jdbcSendCount).append("次,共")
					.append(jdbcSendRecordCount).append("条记录),成功：(")
					.append(jdbcSendSucces).append("次,记录")
					.append(jdbcSendRecordScces).append("条),失败：(")
					.append(jdbcSendCount - jdbcSendSucces).append("次,记录")
					.append(jdbcSendRecordCount - jdbcSendRecordScces)
					.append("条)");
			jobLog.setExecResult(msg.toString());
			//记录失败文件
			jobLog.setErrorInfo(errorMsg.toString());
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

		log.info("send file end... <" + conf.getCode() + ">");
	}

	@SuppressWarnings("deprecation")
	protected List<String> genInsertSql(FilesBuilderInfo fileInfo) {
		ReportConfigurator rptConf = ReportConfigurator.getInstance(fileInfo
				.getReportName());
		List<String> insertSqls = new ArrayList<String>();
		String sql = rptConf.generateSql(params);
		if (StringUtils.isNotBlank(sql)) {
			Statement stat = null;
			ResultSet rs = null;
			try {
				stat = persister.getSession().connection().createStatement();
				if (rptConf.isShowSql()) {
					log.info(sql);
				}
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					String insertSql = fileInfo.getJdbcBean().getInsertSql();
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					if (columnCount > 0) {
						for (int j = 1; j <= columnCount; j++) {
							String name = rsmd.getColumnName(j);// 字段名
							// Object
							// type=Class.forName(rsmd.getColumnClassName(j));
							// //字段类型
							String str = rs.getString(j);// 字段值
							if (str.indexOf("'") != -1)
								str = str.replaceAll("'", "''");
							insertSql = insertSql.replace(name, str);
						}
						insertSqls.add(insertSql);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
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
		return insertSqls;
	}

	private boolean sendJdbc(FilesBuilderInfo fileInfo, List<String> insertSqls) {
		boolean flag = true;
		JdbcProxy db = null;
		try {
			db = new JdbcProxy(fileInfo.getJdbcBean().getJdbcBeanId());
			long id = 0;
			if (StringUtils.isNotBlank(fileInfo.getJdbcBean().getMaxIdSql())
					&& StringUtils
							.isNotBlank(fileInfo.getJdbcBean().getIdStr())) {
				ResultSet rs = db.executeQuery(fileInfo.getJdbcBean()
						.getMaxIdSql());
				if (rs.next()) {
					id = Long.valueOf(rs.getLong(1)) + 1;
				}
				if (rs != null)
					rs.close();
			}
			db.setAutoCommit(false);
			for (String insSql : insertSqls) {
				if (id != 0) {
					insSql = insSql.replace(fileInfo.getJdbcBean().getIdStr(),
							String.valueOf(id));
					id++;
				}
				db.executeUpdate(insSql);
			}
			db.commit();
		} catch (Exception e) {
			flag = false;
			if (db != null) {
				try {
					db.rollback();
					db = null;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			log.error(e.getMessage(), e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return flag;
	}

	@SuppressWarnings("deprecation")
	protected void createFiles(FilesBuilderInfo fileInfo) throws Exception {
		log.info("Creating file ... <" + conf.getCode() + ">");
		//根据当天日期生成文件夹名称
		String dateStr=DateUtils.format(new Date(), "yyyy-MM-dd");
		//取得配置的XML文件
		ReportConfigurator rptConf = ReportConfigurator.getInstance(fileInfo
				.getReportName());
		//生成SQL
		String sql = rptConf.generateSql(params);

		//配置的分组字段，按照分组字段生成文件
		int[] groupFields = fileInfo.getGroupFields();
		
		//配置哪写字段忽略不写入文件
		String ignoreFields = StringUtils
				.isNotBlank(fileInfo.getIgnoreFields()) ? ","
				+ fileInfo.getIgnoreFields() + "," : null;
		//临时文件夹路径
		String tempDir = fileInfo.getSourceDir() + UUID.randomUUID().toString()
				+ "/";
		//创建临时文件夹
		File td = new File(tempDir);
		td.mkdirs();
		//临时文件的路径
		String txtDir = fileInfo.getSourceDir();
		//临时文件的zip文件路径
		String zipDir = fileInfo.getSourceDir()+dateStr+"/";
		//zip 文件夹的路径
		String zipFileDir = fileInfo.getSourceDir()+dateStr+"/";
		//创建此文件夹
		File zipPathtest = new File(zipFileDir);
		if(!zipPathtest.exists())
			zipPathtest.mkdirs();
		
		if (StringUtils.isNotBlank(fileInfo.getZipName())) {
			txtDir = tempDir;
		}else{
			txtDir = fileInfo.getSourceDir()+dateStr+"/";
			File txtd = new File(txtDir);
			if(!txtd.exists())
				txtd.mkdirs();
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
				//如果配置了分组字段，则把配置的分组字段拼接成一个KEY
				if (groupFields != null) {
					for (int i : groupFields) {
						currKey += "_" + rs.getString(i);
					}
				}
				//key 不相同则 生成一个文件
				if (!currKey.equals(key)) {
					if (out != null) {
						out.close();
						out = null;
						if (zipPath != null) {
							ZipUtils.zip(txtPath, zipPath);
						}
					}
					//生成临时文件路径
					txtPath = this.eval(txtDir + fileInfo.getName(), rs,
							params, rptConf);
					//如果配置了 zipname
					if (StringUtils.isNotBlank(fileInfo.getZipName())) {
						/*String txtName = fileInfo.getName().substring(0,
								fileInfo.getName().indexOf("."));
						String zipName = fileInfo.getZipName().substring(0,
								fileInfo.getZipName().indexOf("."));
						if (txtName.equals(zipName)) {
							zipPath = zipDir+zipName+".zip";
								//txtPath.substring(0,txtPath.lastIndexOf("."))+ ".zip";
						} else {*/
							zipPath = this.eval(zipDir + fileInfo.getZipName(),
									rs, params, rptConf);
						//}
						this.sourceFiles.add(zipPath);
						this.targetFiles.add(this.eval(fileInfo.getTargetDir()
								+ fileInfo.getZipName(), rs, params, rptConf));
						//FTP 临时路径
						if (StringUtils.isNotBlank(fileInfo.getTempDir())) {
							this.tempFiles.add(this.eval(fileInfo.getTempDir()
								+ fileInfo.getZipName(), rs, params, rptConf));
						}
					} else {
						//没有配置ZIP 名称
						this.sourceFiles.add(txtPath);
						//FTP要传送的路径
						this.targetFiles.add(this.eval(fileInfo.getTargetDir()
								+ fileInfo.getName(), rs, params, rptConf));
						//FTP 临时路径
						if (StringUtils.isNotBlank(fileInfo.getTempDir())) {
							this.tempFiles.add(this.eval(fileInfo.getTempDir()
								+ fileInfo.getName(), rs, params, rptConf));
						}
					}
					//生成临时文件
					out = new PrintWriter(txtPath, fileInfo.getFileCoding());

					key = currKey;
				}
				//判断是否设置了写入表头
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
			//如果files-builder 里配置了zip-name 则把生成的文件 在压缩成一个文件
			if (StringUtils.isNotBlank(fileInfo.getZipFileName())) {
				String zipFilePath = this.eval(
						zipFileDir + fileInfo.getZipFileName(), rs, params,
						rptConf);
				
				ZipUtils.zip(this.sourceFiles, zipFilePath);
				this.sourceFiles.clear();
				this.targetFiles.clear();
				this.tempFiles.clear();
				this.sourceFiles.add(zipFilePath);
				this.targetFiles.add(this.eval(fileInfo.getTargetDir()
						+ fileInfo.getZipFileName(), rs, params, rptConf));
				this.tempFiles.add(this.eval(fileInfo.getTempDir()
						+ fileInfo.getZipFileName(), rs, params, rptConf));
			}
			//删除临时文件夹
			delete(tempDir);
			//删除历史文件
			this.delHistoryFile(fileInfo.getSourceDir(),this.conf.getDelHistoryDay());
		} catch (Exception e) {
			throw e;
		} finally {
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

	protected boolean after(FilesBuilderInfo fileInfo) {
		if (StringUtils.isNotBlank(fileInfo.getAfterSql())) {
			ReportConfigurator rptConf = ReportConfigurator
					.getInstance(fileInfo.getReportName());
			String afterSql = rptConf.generateSql(fileInfo.getAfterSql(),
					params);
			log.info("After execute sql : " + afterSql);
			try{
				this.persister.executeSqlUpdate(afterSql);
			}catch(Exception e){
				return false;
			}
		}
		return true;
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

	protected boolean sendFtpFiles(FilesBuilderInfo fileInfo,StringBuffer errorMsg) {
		boolean ftpSendFlag = false;
		log.info("ftp transfer begin ... <" + conf.getCode() + ">");
		FtpBean ftpBean = fileInfo.getFtpBean();
		FtpClient ftpClient = null;
		for (int i = 0; i < sourceFiles.size(); i++) {
			String sourceFile = sourceFiles.get(i);
			String targetFile = targetFiles.get(i);
			String tempFile=tempFiles.get(i);
			for (int j = 0; j < 3; j++) {
				try {
					if (ftpClient == null) {
						ftpClient = new FtpClient(ftpBean.getFtpHost(),
								ftpBean.getFtpPort(), ftpBean.getFtpUserName(),
								ftpBean.getFtpPassWord());
					}
					try {
						ftpClient.remove(targetFile);
					} catch (Exception e1) {
					}
					log.info("transfer : " + sourceFile + " ---> " + targetFile);
					try{
						if(StringUtils.isNotBlank(tempFile))
						{
							ftpClient.put(sourceFile, tempFile);
							//ftpClient.rename(tempFile, targetFile);
						}else{
							ftpClient.put(sourceFile, targetFile);
						}
						ftpSendFlag = true;
					}catch(Exception e){
						//传输出现异常则断开重新连接 重新传输
						for(int k=0;k<3;k++){
							ftpClient = retrySend(ftpBean, ftpClient, sourceFile,
									targetFile,tempFile);
							if(ftpClient==null){
								//ftpSendFlag = false;
								continue;
							}else{
								ftpSendFlag = true;
								break;
							}
						}
						if(ftpSendFlag==false){
							errorMsg.append(sourceFile).append(",");
							System.out.println("传输失败："+ftpSendFlag);
						}
					}
					//删除文件			
				/*	delete(sourceFile);*/
					//ftpSendFlag = true;
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
		log.info("ftp transfer end <" + conf.getCode() + ">");
		return ftpSendFlag;
	}

	private FtpClient retrySend(FtpBean ftpBean, FtpClient ftpClient,
			String sourceFile, String targetFile,String tempFile){
		if (ftpClient != null) {
			ftpClient.close();
			ftpClient = null;
		}
		try{
			if(ftpClient==null){
				ftpClient = new FtpClient(ftpBean.getFtpHost(),
					ftpBean.getFtpPort(), ftpBean.getFtpUserName(),
					ftpBean.getFtpPassWord());
			}
		}catch(Exception e){
			ftpClient=null;
		}
		if(ftpClient!=null){
			try {
				if(StringUtils.isNotBlank(tempFile)){
					ftpClient.remove(tempFile);
				}
				ftpClient.remove(targetFile);
			} catch (Exception e) {
			}
			try{
				if(StringUtils.isNotBlank(tempFile))
				{
					ftpClient.put(sourceFile, tempFile);
					ftpClient.rename(tempFile, targetFile);
				}else{
					ftpClient.put(sourceFile, targetFile);
				}
			}catch(Exception e){
				//出了异常则断开连接
				ftpClient.close();
				ftpClient=null;
			}
		}
		return ftpClient;
	}

	protected boolean sendMailFiles(FilesBuilderInfo fileInfo,
			List<String> sourceFtiles) throws ServiceException {
		boolean sendFlag = true;
		log.info("Mail transfer begin ... <" + conf.getCode() + ">");
		MailBean mailBean = fileInfo.getMailBean();
		if (sourceFtiles != null && sourceFtiles.size() > 0) {
			MultiPartEmail email = new MultiPartEmail();
			initMail(email, mailBean);
			email.setSubject(mailBean.getMailTitle());
			for (int i = 0; i < sourceFtiles.size(); i++) {
				String sourceFile = sourceFtiles.get(i);
				EmailAttachment attachment = new EmailAttachment();
				attachment.setPath(sourceFile);
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				try {
					email.attach(attachment);
				} catch (EmailException e) {
					e.printStackTrace();
					sendFlag = false;
				}
			}
			try {
				email.send();
				//删除文件
			/*	if (sourceFtiles != null && sourceFtiles.size() > 0) {
					for (int i = 0; i < sourceFtiles.size(); i++) {
						this.delete(sourceFtiles.get(i));
					}
				}*/
			} catch (EmailException e) {
				e.printStackTrace();
				sendFlag = false;
				// throw new ServiceException(e.getMessage(), e);
			}
		}
		log.info("Mail transfer end <" + conf.getCode() + ">");
		return sendFlag;
	}

	private void initMail(MultiPartEmail email, MailBean mailBean) {
		try {
			email.setHostName(mailBean.getMailHostname());
			email.addTo(mailBean.getMailAddTo());
			if (mailBean.getMailCcto() != null)
				email.setCc(mailBean.getMailCcto());
			email.setFrom(mailBean.getMailFrom());
			email.setAuthentication(mailBean.getMailUserName(),
					mailBean.getMailPassWord());
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	private  boolean delete(String fileName){   
		File file = new File(fileName);
		if (!file.exists()) {
			log.error("删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

    private  boolean deleteFile(String fileName){   
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			log.error("删除单个文件" + fileName + "失败！");
			return false;
		}
    }  
	  
    private  boolean deleteDirectory(String dir){   
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.error("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		if(files!=null && files.length>0){
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag) {
						break;
					}
				}else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag) {
						break;
					}
				}
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			log.error("删除目录" + dir + "失败！");
			return false;
		}
    }  
    
	public void delHistoryFile(String directoryPath, String days) {
		// 如果配置了删除时间
		if (StringUtils.isNotBlank(days)) {
			File f = new File(directoryPath);
			if (f.exists() && f.isDirectory()) {
				// 当天日期字符串
				String dateStr = DateUtils.format(new Date(), "yyyy-MM-dd");
				File lists[] = f.listFiles();
				for (File fl : lists) {
					// 如果是文件夹
					if (fl.isDirectory()) {
						if (fl.getName().indexOf("-") == 4
								&& fl.getName().indexOf("-", 5) == 7)
							try {
								if (getIntervalDays(
										DateUtils
												.parseIso8601Date(fl.getName()),
										DateUtils.parseIso8601Date(dateStr)) > Integer
										.valueOf(days)) {
									this.delete(directoryPath + fl.getName());
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
					}
				}
			}
		}
	}

    public  int getIntervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	}


}
