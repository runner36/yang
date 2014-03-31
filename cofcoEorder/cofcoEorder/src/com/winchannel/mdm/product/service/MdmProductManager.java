package com.winchannel.mdm.product.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ReflectionUtils;

import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.service.BaseDictManager;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.mdm.product.model.MdmProduct;

public class MdmProductManager extends HibernateEntityDao<MdmProduct> {
	private BaseDictManager baseDictManager;

	public BaseDictManager getBaseDictManager() {
		return baseDictManager;
	}

	public void setBaseDictManager(BaseDictManager baseDictManager) {
		this.baseDictManager = baseDictManager;
	}
	/**
	 * 查询一个对象的属性值在数据库中是不是唯一
	 * @param entity 实体对象
	 * @param propertyNames 属性的名称，可多个 如："prop1,prop2"
	 * @return
	 * 针对 编辑 对象保存时 如果和其它 对象 属性名称相同 抛出异常问题 重写此方法
	 */
	public boolean isUnique(Object entity, String propertyNames) {
		Class<?> clazz = getTrueClass(entity);
		Criteria criteria = createCriteria(clazz).setProjection(
				Projections.rowCount());
		String[] nameList = propertyNames.split(",");
		try {
			boolean isQuery = false;
			for (String name : nameList) {
				Object obj = PropertyUtils.getProperty(entity, name);
				if (obj != null) {
					criteria.add(Restrictions.eq(name, obj));
					isQuery = true;
				} else {
					isQuery = false;
				}
			}
			if (!isQuery) {
				return true;
			}

			String idName = getIdName(clazz);
			Serializable id = getId(clazz, entity);
			if (id != null) {
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		this.getSession().evict(entity);
		return (Integer) criteria.uniqueResult() == 0;
	}

	public void save(Object object) {
		if (!this.isUnique(object, "prodCode")) {
			throw new BusinessException("base","common.codeDuplication");
		}
		if (!this.isUnique(object, "extCode")) {
			throw new BusinessException("base","common.externalCodeDuplication");
		}
		// makeSubCode(object);
		super.save(object);
	}

	/**
	 * 通过code得到字典表里的相关的id
	 * 
	 * @param baseDictManager
	 * @param code
	 * @return
	 */
	public BaseDictItem getMdmStoreByProdCode(String code, String dictId) {
		return baseDictManager.findUniqueEntity(
				"from BaseDictItem where itemCode=? and baseDict.dictId=?",
				code, dictId);
	}

	public BaseDictItem getUnitByName(String code, String dictId) {
		return baseDictManager.findUniqueEntity(
				"from BaseDictItem where itemName=? and baseDict.dictId=?",
				code, dictId);
	}
	/**
	 * 根据code得到产品
	 * 
	 * @param prodCode
	 * @return
	 */
	public MdmProduct getProductByProdCode(String prodCode) {
		return findUniqueEntity("from MdmProduct where prodCode=?", prodCode);
	}

	public MdmProduct getProductById(long prodId) {
		return findUniqueEntity("from MdmProduct where prodId=?", prodId);
	}

	/**
	 * 得到排序号
	 * 
	 * @return
	 */
	public long getSort() {
		String sql = "select max(SORT) from  MDM_PRODUCT ";
		List<?> list = this.executeSqlQuery(sql);
		long sort = 1;
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj[0] != null) {
				sort = ((java.math.BigDecimal) obj[0]) == null ? 1
						: ((java.math.BigDecimal) obj[0]).longValue() + 1;
			}
		}
		return sort;
	}
	public long countMapping(String prod_code){
	 long count=0;
		//count=super.findLong("select count(activePId) from DmsProdMapping where targetProdCode=?", prod_code);
		return count;
	}
	public String getMdmPordCode(long prodId){
		 Object[] obj=this.executeUniqueSqlQuery("SELECT d.PROD_CODE FROM MDM_PRODUCT d WHERE d.PROD_ID="+prodId);
		  if(obj!=null){
			  return obj[0].toString();
		  }
		return null;
	}
	public String getType3(){
		return null;
	}
	public String getType5(){
		return null;
	}
	public String getType6(){
		return null;
	}
	public String getType7(){
		return null;
	}

}
