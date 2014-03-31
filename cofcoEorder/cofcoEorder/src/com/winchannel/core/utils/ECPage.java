package com.winchannel.core.utils;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * 
 * @author xianghui
 * 
 */
@SuppressWarnings("serial")
public class ECPage extends Page {
	
	public static String DEFAULT_PAGENAME = "ec";

	public ECPage(HttpServletRequest request) {
		this(request, DEFAULT_PAGENAME);
	}

	public ECPage(HttpServletRequest request, String pageName) {
		super(request, pageName);
	}
	
	protected void initParams() {
		this.put("ec_eti", null);
		String parameters = "";
		String sort = "";
		Iterator<String> it = this.keySet().iterator();
		while (it.hasNext()) {
			String name = it.next();
			String value = this.get(name);
			if (StringUtils.isNotBlank(value)) {
				if (name.indexOf(pageName + "_") == 0 || name.indexOf("ec_") == 0) {

					if (parameters.equals("")) {
						parameters += "?" + name + "=" + value;
					}
					else {
						parameters += "&" + name + "=" + value;
					}
					
					String s = pageName + "_s_";
					if (name.indexOf(s) == 0 && !value.equals("") && !value.equals("default")) {
						String sortName = name.substring(s.length());
						
						String a = this.get(pageName + "_a_" + sortName);
						if (a != null) {
							if (StringUtils.isNumeric(a)) {
								sortName = sortName.replaceAll("_", ".");
							}
							else {
								sortName = a;
							}
						}

						if (sort.equals("")) {
							sort += sortName + " " + value;
						}
						else {
							sort += "," + sortName + " " + value;
						}
					}
				}
			}
		}
		if (sort.length() == 0) {
			sort = this.get(DEFAULT_SORT);
		}
		this.put(SORT, sort);
		this.put("parameters", parameters);

		if (this.get(pageName + "_ev") != null && this.get(pageName + "_ev").length() > 0) {
			if (this.get(pageName + "_exp_p") != null && this.get(pageName + "_exp_p").length() > 0) {
				this.put(PAGE_NO, this.get(pageName + "_exp_p"));
			}
			else {
				this.put(PAGE_NO, DEFAULT_PAGE);
			}
			this.put(PAGE_SIZE, DEFAULT_EXPORTSIZE);     
		}
		else {
			if (this.get(pageName + "_p") != null && this.get(pageName + "_p").length() > 0) {
				this.put(PAGE_NO, this.get(pageName + "_p"));
			}
			else {
				this.put(PAGE_NO, DEFAULT_PAGE);
			}

			if (this.get(pageName + "_crd") != null && this.get(pageName + "_crd").length() > 0) {
				this.put(PAGE_SIZE, this.get(pageName + "_crd"));
			}
			else {
				this.put(PAGE_SIZE, DEFAULT_PAGESIZE);
			}
		}

	}
	
	public void compute() {
		super.compute();
//		ActionContext.getContext().getValueStack().set("totalRows", this.getInt(TOTAL_COUNT));
	}
	
    public ActionForward initForward(ActionForward forward) {
    	return new ActionForward(forward.getName(),	forward.getPath() + get("parameters"), forward.getRedirect(), forward.getContextRelative());
    }

}
