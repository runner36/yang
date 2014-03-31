package com.winchannel.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Criterion;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.winchannel.core.utils.BeanUtils;
import com.winchannel.core.utils.GenericsUtils;
import com.winchannel.core.utils.Page;

/**
 * DAO实体类，需定义泛型参数<T>
 * @author xianghui
 *
 * @param <T> Hibernate实体对象
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDao<T> extends HibernateBaseDao {
	
	protected Class<T> entityClass;

	public HibernateEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}
	
	protected Class<T> getEntityClass() {
		return entityClass;
	}
	
	@Transactional
	public void deleteById(Serializable id) {
		delete(get(id));
	}
	
	public T get(Serializable id) {
		return get(entityClass, id);
	}

	public List<T> getAll() {
		return getAll(entityClass);
	}

	public List<T> getAll(String orderBy) {
		return getAll(entityClass, orderBy);
	}

	public List<T> findEntity(String queryString, Object... values) {
		return findEntity(entityClass, queryString, values);
	}
	
	public T findUniqueEntity(String queryString, Object... values) {
		return findUniqueEntity(entityClass, queryString, values);
	}

	public List<T> findPage(Page page, String queryString, Object... values) {
		int pageNo = page.getInt(Page.PAGE_NO);
		int pageSize = page.getInt(Page.PAGE_SIZE);
		
		Query query = createQuery(queryString, values);
		ScrollableResults scrollableResults = query.scroll();
		scrollableResults.last();
		page.put(Page.TOTAL_COUNT, String.valueOf(scrollableResults.getRowNumber() + 1));
		page.compute();
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	public List<T> findByCriteria(Criterion... criterion) {
		return createCriteria(criterion).list();
	}

	public Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	public Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(entityClass));
	}

	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	public Class getIdClass() {
		Class clazz = null;
		try {
			clazz = BeanUtils.getPropertyType(entityClass, getIdName());
		}
		catch(Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return clazz;
	}

	public List<T> query(Page page) {
	   	return query(entityClass, page);
	}
	
	public List<T> query(Page page, String authPrefix) {
	   	return query(entityClass, page, authPrefix);
	}
	
	public String makeQueryConditions(Page page, String alias) {
		return makeQueryConditions(entityClass, page, alias);
	}
	
	public String makeQueryConditions(Page page, String alias, String authPrefix) {
		return makeQueryConditions(entityClass, page, alias, authPrefix);
	}
	
	public String makeQuerySort(Page page, String alias) {
		return makeQuerySort(entityClass, page, alias);
	}
	
	public void makeParentInfo(Object object, String descName) {
		makeParentInfo(entityClass,  object, descName);
	}
	
	public void makeSubCode(Object object) {
		makeSubCode(entityClass,  object);
	}
	
	public void updateSubCode(Object object) {
		updateSubCode(entityClass, object);
	}
	
	public String getValidSubCode(String parentSubCode) {
		return getValidSubCode(entityClass, parentSubCode);
	}

	public void makeSubCode(Object object, String whereExp) {
		makeSubCode(entityClass, object, whereExp);
	}
	
	public void updateSubCode(Object object, String whereExp) {
		updateSubCode(entityClass, object, whereExp);
	}
	
	public String getValidSubCode(String parentSubCode, String whereExp) {
		return getValidSubCode(entityClass, parentSubCode, whereExp);
	}

}