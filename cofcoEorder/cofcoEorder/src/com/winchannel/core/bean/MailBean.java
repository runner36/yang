package com.winchannel.core.bean;

import java.util.List;

import javax.mail.internet.InternetAddress;

public class MailBean {
	private String mailHostname;
	private String mailFrom;
	private String mailUserName;
	private String mailPassWord;
	private String mailAddTo;
	private List<InternetAddress> mailCcto;
	private String mailTitle;
	public String getMailHostname() {
		return mailHostname;
	}
	public void setMailHostname(String mailHostname) {
		this.mailHostname = mailHostname;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailUserName() {
		return mailUserName;
	}
	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}
	public String getMailPassWord() {
		return mailPassWord;
	}
	public void setMailPassWord(String mailPassWord) {
		this.mailPassWord = mailPassWord;
	}
	public String getMailAddTo() {
		return mailAddTo;
	}
	public void setMailAddTo(String mailAddTo) {
		this.mailAddTo = mailAddTo;
	}
	public List<InternetAddress> getMailCcto() {
		return mailCcto;
	}
	public void setMailCcto(List<InternetAddress> mailCcto) {
		this.mailCcto = mailCcto;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	

}
