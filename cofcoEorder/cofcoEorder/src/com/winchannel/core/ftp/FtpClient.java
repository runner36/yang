package com.winchannel.core.ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClient {

	public static final Log log = LogFactory.getLog("FtpClient");

	private final static String SYS_ENCODING = System
			.getProperty("file.encoding");

	private FTPClient ftp;

	public FtpClient(String server, int port, String userName, String password)
			throws IOException {
		ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(server);
			log.info("Connected to " + server + ".");

			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP server refused connection.");
				throw new IOException("FTP server refused connection.");
			}

			if (!ftp.login(userName, password)) {
				ftp.logout();
			}
			log.info("Remote system is " + ftp.getSystemType());
			log.info("Control Encoding is " + ftp.getControlEncoding());

			this.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setDataTimeout(1000 * 60 * 60 * 2);

			ftp.enterLocalPassiveMode();
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
				}
			}
			log.error("Could not connect to server.");
			throw e;
		}
	}

	public boolean setFileType(int fileType) throws IOException {
		return ftp.setFileType(fileType);
	}

	public void get(String remoteFile, String localFile) throws IOException {
		try {
			if (!SYS_ENCODING.equals("GBK")) {
				remoteFile = new String(remoteFile.getBytes("GBK"),
						ftp.getControlEncoding());
			} else {
				remoteFile = new String(remoteFile.getBytes(),
						ftp.getControlEncoding());
			}

			log.info("remoteFile:" + remoteFile);
			log.info("localFile:" + localFile);

			OutputStream output;
			output = new FileOutputStream(localFile);
			boolean retrived = ftp.retrieveFile(remoteFile, output);
			if (retrived) {
				log.info("get file success:" + remoteFile);
			} else {
				log.error("get file faild:" + remoteFile);
			}
			output.close();
			ftp.logout();
		} catch (FTPConnectionClosedException e) {
			log.error("Server closed connection.");
			throw e;
		}
	}

	public void put(String localFile, String remoteFile) throws IOException {
		try {
			InputStream input;
			input = new FileInputStream(localFile);
			ftp.storeFile(remoteFile, input);
			input.close();
		} catch (FTPConnectionClosedException e) {
			log.error("Server closed connection.");
			throw e;
		}
	}

	public boolean cd(String dir) throws IOException {
		if (!SYS_ENCODING.equals("GBK")) {
			dir = new String(dir.getBytes("GBK"), ftp.getControlEncoding());
		} else {
			dir = new String(dir.getBytes(), ftp.getControlEncoding());
		}
		boolean rtn = ftp.changeWorkingDirectory(dir);
		if (rtn) {
			log.info("ftp cd to " + dir);
		} else {
			log.error("ftp cd to " + dir + " faild!");
		}
		return rtn;
	}

	public boolean cdup() throws IOException {
		return ftp.changeToParentDirectory();
	}

	public boolean remove(String fileName) throws IOException {
		return ftp.deleteFile(fileName);
	}

	public boolean rename(String from, String to) throws IOException {
		return ftp.rename(from, to);
	}

	public boolean mkdir(String dir) throws IOException {
		return ftp.makeDirectory(dir);
	}

	public void close() {
		try {
			ftp.logout();
		} catch (IOException e) {
			// log.error("关闭FTP连接时发生错误：" + e.getMessage(), e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
				}
			}
		}
	}

	public static void main(String[] args) {
		FtpClient ftpClient;
		try {
			ftpClient = new FtpClient("ftp.topsports.com.cn", 21, "nkftp",
					"nkdownload");
			// ftpClient.get("2011年05月/20110522-20110528.rar", "d:/test.rar");
			ftpClient.cd("2011年05月");
			// ftpClient.cdup();
			// ftpClient.get("20110430-库存.rar", "d:/test.rar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
