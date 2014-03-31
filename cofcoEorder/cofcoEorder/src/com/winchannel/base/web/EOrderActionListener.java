package com.winchannel.base.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseLog;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.service.BaseLogManager;
import com.winchannel.base.service.BaseSecurityManager;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.utils.SpringContext;


public class EOrderActionListener implements HttpSessionListener{

	private static HashMap onLineMap = null;
		
	public static HashMap getOnLineMap() {
        if (onLineMap == null){
            onLineMap = new HashMap();
        }
        return onLineMap;
    }
	
	 public EOrderActionListener(){
	        
	    }
	
	public static void invalidateOtherSession(String userid,String sessionId) {        
        Set keySet = getOnLineMap().keySet();
        Iterator ite = keySet.iterator();
        while (ite.hasNext()) {
        	String key=ite.next().toString();
        	if(!key.equals(sessionId)){
        		HttpSession tempSession = (HttpSession) getOnLineMap().get(key);            
                BaseUser baseUser = (BaseUser) tempSession.getAttribute(Constants.SESSION_USER_KEY);            
                if (userid.equals(String.valueOf(baseUser.getUserId()))) {
                	getOnLineMap().remove(tempSession.getId());
                	tempSession.invalidate();
                	break;
                }    
        	}                    
        }
    }
	
	public static String hasLoginOn(String userid) {        
        Set keySet = getOnLineMap().keySet();
        Iterator ite = keySet.iterator();
        while (ite.hasNext()) {
            HttpSession tempSession = (HttpSession) getOnLineMap().get(ite.next().toString());            
            BaseUser baseUser = (BaseUser) tempSession.getAttribute(Constants.SESSION_USER_KEY);            
            if (userid.equals(String.valueOf(baseUser.getUserId()))) {
            	//getOnLineMap().remove(tempSession.getId());
            	//tempSession.invalidate();
            	return "true";
            }            
        }
        return "false";
    }
	
	public static boolean checkLogin(HttpSession session){
        if (getOnLineMap().containsKey(session.getId())) {
            return true;
        }
        return false;
    }
	//Clean up resources
	public void destroy() {
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        HttpSession session = se.getSession();
        //BaseUser baseUser = (BaseUser)session.getAttribute(Constants.SESSION_USER_KEY);        
        if (getOnLineMap().containsKey(session.getId())) {
        	getOnLineMap().remove(session.getId());
        }        
    }
}
