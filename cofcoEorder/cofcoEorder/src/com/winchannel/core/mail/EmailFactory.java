package com.winchannel.core.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import com.winchannel.core.conf.EmailConfigurator;
import com.winchannel.core.utils.StringUtils;

public class EmailFactory {
	
	private static EmailConfigurator conf = EmailConfigurator.getInstance();
	
	public static Email getSimpleEmail() throws EmailException {
		Email email = new SimpleEmail();
		init(email);
		return email;
	}

	public static HtmlEmail getHtmlEmail() throws EmailException {
		HtmlEmail email = new HtmlEmail();
		init(email);
		return email;
	}
	
	public static MultiPartEmail getMultiPartEmail() throws EmailException {
		MultiPartEmail email = new MultiPartEmail();
		init(email);
		return email;
	}
	
	private static void init(Email email) throws EmailException {
		email.setHostName(conf.getHostName());
		email.setSmtpPort(conf.getSmptPort());
		if (StringUtils.isNotBlank(conf.getUserName())) {
			email.setAuthenticator(new DefaultAuthenticator(conf.getUserName(), conf.getPassword()));
		}
		email.setTLS(true);
		if (StringUtils.isNotBlank(conf.getFrom())) {
			email.setFrom(conf.getFrom());
		}
		if (conf.getTo() != null) {
			for (String to : conf.getTo()) {
				email.addTo(to);
			}
		}
		if (conf.getCc() != null) {
			for (String cc : conf.getCc()) {
				email.addCc(cc);
			}
		}
		if (conf.getBcc() != null) {
			for (String bcc : conf.getBcc()) {
				email.addBcc(bcc);
			}
		}
		
	}

}
