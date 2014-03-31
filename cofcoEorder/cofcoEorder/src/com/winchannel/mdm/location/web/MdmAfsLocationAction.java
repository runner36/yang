package com.winchannel.mdm.location.web;

import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.location.model.MdmAfsLocation;
import com.winchannel.mdm.location.service.MdmAfsLocationManager;

public class MdmAfsLocationAction extends StrutsEntityAction<MdmAfsLocation, MdmAfsLocationManager> {
	protected void init() {
		this.setFirstQuery(false);
	}
}
	
