package com.winchannel.base.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.filter.ExportFilterUtils;

import com.winchannel.core.bean.ExpParam;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.dao.HibernateBaseDao;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;

public class BaseReportManager extends HibernateBaseDao {
	
	public static final Log log = LogFactory.getLog("ReportManager");
	
	public List<Object[]> query(Page page, ReportConfigurator configurator,HttpServletRequest request) {
		page.put("_orgAuthExp", this.makeSqlAuthExpByOrg(page, "org"));
		page.put("_empAuthExp", this.makeSqlAuthExpByEmp(page, "emp"));
		
		String sql = configurator.getSql();
		
		List<ExpParam> params = configurator.getParams();
		for (ExpParam param : params) {
			if (StringUtils.isNotBlank(param.getText())) {
				
				String value = StringUtils.toString(page.get(param.getId()));
				if ("".equals(value)) {
					value = StringUtils.toString(param.getDefaultValue());
				}
				
				if (!"".equals(value)) {
					if (StringUtils.isNotBlank(param.getValueList())) {
						value = (param.getValueList().split("~"))[Integer.valueOf(value)];
					}
					
					if ("char".equals(param.getDataType())) {
						value = "'" + StringUtils.replace(value, ",", "','") + "'";
					}

					if (StringUtils.isNotBlank(param.getTemplate())) {
						value = StringUtils.replace(param.getTemplate(), param.getText(), value);
					}

				}
				
				sql = StringUtils.replace(sql, param.getText(), value);
				
			}
		}
		
		if (StringUtils.isNotBlank(page.get(Page.SORT))) {
			String[] s = StringUtils.split(page.get(Page.SORT), " ");
			sql += " order by " + (Integer.parseInt(s[0]) + 1) + " " + s[1];
		}

		HttpServletRequestContext context = new HttpServletRequestContext((HttpServletRequest) request);
		
		boolean isExport = isExport(page,context);
		if(isExport){
			log.info("执行报表导出!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		
		if (configurator.isShowSql()) {
			log.info(sql);
		}
	
		
		List<Object[]> list = null;
		if (configurator.isPaging()
				//导出功能不能使用分页
				&&!isExport) {
			list = this.executeSqlQuery(sql, page);
		}
		else {
			if (configurator.getCacheTime() > 10000) {
				list = getCacheData(sql, configurator.getCacheTime());
				if (list == null) {
					list = this.executeSqlQuery(sql);
					if (list.size() < 5000) {
						putCacheData(sql, list);
					}
				}
			}
			else {
				//没有使用ectable的分页
				if(page.get(ExportFilterUtils.getTableId(context)+"_"+TableConstants.ROWS_DISPLAYED )==null
						//导出功能
						||isExport
						){
					list = this.executeSqlQuery(sql);
				}else{
					list = this.executeSqlQuery(sql,page);
				}
			}
		}

		return list;
	}
	
	boolean isExport(Page page,HttpServletRequestContext context){
		//ectable方法导出
		return ExportFilterUtils.isExported(context)
				//平台方法导出
				||StringUtils.isNotBlank(page.get("view"));
	}
	
	boolean notBlank(String str){
		return str!=null&&str.length()>0;
	}
	
	
	private static long cacheSize = 100;
	private static Map<String, List<Object[]>> cacheDatas = new HashMap<String, List<Object[]>>();
	private static Map<String, Long> cacheTimes = new HashMap<String, Long>();
	
	private synchronized List<Object[]> getCacheData(String key, long cacheTime) {
		Long time = cacheTimes.get(key);
		if (time != null) {
			if ((System.currentTimeMillis() - time) < cacheTime) {
				return cacheDatas.get(key);
			}
			else {
				cacheTimes.remove(key);
				cacheDatas.remove(key);
			}
		}
		return null;
	}
	private synchronized void putCacheData(String key, List<Object[]> value) {
		cacheDatas.put(key, value);
		cacheTimes.put(key, System.currentTimeMillis());
		if (cacheDatas.size() > cacheSize) {
			String minKey = null;
			long minVal = Long.MAX_VALUE;
			for (Iterator<String> it = cacheTimes.keySet().iterator(); it.hasNext();) {
				String k = it.next();
				long v = cacheTimes.get(k);
				if (v < minVal) {
					minKey = k;
					minVal = v;
				}
			}
			cacheTimes.remove(minKey);
			cacheDatas.remove(minKey);
		}
	}

}
