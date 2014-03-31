package com.winchannel.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.Constants;
import com.winchannel.core.utils.JdbcProxy;
import com.winchannel.core.utils.NumberUtils;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;

/**
 * DAO基类
 * @author xianghui
 *
 */
@SuppressWarnings("unchecked")
public class HibernateBaseDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	/**
	 * 新增或修改一个对象
	 * @param object
	 */
	@Transactional
	public void save(Object object) {
		getSession().saveOrUpdate(object);
	}
	/**
	 * 新增或修改集合中的全部对象
	 * @param collection
	 */
	@Transactional
	public void saveAll(Collection collection) {
		for (Iterator it = collection.iterator(); it.hasNext();) {
			save(it.next());
		}
	}
	

	
	/**
	 * 删除一个对象
	 * @param object
	 */
	@Transactional
	public void delete(Object object) {
		getSession().delete(object);
	}
	/**
	 * 删除HQL查询到的全部对象
	 * @param hql
	 * @param values
	 */
	@Transactional
	public void deleteAll(String hql, Object... values) {
		deleteAll(find(hql, values));
	}
	/**
	 * 删除集合中的全部对象
	 * @param collection
	 */
	@Transactional
	public void deleteAll(Collection collection) {
		for (Iterator it = collection.iterator(); it.hasNext();) {
			delete(it.next());
		}
	}
	/**
	 * 根据ID删除一个对象
	 * @param <T>
	 * @param entityClass
	 * @param id
	 */
	@Transactional
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
		delete(get(entityClass, id));
	}


	
	/**
	 * 根据ID获取一个对象，如果查不到返回null
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getSession().get(entityClass, id);
	}
	
	/**
	 * 获取全部的对象
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> getAll(Class<T> entityClass) {
		return createCriteria(entityClass).list();
	}
	
	/**
	 * 获取全部的对象并指定排序方式
	 * @param <T>
	 * @param entityClass
	 * @param orderBy 排序表达式，如："empName desc"
	 * @return
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy) {
		return find("from " + entityClass.getSimpleName() + " order by " + orderBy);
	}
	
	/**
	 * HQL查询
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return
	 */
	public List find(String queryString, Object... values) {
		return createQuery(queryString, values).list();
	}
	
	/**
	 * HQL查询
	 * @param <T>
	 * @param entityClass 查询结果的类型
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return
	 */
	public <T> List<T> findEntity(Class<T> entityClass, String queryString, Object... values) {
		return find(queryString, values);
	}
	
	/**
	 * HQL查询一个整数
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return 
	 */
	public Integer findInt(String queryString, Object... values) {
		return (Integer) findUnique(queryString, values);
	}

	/**
	 * HQL查询一个长整数
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return 
	 */
	public Long findLong(String queryString, Object... values) {
		return (Long) findUnique(queryString, values);
	}

	/**
	 * HQL唯一查询，仅返回第一个对象
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return 
	 */
	public Object findUnique(String queryString, Object... values) {
		return createQuery(queryString, values).setMaxResults(1).uniqueResult();
	}

	/**
	 * HQL唯一查询，仅返回第一个对象
	 * @param entityClass 查询结果的类型
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return 
	 */
	public <T> T findUniqueEntity(Class<T> entityClass, String queryString, Object... values) {
		return (T) findUnique(queryString, values);
	}

	/**
	 * HQL分面查询
	 * @param <T>
	 * @param entityClass 查询结果的类型
	 * @param page page对象
	 * @param queryString HQL语句
	 * @param values HQL参数
	 * @return
	 */
	public <T> List<T> findPage(Class<T> entityClass, Page page, String queryString, Object... values) {
		int pageNo = page.getInt(Page.PAGE_NO);
		int pageSize = page.getInt(Page.PAGE_SIZE);
		
//		page.put(Page.TOTAL_COUNT, String.valueOf(this.findLong("select count(*) " + queryString.substring(queryString.indexOf("from")))));
//		page.compute();
		
		Query query = createQuery(queryString, values);
		if (page.isPaging()) {
			ScrollableResults scrollableResults = query.scroll(ScrollMode.SCROLL_SENSITIVE);
			scrollableResults.last();
			page.put(Page.TOTAL_COUNT, String.valueOf(scrollableResults.getRowNumber() + 1));
			page.compute();
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		
		return query.list();
	}
	
	/**
	 * 查询一个对象的属性值在数据库中是不是唯一
	 * @param entity 实体对象
	 * @param propertyNames 属性的名称，可多个 如："prop1,prop2"
	 * @return
	 */
	public boolean isUnique(Object entity, String propertyNames) {
		Class clazz = getTrueClass(entity);
		Criteria criteria = createCriteria(clazz).setProjection(
				Projections.rowCount());
		String[] nameList = StringUtils.split(propertyNames, ",");
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
		return (Integer) criteria.uniqueResult() == 0;
	}
	
	public <T> List<T> findByCriteria(Class<T> entityClass, Criterion... criterion) {
		return createCriteria(entityClass, criterion).list();
	}


	public Query createQuery(String queryString, Object... values) {
		Query query = getSession().createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	public Criteria createCriteria(Class entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	public void flush() {
		getSession().flush();
	}
	
	public void clear() {
		getSession().clear();
	}
	
	public void evict(Object object) {
		getSession().evict(object);
	}
	
	

	public Serializable getId(Class entityClass, Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(entityClass));
	}
	
	public String getIdName(Class entityClass) {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
	public Class getTrueClass(Object object) {
		Class clazz = object.getClass();
		String className = clazz.getName();
		int index = className.indexOf("$");
		if (index != -1) {
			className = className.substring(0, index);
			try {
				clazz = Class.forName(className);
			}
			catch(ClassNotFoundException ex) {
				ReflectionUtils.handleReflectionException(ex);
			}
		}
		return clazz;
	}
	
	/**
	 * HQL查询，会通过Page对象动态组装HQL语句，Action黙认调用的方法
	 * @param <T>
	 * @param entityClass 查询结果的类型
	 * @param page page对象
	 * @return
	 */
	public <T> List<T> query(Class<T> entityClass, Page page) {
		return this.query(entityClass, page, page.get(Page.AUTH_PREFIX));
	}
	
	/**
	 * HQL查询，会通过Page对象动态组装HQL语句，Action黙认调用的方法
	 * @param <T>
	 * @param entityClass 查询结果的类型
	 * @param page page对象
	 * @param authPrefix 授权对象的路径表达表，如果授权对象（baseOrg或baseEmp）不在当前对象中，则需要指定授权对象的路径名称，
	 *   如：有如下结构currObj.obj1.obj2，授权对象在obj2中，则表达式应为"obj1_obj2"，授权对象在obj1中，则表达式应为"obj1"
	 * @return
	 */
	public <T> List<T> query(Class<T> entityClass, Page page, String authPrefix) {
		StringBuilder sb = new StringBuilder("from " + entityClass.getSimpleName() + " where 1=1");
		sb.append(makeQueryConditions(entityClass, page, "", authPrefix));
		sb.append(makeQuerySort(entityClass, page, ""));
		
	   	return findPage(entityClass, page, sb.toString());
	}
	
	public void initAuthExp(Class entityClass, Page page, String expression, String authPrefix) {
		String entityName = entityClass.getSimpleName();
		String[] exps = StringUtils.split(expression, ";");
		String keys = "";
		String values = "";
		for (String exp : exps) {

			if (entityName.indexOf("BaseOrg") == 0 && exp.indexOf("baseEmployee") != -1) {
				continue;
			}

			String[] kv = StringUtils.split(exp, "=");
			String key = kv[0];
			String value = page.get(kv[1]);
			
			if (StringUtils.isNotBlank(authPrefix)) {
				int p = key.indexOf("_");
				key = key.substring(0, p + 1) + authPrefix + key.substring(p);
			}

			if (StringUtils.isNotBlank(value)) {
				String[] props = StringUtils.split(key, "_");
				if (props[1].toUpperCase().equals(entityName.toUpperCase())) {
					keys += StringUtils.replace(key, "_" + props[1], "") + " ;";
					values += value + ";";
				}
				else {
					try {
						Class c = entityClass;
						for (int i = 1; i < props.length; i++) {
							c = c.getDeclaredField(props[i]).getType();
						}
						keys +=  key + " ;";
						values += value + ";";
					}
					catch (Exception e) {}
				}
			}
			
		}
		if (!"".equals(keys)) {
			page.put(keys, values);
		}
	}
	
	/**
	 * 生成HQL where语句
	 * @param entityClass
	 * @param page
	 * @param alias 主表别称
	 * @return
	 */
	public String makeQueryConditions(Class entityClass, Page page, String alias) {
		return makeQueryConditions(entityClass, page, alias, null);
	}
	
	/**
	 * 生成HQL where语句
	 * @param entityClass
	 * @param page
	 * @param alias 当前所查询对象的别称
	 * @param authPrefix 授权对象的路径表达表，如果授权对象（baseOrg或baseEmp）不在当前对象中，则需要指定授权对象的路径名称，
	 *   如：有如下结构currObj.obj1.obj2，授权对象在obj2中，则表达式应为"obj1_obj2"，授权对象在obj1中，则表达式应为"obj1"
	 * @return
	 */
	public String makeQueryConditions(Class entityClass, Page page, String alias, String authPrefix) {
		String expressions = page.get(Page.DATA_AUTH);
		if (StringUtils.isNotBlank(expressions)) {
			String[] expArray = StringUtils.split(expressions, ",");
			for (String expression : expArray) {
				initAuthExp(entityClass, page, expression, authPrefix);
			}
		}

		alias = StringUtils.isNotBlank(alias) ? alias + "." : "";
		StringBuilder conditions = new StringBuilder(200);
		Set<String> keys = page.keySet();
		for (String key : keys) {
			String[] val = StringUtils.split(page.get(key), ";");
			if (val == null) {
				continue;
			}
			String[] exp = StringUtils.split(key, ";");
			StringBuilder cond = new StringBuilder(50);
			for (int i = 0; i < exp.length && i < val.length; i++) {
				if (exp[i].indexOf("$") == 0 && StringUtils.isNotEmpty(val[i])) {
					String prefix = exp[i].substring(0, exp[i].indexOf("_"));
					if (Constants.QUERY_EXP.containsKey(prefix)) {
						String name = alias + exp[i].substring(prefix.length() + 1).replaceAll("_", ".");
						String queryExp = Constants.QUERY_EXP.get(prefix);
						if (Constants.PATTERN_DATE.matcher(val[i]).matches()) {
//							name = "to_char(" + name + ",'yyyy-mm-dd')";
							if (prefix.equals("$lt") || prefix.equals("$le")) {
								val[i] = val[i] + " 23:59:59";
							}
						}
						if ("$in".equals(prefix)) {
							String v = "'" + StringUtils.replace(val[i], ",", "','") + "'";
							if (cond.length() == 0) {
								cond.append(" and (").append(name).append(StringUtils.replace(queryExp, "?", v));
							}
							else {
								cond.append(" or ").append(name).append(StringUtils.replace(queryExp, "?", v));
							}
						}
						else {
							String[] values = StringUtils.split(val[i], ",");
							for (String v : values) {
								if (cond.length() == 0) {
									cond.append(" and (").append(name).append(StringUtils.replace(queryExp, "?", v));
								}
								else {
									cond.append(" or ").append(name).append(StringUtils.replace(queryExp, "?", v));
								}
							}
						}
					}
				}
			}
			if (cond.length() > 0) {
				conditions.append(cond.append(")"));
			}
		}
		return conditions.toString();
	}
	
	/**
	 * 生成HQL order语句
	 * @param entityClass
	 * @param page
	 * @param alias 当前所查询对象的别称
	 * @return
	 */
	public String makeQuerySort(Class entityClass, Page page, String alias) {
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			alias = StringUtils.isNotBlank(alias) ? alias + "." : "";
	   		return " order by " + alias + page.get(Page.SORT);
	   	}
		return "";
	}
	
	public void makeParentInfo(Class entityClass, Object object, String descName) {
		String parentName = entityClass.getSimpleName();
		parentName = parentName.substring(0,1).toLowerCase() + parentName.substring(1);
		String idName = getIdName(getTrueClass(object));
		
		try {
			Long levelCode = (Long) PropertyUtils.getProperty(object, "levelCode");
			
			while (true) {
				String desc = StringUtils.toString(PropertyUtils.getProperty(object, descName));
				String currDesc = StringUtils.toString(PropertyUtils.getProperty(object, "pn" + levelCode));
				if (!desc.equals(currDesc)) {
					break;
				}
				
				String id = StringUtils.toString(PropertyUtils.getProperty(object, idName));
				String currId = StringUtils.toString(PropertyUtils.getProperty(object, "pi" + levelCode));
				if (currId!=null && !id.equals(currId)) {
					break;
				}

				String parentId = "";
				Object parent = PropertyUtils.getProperty(object, parentName);
				if (parent != null) {
					parentId = StringUtils.toString(PropertyUtils.getProperty(parent, idName));
				}
				String currParnetId = "";
				if (levelCode > 1) {
					currParnetId = StringUtils.toString(PropertyUtils.getProperty(object, "pi" + (levelCode-1)));
				}
				if (!parentId.equals(currParnetId)) {
					break;
				}
				
				return;
			}
			
			for (int i = 1; i <= 8; i++) {
				PropertyUtils.setProperty(object, "pi" + i, null);
				PropertyUtils.setProperty(object, "pn" + i, null);
			}
			this.updateParentInfo(entityClass, object, descName);
		}
		catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	private void updateParentInfo(Class entityClass, Object object, String descName) {
		String parentName = entityClass.getSimpleName();
		parentName = parentName.substring(0,1).toLowerCase() + parentName.substring(1);
		Object parent = object;
		StringBuilder pnall = new StringBuilder();
		try {
			while (parent != null) {
				Object id = PropertyUtils.getProperty(parent, getIdName(getTrueClass(object)));
				Object desc = PropertyUtils.getProperty(parent, descName);
				String levelCode = PropertyUtils.getProperty(parent, "levelCode") + "";
				
				PropertyUtils.setProperty(object, "pi" + levelCode, id);
				PropertyUtils.setProperty(object, "pn" + levelCode, desc);
//				if (!"1".equals(levelCode) || pnall.length() == 0) {
					if (pnall.length() > 0) {
						pnall.insert(0, "-");
					}
					pnall.insert(0, desc);
//				}
				
				parent = PropertyUtils.getProperty(parent, parentName);
			}
			
			PropertyUtils.setProperty(object, "pnall", pnall.toString());
			
			
			Iterator it = ((Set) PropertyUtils.getProperty(object, parentName + "s")).iterator();
			while (it.hasNext()) {
				updateParentInfo(entityClass, it.next(), descName);
				
			}
			
		}
		catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	
	public void makeSubCode(Class entityClass, Object object) {
		makeSubCode(entityClass, object, "");
	}
	
	public void makeSubCode(Class entityClass, Object object, String whereExp) {
		String parentName = entityClass.getSimpleName();
		parentName = parentName.substring(0,1).toLowerCase() + parentName.substring(1);

		String parentSubCode = "";
		Long parentLevelCode = 0L;
		
		try {
			Object parent = PropertyUtils.getProperty(object, parentName);
			if (parent != null) {
				parentSubCode = (String) PropertyUtils.getProperty(parent, "subCode");
				parentLevelCode = (Long) PropertyUtils.getProperty(parent, "levelCode");
			}

			Object id = PropertyUtils.getProperty(object, getIdName(getTrueClass(object)));
			if (id == null) {
				PropertyUtils.setProperty(object, "subCode", getValidSubCode(getTrueClass(object), parentSubCode, whereExp));
				PropertyUtils.setProperty(object, "levelCode", parentLevelCode + 1);
			}
			else {
				String subCode = (String) PropertyUtils.getProperty(object, "subCode");
				Long levelCode = (Long) PropertyUtils.getProperty(object, "levelCode");
				if (subCode!=null && !parentSubCode.equals("") && parentSubCode.indexOf(subCode) == 0) {
					throw new Exception("不能把自己或自已的下级改变为自己的上级");
				}
				if (subCode == null || levelCode == null || subCode.equals("") || levelCode == 0 || subCode.indexOf(parentSubCode) != 0 || levelCode != (parentLevelCode + 1)) {
					updateSubCode(entityClass, object, whereExp);
				}
			}
		}
		catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	public void updateSubCode(Class entityClass, Object object) {
		updateSubCode(entityClass, object, "");
	}
	
	public void updateSubCode(Class entityClass, Object object, String whereExp) {
		String parentName = entityClass.getSimpleName();
		parentName = parentName.substring(0,1).toLowerCase() + parentName.substring(1);
		String parentSubCode = "";
		Long parentLevelCode = 0L;
		
		try {
			Object parent = PropertyUtils.getProperty(object, parentName);
			if (parent != null) {
				parentSubCode = (String) PropertyUtils.getProperty(parent, "subCode");
				parentLevelCode = (Long) PropertyUtils.getProperty(parent, "levelCode");
			}
			PropertyUtils.setProperty(object, "subCode", getValidSubCode(getTrueClass(object), parentSubCode, whereExp));
			PropertyUtils.setProperty(object, "levelCode", parentLevelCode + 1);
			save(object);
			
			Iterator it = ((Set) PropertyUtils.getProperty(object, parentName + "s")).iterator();
			while (it.hasNext()) {
				updateSubCode(entityClass, it.next(), whereExp);
			}

		}
		catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	public String getValidSubCode(Class entityClass, String parentSubCode) {
		return getValidSubCode(entityClass, parentSubCode, "");
	}
	
	public String getValidSubCode(Class entityClass, String parentSubCode, String whereExp) {
		if (StringUtils.isNotBlank(whereExp)) {
			whereExp = "and " + whereExp;
		}
		else {
			whereExp = "";
		}
		String queryString = "select subCode from " + entityClass.getSimpleName() + " where subCode like '" + parentSubCode + "___' " + whereExp + " order by subCode";
		Iterator it = this.find(queryString).iterator();
		String code = null;
		for (int index = 0; index < 999; index++) {
			if  (it.hasNext()) {
				String c = ((String) it.next());
				c = c.substring(c.length() - 3);
				
				if (index != Integer.parseInt(c)) {
					code = NumberUtils.format(index, "000");
					break;
				}
			}
			else {
				code = NumberUtils.format(index, "000");
				break;
			}
		}
		return parentSubCode + code;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 生成SQL授权where语句by组织
	 * @param page
	 * @param alias 组织授权表的别称
	 * @return
	 */
	public String makeSqlAuthExpByOrg(Page page, String alias) {
		if (StringUtils.isBlank(alias)) {
			alias = "";
		}
		else {
			alias = alias + ".";
		}
		StringBuilder exp = new StringBuilder("");
		
		int authLevel = Integer.parseInt(page.get(Page.DATA_AUTH_LEVEL));
		if (authLevel == 2) {
		}
		else if (authLevel == 3) {
		}
		else if (authLevel == 4) {
			String[] codes = page.get(Page.ORG_SUBCODE_KEY).split("\\,");
			for (String code : codes) {
				if (exp.length() == 0) {
					exp.append(" and (");
				}
				else {
					exp.append(" or ");
				}
				exp.append(alias).append("sub_code like '").append(code).append("%'");
			}
			exp.append(")");
		}
		else if (authLevel == 5) {
			exp.append(" and ").append(alias).append("org_id in (").append(page.get(Page.ORG_ID_KEY)).append(")");
		}
		else if (authLevel == 99) {
			if (StringUtils.isNotBlank(page.get(Page.ORG_AUTH_KEY))) {
				exp.append(" and ").append(alias).append("org_id in (").append(page.get(Page.ORG_AUTH_KEY)).append(")");
			}
		}
		return exp.toString();
		
	}
	
	/**
	 * 生成SQL授权where语句by人员
	 * @param page
	 * @param alias 人员授权表的别称
	 * @return
	 */
	public String makeSqlAuthExpByEmp(Page page, String alias) {
		if (StringUtils.isBlank(alias)) {
			alias = "";
		}
		else {
			alias = alias + ".";
		}
		String exp = "";
		int authLevel = Integer.parseInt(page.get(Page.DATA_AUTH_LEVEL));
		if (authLevel == 2) {
			exp += " and " + alias + "sub_code like '" + page.get(Page.PARENT_EMP_SUBCODE_KEY) + "%'";
		}
		else if (authLevel == 3) {
			exp += " and " + alias + "level_code>=" + page.get(Page.EMP_LEVELCODE_KEY);
		}
		else if (authLevel == 4) {
			exp += " and " + alias + "sub_code like '" + page.get(Page.EMP_SUBCODE_KEY) + "%'";
		}
		else if (authLevel == 5) {
			exp += " and " + alias + "emp_id=" + page.get(Page.EMP_ID_KEY);
		}
		else if (authLevel == 99) {
			if (StringUtils.isNotBlank(page.get(Page.EMP_AUTH_KEY))) {
				exp += " and " + alias + "emp_id in (" + page.get(Page.EMP_AUTH_KEY) + ")";
			}
		}
		return exp;
	}
	
	/**
	 * 生成SQL授权where语句by产品
	 * @param page
	 * @param alias 产品授权表的别称
	 * @return
	 */
	public String makeSqlAuthExpByProd(Page page, String alias) {
		return null;
	}
	
	/**
	 * 生成SQL order语句
	 * @param page
	 * @param alias 当前所查询对象的别称
	 * @return
	 */
	public String makeSqlSortExp(Page page, String alias) {
		return this.makeQuerySort(null, page, alias);
	}
	
	/**
	 * 执行SQL查询，返加一个Object[]类型的List
	 * @param sql
	 * @return List<Object[]>
	 */
	public List<Object[]> executeSqlQuery(String sql) {
		return getSqlList(null, sql);
	}
	
	/**
	 * 执行SQL查询，返加一个指定类型entityClass的List
	 * @param sql
	 * @return List<T>
	 */
	public <T> List<T> executeSqlQuery(Class<T> entityClass, String sql) {
		return getSqlList(entityClass, sql);
	}
	
	/**
	 * 执行唯一记录的SQL查询，返加一个Object[]类型的对象
	 * @param sql
	 * @return Object[]
	 */
	public Object[] executeUniqueSqlQuery(String sql) {
		Object[] object = null;
    	Iterator<Object[]> it = executeSqlQuery(sql).iterator();
    	if (it.hasNext()) {
    		object = it.next();
    	}
        return object;
	}
	
	/**
	 * 执行唯一记录的SQL查询，返加一个指定类型entityClass的对象
	 * @param entityClass
	 * @param sql
	 * @return T
	 */
	public <T> T executeUniqueSqlQuery(Class<T> entityClass, String sql) {
		T object = null;
    	Iterator<T> it = executeSqlQuery(entityClass, sql).iterator();
    	if (it.hasNext()) {
    		object = it.next();
    	}
        return object;
	}
	
	/**
	 * 执行分页的SQL查询，返加一个Object[]类型的List
	 * @param sql
	 * @param page
	 * @return List<Object[]>
	 */
	public List<Object[]> executeSqlQuery(String sql, Page page) {
		return getSqlList(null, sql, page);
	}

	/**
	 * 执行分页的SQL查询，返加一个指定类型entityClass的List
	 * @param sql
	 * @param page
	 * @return List<T>
	 */
	public <T> List<T> executeSqlQuery(Class<T> entityClass, String sql, Page page) {
		return getSqlList(entityClass, sql, page);
	}
	
	/**
	 * 执行一个SQL，update或insert
	 * @param sql
	 * @return update或insert的记录数
	 */
	public int executeSqlUpdate(String sql) {
		int n = 0;
		Statement stat = null;
		try {
			stat = getSession().connection().createStatement();
			n = stat.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new BusinessException(e.getMessage() + ":" + sql);
		}
		finally {
			try {
				if (stat != null) {
					stat.close();
				}
			}
			catch(SQLException e) {}
		}
		return n;
	}
	
	public void executeCall(String procedure) {
		CallableStatement stat = null;
		try {
			stat = getSession().connection().prepareCall(procedure);
			stat.executeUpdate();
		} 
		catch (SQLException e) {
			throw new BusinessException(e.getMessage() + ":" + procedure);
		}
		finally {
			try {
				if (stat != null) {
					stat.close();
				}
			}
			catch(SQLException e) {}
		}
	}
	
	private <T> List getSqlList(Class<T> entityClass, String sql) {
		List list = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = getSession().connection().createStatement();
			rs = stat.executeQuery(sql);
			if (entityClass == null) {
				list = JdbcProxy.getObjectArrayList(rs);
			}
			else {
				list = JdbcProxy.getEntityList(rs, entityClass);
			}
			
		}
		catch (SQLException e) {
			throw new BusinessException(e.getMessage() + ":" + sql);
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	private <T> List getSqlList(Class<T> entityClass, String sql, Page page) {
		List list = null;
		int pageNo = page.getInt(Page.PAGE_NO);
		int pageSize = page.getInt(Page.PAGE_SIZE);
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = getSession().connection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(sql);
			rs.last();
			page.put(Page.TOTAL_COUNT, String.valueOf(rs.getRow()));
			page.compute();
			if ((pageNo - 1) * pageSize == 0) {
				rs.beforeFirst();
			}
			else {
				rs.absolute((pageNo - 1) * pageSize);
			}
			
			if (entityClass == null) {
				list = JdbcProxy.getObjectArrayList(rs, page.getLong(Page.PAGE_SIZE));
			}
			else {
				list = JdbcProxy.getEntityList(rs, page.getLong(Page.PAGE_SIZE), entityClass);
			}
		}
		catch (SQLException e) {
			throw new BusinessException(e.getMessage() + ":" + sql);
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	

	/**
	 * 预执行一个SQL，update或insert
	 */
	@SuppressWarnings("deprecation")
	public int executeSqlUpdate(String sql, Object[] values) {
		int n = 0;
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = getSession().connection();
			stat = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; i++) {
				stat.setObject((i + 1), values[i]);
			}
			n = stat.executeUpdate();
		} catch (SQLException e) {
			throw new BusinessException(e.getMessage() + ":" + sql);
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		return n;
	}

	/**
	 * 预执行一个SQL，select
	 */
	@SuppressWarnings({ "deprecation"})
	public <T> List<T> executeSqlQuery(Class<T> entityClass, String sql,
			Object[] values) {
		List<T> list = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = getSession().connection().prepareStatement(sql);
			for (int i = 0; i < values.length; i++) {
				stat.setObject((i + 1), values[i]);
			}
			rs = stat.executeQuery();
			if (entityClass == null) {
				list = (List<T>) JdbcProxy.getObjectArrayList(rs);
			} else {
				list = JdbcProxy.getEntityList(rs, entityClass);
			}
		} catch (SQLException e) {
			throw new BusinessException(e.getMessage() + ":" + sql);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	/**
	 * 预执行SQL查询，返加一个Object[]类型的List
	 * @param sql
	 * @return List<Object[]>
	 */
	public List<Object[]> executeSqlQuery(String sql,Object[] values) {
		return executeSqlQuery(null, sql,values);
	}
}
