package com.winchannel.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winchannel.base.model.BaseMessRes;
import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.utils.ObjectUtils;
import com.winchannel.core.utils.StringUtils;

public class BaseMessResManager extends HibernateEntityDao<BaseMessRes> {
	
	public ListColumn[] getColumns(String language, String reportName, String param) {
		ListColumn[] columns = (ListColumn[]) ObjectUtils.clone(ReportConfigurator.getInstance(reportName).getColumns());
		Map<String, ListColumn> colMap = new HashMap<String, ListColumn>();
		for (ListColumn column : columns) {
			colMap.put(column.getProperty(), column);
		}
		
		String bundle = reportName;
		if (StringUtils.isNotBlank(param)) {
			bundle += "_" + param;
		}
		List<BaseMessRes> messRes = this.getMessRes(language, bundle);
		for (BaseMessRes res : messRes) {
			ListColumn column = colMap.get(res.getId().getMessKey());
			column.setSort(res.getSort());
			column.setState(res.getState());
			if (StringUtils.isNotBlank(res.getMessValue())) {
				column.setNewTitle(res.getMessValue());
			}
		}
		
		int len = columns.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				int ii = columns[i].getSort() != null ? columns[i].getSort().intValue() : 9999999;
				int jj = columns[j].getSort() != null ? columns[j].getSort().intValue() : 9999999;
				if (ii > jj) {
					ListColumn t = columns[i];
					columns[i] = columns[j];
					columns[j] = t;
				}
			}
		}
		
		return columns;
		
	}
	
	public List<BaseMessRes> getMessRes(String language, String bundle) {
		return this.findEntity("from BaseMessRes where id.messLanguage=? and id.messBundle=?", language, bundle);
	}

}
