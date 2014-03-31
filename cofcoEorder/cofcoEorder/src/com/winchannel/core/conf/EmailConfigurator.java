package com.winchannel.core.conf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class EmailConfigurator {

	public static final String FILE_NAME = Thread.currentThread()
			.getContextClassLoader().getResource("/config/mail.properties")
			.getFile();

	private static EmailConfigurator instance;

	private Properties props = new Properties();
	private String hostName;
	private int smptPort;
	private String userName;
	private String password;
	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;

	public static EmailConfigurator getInstance() {
		if (instance == null) {
			instance = new EmailConfigurator();
		}
		return instance;
	}

	public static String getProperty(String name) {
		return instance.props.getProperty(name);
	}

	private EmailConfigurator() {
		init();
	}

	public void init() {
		Properties props = new Properties();
		try {
			InputStream ips = new BufferedInputStream(new FileInputStream(
					FILE_NAME));
			props.load(ips);
			this.hostName = props.getProperty("default.hostName");
			this.smptPort = Integer.parseInt(props
					.getProperty("default.smptPort"));
			this.userName = props.getProperty("default.userName");
			this.password = props.getProperty("default.password");
			this.from = props.getProperty("default.from");

			String to = props.getProperty("default.to");
			if (StringUtils.isNotBlank(to)) {
				this.to = to.split("\\,");
			}
			String cc = props.getProperty("default.cc");
			if (StringUtils.isNotBlank(cc)) {
				this.cc = to.split("\\,");
			}
			String bcc = props.getProperty("default.bcc");
			if (StringUtils.isNotBlank(bcc)) {
				this.bcc = to.split("\\,");
			}

			ips.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getBcc() {
		return bcc;
	}

	public String[] getCc() {
		return cc;
	}

	public String getFrom() {
		return from;
	}

	public String getHostName() {
		return hostName;
	}

	public String getPassword() {
		return password;
	}

	public int getSmptPort() {
		return smptPort;
	}

	public String[] getTo() {
		return to;
	}

	public String getUserName() {
		return userName;
	}

}
