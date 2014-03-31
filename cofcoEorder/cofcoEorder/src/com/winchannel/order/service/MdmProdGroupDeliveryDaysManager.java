package com.winchannel.order.service;

import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.order.model.MdmProdGroupDeliveryDays;

public class MdmProdGroupDeliveryDaysManager extends HibernateEntityDao<MdmProdGroupDeliveryDays> {

	public MdmProdGroupDeliveryDays getByProdBrandId(Long id, Long prodBrandId){
		return this.findUniqueEntity("from MdmProdGroupDeliveryDays where id != ? and itemProdStru.dictItemId=?", id, prodBrandId);
	}
}
