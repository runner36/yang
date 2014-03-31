package com.winchannel.base.web;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.core.web.StrutsEntityAction;

public class SelectEmployeeAction extends StrutsEntityAction<BaseEmployee, BaseEmployeeManager> {

	@Override
	protected void init() {
		this.setPageName("seleEmp");
	}

}
