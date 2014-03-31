package com.winchannel.core.utils;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;


public class MessageResourceUtils extends HashMap {

	private HttpSession session;
	
	public MessageResourceUtils(HttpSession session) {
        this.session = session;
    }

	public Object get(Object k) {
		String key = StringUtils.toString(k);
		String value = null;

		int i = key.indexOf(".");
		if (i == -1) {
			return "";
		}
		String bundle = key.substring(0, i);
		key = key.substring(i + 1);
		if ("".equals(bundle)) {
			bundle = Globals.MESSAGES_KEY;
		}

		MessageResources resources = (MessageResources) session.getServletContext().getAttribute(bundle);
		if (resources == null) {
			if (!bundle.equals(Globals.MESSAGES_KEY)) {
				resources = (MessageResources) session.getServletContext().getAttribute(Globals.MESSAGES_KEY);
			}
		}

		if (resources != null) {
			Locale userLocale = getUserLocale(null);
			value = resources.getMessage(userLocale, key);
		}
		return value;
	}
	
	public Locale getUserLocale(String locale) {

        Locale userLocale = null;
        if (locale == null) {
            locale = Globals.LOCALE_KEY;
        }

        if (session != null) {
            userLocale = (Locale) session.getAttribute(locale);
        }

        return userLocale;

    }
	
	public static void main(String[] args) {
		String key = "";
		
		int i = key.indexOf(".");
		if (i == -1) {
			return;
		}
		String bundle = key.substring(0, i);
		key = key.substring(i + 1);
		System.out.println(bundle + ":" + key);
	}
	

}
