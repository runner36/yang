package com.winchannel.base.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.winchannel.base.model.BaseJobLog;
import com.winchannel.base.service.BaseJobLogManager;
import com.winchannel.core.utils.Page;
import com.winchannel.core.web.StrutsEntityAction;

public class BaseJobLogAction extends StrutsEntityAction<BaseJobLog, BaseJobLogManager> {
	
	@Override
	protected void onInitList(ActionForm form, HttpServletRequest request, List list, Page page) {
		request.setAttribute("jobs", this.entityManager.getJobs());
//		BaseJobLogManager.createLog("testJob", new Date(), new Date(), false, "成功执行", "无错误消息", "无备注", "demo.xml");
	}

}
