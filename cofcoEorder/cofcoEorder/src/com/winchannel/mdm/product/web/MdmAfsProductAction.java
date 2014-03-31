package com.winchannel.mdm.product.web;

import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.product.model.MdmAfsProduct;
import com.winchannel.mdm.product.service.MdmAfsProductManager;

public class MdmAfsProductAction extends StrutsEntityAction<MdmAfsProduct, MdmAfsProductManager>  {
	protected void init() {
		this.setFirstQuery(false);
	}
}
