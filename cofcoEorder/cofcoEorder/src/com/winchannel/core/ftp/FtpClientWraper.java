package com.winchannel.core.ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FtpClientWraper {
	
	public static final Log log = LogFactory.getLog(FtpClientWraper.class);
	
	private FtpClient ftpClient;
	
	public FtpClientWraper(String ip, int port, String userName, String password) throws IOException {
		ftpClient = new FtpClient(ip, port);
		ftpClient.login(userName, password);
		ftpClient.binary();
		log.info("ftp(" + ip + ":" + port + ") login success!");
	}
	
	public void get(String sourceFile, String targetFile) throws IOException {
		TelnetInputStream source = null;
		FileOutputStream target = null;
		
		int bufLen = 2048;
		byte[] buf = new byte[bufLen];
		int readLen = 0;
		
		try {
			source = ftpClient.get(sourceFile);
			target = new FileOutputStream(targetFile);
			while ((readLen = source.read(buf)) != -1) {
				target.write(buf, 0, readLen);
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (source != null) {
				source.close();
			}
			if (target != null) {
				target.close();
			}
		}
		
	}
	
	public void put(String sourceFile, String targetFile) throws IOException {
		FileInputStream source = null;
		TelnetOutputStream target = null;
		
		int bufLen = 2048;
		byte[] buf = new byte[bufLen];
		int readLen = 0;
		
		try {
			source = new FileInputStream(sourceFile);
			target = ftpClient.put(targetFile);
			while ((readLen = source.read(buf)) != -1) {
				target.write(buf, 0, readLen);
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (source != null) {
				source.close();
			}
			if (target != null) {
				target.close();
			}
		}
		
	}
	
	public void cd(String dir) throws IOException {
		ftpClient.cd(dir);
	}
	
	public void close() {
		try {
			ftpClient.closeServer();
			log.info("ftp is close!");
		}
		catch(Exception e) {
			log.error("关闭FTP连接时发生错误：" + e.getMessage(), e);
		}
	}
}
