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


public class SecurityFilter implements Filter{

	protected FilterConfig filterConfig;
	private String ignoreUri;
	private String loginUri;
	private boolean openLog;
	private boolean uriAuth;
	
	private Map<String, String[]> resources;
	private BaseLogManager baseLogManager;
	
	//Handle the passed-in FilterConfig
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		BaseConfigurator conf = BaseConfigurator.getInstance();
		this.ignoreUri = conf.getIgnoreUri();
		this.loginUri = conf.getLoginUri();
		this.openLog = conf.isBaseLog();
		this.uriAuth = conf.isUriAuth();
		resources = ((BaseSecurityManager) SpringContext.getBean("baseSecurityManager")).getAllResourecs();
		baseLogManager = (BaseLogManager) SpringContext.getBean("baseLogManager");
	}

	//Process the request/response pair
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		BaseLog baseLog = null;
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			String key = req.getServletPath();
			if (req.getSession().getAttribute(Constants.SESSION_USER_KEY) == null && ignoreUri.indexOf(key) == -1) {
				response.getWriter().write("<script>parent.location='" + req.getContextPath() + loginUri + "';</script>");
				return;
			}

			String m = req.getParameter("method");
			if (m != null) {
				key += "?method=" + m;
			}
			else {
				m = req.getParameter("fileName");
				if (m != null) {
					key += "?fileName=" + m;
				}
			}
			
			String[] res = resources.get(key);
			
			if (uriAuth) {
				if (res != null && !((Map) req.getSession().getAttribute(Constants.SESSION_AUTH_KEY)).containsKey(res[0])) {
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().write("<script>alert('You do not have permission to perform this operation');history.go(-1);</script>");
					return;
				}
			}
			
			if (openLog && key.indexOf(".do") != -1) {
				baseLog = new BaseLog();
				baseLog.setResUri(key);
				baseLog.setResName(res == null ? null : res[1]);
				baseLog.setStartTime(new Date());
				baseLog.setClientIp(request.getRemoteHost());
				baseLog.setClientName(request.getRemoteAddr());
			}

			filterChain.doFilter(request, response);


		}
		catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		}
		catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
		finally {
			if (baseLog != null) {
				BaseUser user = (BaseUser) ((HttpServletRequest) request).getSession().getAttribute(Constants.SESSION_USER_KEY);
				if (user != null) {
					baseLog.setUserName(user.getUserAccount() + ":" + user.getBaseEmployee().getEmpName());
				}
				baseLog.setEndTime(new Date());
				baseLog.setUsedTime(baseLog.getEndTime().getTime() - baseLog.getStartTime().getTime());
				baseLogManager.save(baseLog);
			}
		}
	}


	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
