package com.winchannel.core.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.winchannel.base.web.ReportColumnSource;
import com.winchannel.core.bean.ExpParam;
import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.bean.ListCondition;
import com.winchannel.core.utils.StringUtils;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ReportConfigurator {
	
	private static final Log log = LogFactory.getLog(ReportConfigurator.class);
	
	private String reportName;
	private String condition;
	private String title;
	private String titleBundle;
	private String titleKey;
	private ListColumn[] columns;
	private ListCondition[] conditions;
	private List<ExpParam> params;
	private String sql;
	
	private boolean showSql;
	private long cacheTime;
	private boolean firstQuery;
	private boolean paging;
	private boolean sortable;
	private String forword;
	
	/**是否显示排列按钮*/
	private boolean showSort;
	
	private long lastModified;
	
	private static Map<String, ReportConfigurator> instances = new HashMap<String, ReportConfigurator>();
	
	/*private ReportConfigurator(String reportName, File file) {
		this.reportName = reportName;
		init(file);
	}*/
	private ReportConfigurator(String reportName, InputStream is) {
		this.reportName = reportName;
		init(is);
	}
	public static synchronized ReportConfigurator getInstance(String reportName, File file) throws FileNotFoundException {
		ReportConfigurator conf = instances.get(reportName);
		if (conf == null || conf.getLastModified() != file.lastModified()) {
			conf = new ReportConfigurator(reportName, new FileInputStream(file));
			instances.put(reportName, conf);
			conf.setLastModified(file.lastModified());
		}
		return conf;
	}
	
	
	
	public static synchronized ReportConfigurator getInstance(String reportName) {
		return instances.get(reportName);
	}
	
	public static synchronized ReportConfigurator getInstance(String reportName, InputStream is) {
		ReportConfigurator conf = new ReportConfigurator(reportName, is);
		instances.put(reportName, conf);
		return conf;
	}
	public void init(InputStream is) {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(is);
			Element root = doc.getRootElement();
			this.showSql = root.attributeValue("showSql") == null ? false : Boolean.parseBoolean(root.attributeValue("showSql"));
			this.cacheTime = root.attributeValue("cacheTime") == null ? 0L : Long.parseLong(root.attributeValue("cacheTime"));
			this.firstQuery = root.attributeValue("firstQuery") == null ? false : Boolean.parseBoolean(root.attributeValue("firstQuery"));
			this.paging = root.attributeValue("paging") == null ? false : Boolean.parseBoolean(root.attributeValue("paging"));
			this.sortable = root.attributeValue("sortable") == null ? false : Boolean.parseBoolean(root.attributeValue("sortable"));
			this.forword = root.attributeValue("forword");
			
			this.showSort=root.attributeValue("showSort") == null ? true : Boolean.parseBoolean(root.attributeValue("showSort"));
			
			this.title = root.elementText("title");
			//Title 国际化
			Element titleElement=root.element("title");
			this.titleBundle=StringUtils.toString(titleElement.attributeValue("bundle"));
			this.titleKey=StringUtils.toString(titleElement.attributeValue("key"));
			//必要条件选择
			List conditionList = root.elements("condition");
			if(conditionList.size()>0){
				this.conditions=new ListCondition[conditionList.size()];
				Iterator conditionIt = conditionList.iterator();
				for (int i = 0; conditionIt.hasNext(); i++) {
					Element e = (Element) conditionIt.next();
					ListCondition condition = new ListCondition();
					condition.setProperty(StringUtils.toString(e.attributeValue("property")));
					condition.setTitle(StringUtils.toString(e.attributeValue("title")));
					condition.setBundle(StringUtils.toString(e.attributeValue("bundle")));
					condition.setKey(StringUtils.toString(e.attributeValue("key")));
					this.conditions[i]=condition;				
				}
			}			
			
			Element header = root.element("header");
			if (header != null) {
				List columnList = header.elements("column");
				this.columns = new ListColumn[columnList.size()];
				Iterator columnIt = columnList.iterator();
				for (int i = 0; columnIt.hasNext(); i++) {
					Element e = (Element) columnIt.next();
					ListColumn column = new ListColumn();
					column.setAlias(StringUtils.toString(e.attributeValue("alias")));
					column.setCalc(StringUtils.toString(e.attributeValue("calc")));
					column.setCalcTitle(StringUtils.toString(e.attributeValue("calcTitle")));
					column.setCell(StringUtils.toString(e.attributeValue("cell")));
					column.setEscapeAutoFormat(StringUtils.toString(e.attributeValue("escapeAutoFormat")));
					column.setFilterable(StringUtils.toString(e.attributeValue("filterable")));
					column.setFilterCell(StringUtils.toString(e.attributeValue("filterCell")));
					column.setFilterClass(StringUtils.toString(e.attributeValue("filterClass")));
					column.setFilterOptions(StringUtils.toString(e.attributeValue("filterOptions")));
					column.setFilterStyle(StringUtils.toString(e.attributeValue("filterStyle")));
					column.setFormat(StringUtils.toString(e.attributeValue("format")));
					column.setHeaderCell(StringUtils.toString(e.attributeValue("headerCell")));
					column.setHeaderClass(StringUtils.toString(e.attributeValue("headerClass")));
					column.setHeaderStyle(StringUtils.toString(e.attributeValue("headerStyle")));
					column.setInterceptor(StringUtils.toString(e.attributeValue("interceptor")));
					column.setParse(StringUtils.toString(e.attributeValue("parse")));
					column.setProperty(StringUtils.toString(e.attributeValue("property")));
					column.setSortable(StringUtils.toString(e.attributeValue("sortable")));
					column.setStyle(StringUtils.toString(e.attributeValue("style")));
					column.setStyleClass(StringUtils.toString(e.attributeValue("styleClass")));
					column.setTitle(StringUtils.toString(e.attributeValue("title")));
					column.setValue(StringUtils.toString(e.attributeValue("value")));
					column.setViewsAllowed(StringUtils.toString(e.attributeValue("viewsAllowed")));
					column.setViewsDenied(StringUtils.toString(e.attributeValue("viewsDenied")));
					column.setWidth(StringUtils.toString(e.attributeValue("width")));
					column.setAlign(StringUtils.toString(e.attributeValue("align")));
					
					column.setBundle(StringUtils.toString(e.attributeValue("bundle")));
					column.setKey(StringUtils.toString(e.attributeValue("key")));
					this.columns[i] = column;
				}
				
				List<ListColumn> listColumn=new ArrayList<ListColumn>(Arrays.asList(columns));
				//动态加入的列 元素
				List<ListColumn> columnsList = header.elements("columns");
				if(columnsList!=null&&!columnsList.isEmpty()){
					Iterator<ListColumn> columnsIt = columnsList.iterator();
					for (int i = 0; columnsIt.hasNext(); i++) {
						Element e = (Element) columnsIt.next();
						//插入的位置
						int insertIndex = NumberUtils.toInt(e.attributeValue("insertIndex"));
					
						//动态 的列资源
						String columnsSource = e.attributeValue("columnsSource");
						
						ReportColumnSource columnsSourceInstance=(ReportColumnSource) Class.forName(columnsSource).newInstance();
						
						//获得动态加入的列
						List<ListColumn> queryColumns = columnsSourceInstance.doGetColumns(e);
						
						int size = queryColumns.size();
						
						List<ListColumn> beforePart = new ArrayList<ListColumn>(beforePart(listColumn, insertIndex));
						List<ListColumn> afterPart = new ArrayList<ListColumn>(afterPart(listColumn, insertIndex));
						
						for (int j = 0; j < size; j++) {//前半部分
							ListColumn column = queryColumns.get(j);
							column.setProperty((insertIndex+j)+"");
							beforePart.add(column);
						}
						
						
						for (int j = 0; j < afterPart.size(); j++) {//后半部分
							ListColumn column = afterPart.get(j);
							column.setProperty((size+NumberUtils.toInt(column.getProperty()))+"");
							beforePart.add(column);
						}
						
						listColumn=beforePart;
					}
				}
				this.columns = listColumn.toArray(new ListColumn[0]);
			}
			
			this.params = buildParams(root.element("content").elementIterator("param"));
			
			this.sql = root.element("content").elementText("sql");
			
			//this.lastModified = file.lastModified();
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}
	
	List<ListColumn> beforePart(List<ListColumn> list,int insertIndex){
		return list.subList(0, insertIndex);
	}
	
	List<ListColumn> afterPart(List<ListColumn> list,int insertIndex){
		return list.subList(insertIndex, list.size());
	}
	
	public static List<ExpParam> buildParams(Iterator iterator) {
		List<ExpParam> params = new ArrayList<ExpParam>();
		while (iterator.hasNext()) {
			Element e = (Element) iterator.next();
			
			ExpParam param = new ExpParam();
			param.setId(e.attributeValue("id"));
			param.setName(e.attributeValue("name"));
			param.setTemplate(e.attributeValue("template"));
			param.setInitValue(e.attributeValue("initValue"));
			param.setOffset(StringUtils.isNotBlank(e.attributeValue("offset")) ? Integer.parseInt(e.attributeValue("offset")) : 0);
			param.setDefaultValue(e.attributeValue("defaultValue"));
			param.setText(e.getText());
			param.setValueList(e.attributeValue("valueList"));
			param.setDataType(e.attributeValue("dataType"));
			param.setFormat(e.attributeValue("format"));
			param.setDisplayName(e.attributeValue("displayName"));
			param.setIsDisplay(e.attributeValue("isDisplay"));
			
			params.add(param);
		}
		return params;
	}
	
	public String generateSql(Map<String, String> values) {
		return this.generateSql(sql, values);
	}
	
	public String generateSql(String sql, Map<String, String> values) {
		String result = sql;
		for (ExpParam param : params) {
			if (StringUtils.isNotBlank(param.getText())) {
				
				String value = StringUtils.toString(values.get(param.getId()));
				if ("".equals(value)) {
					value = StringUtils.toString(param.getDefaultValue());
				}
				
				if (!"".equals(value)) {
					if ("char".equals(param.getDataType())) {
						value = "'" + StringUtils.replace(value, ",", "','") + "'";
					}

					if (StringUtils.isNotBlank(param.getTemplate())) {
						value = StringUtils.replace(param.getTemplate(), param.getText(), value);
					}

					if (StringUtils.isNotBlank(param.getValueList())) {
						value = (param.getValueList().split("~"))[Integer.valueOf(value)];
					}
				}
				
				result = StringUtils.replace(result, param.getText(), value);
				
			}
		}
		return result;
	}
	
	public String getParamValue(String name) {
		Iterator<ExpParam> it = this.params.iterator();
		while (it.hasNext()) {
			ExpParam param = it.next();
			if (name.equals(param.getId())) {
				if (StringUtils.isNotBlank(param.getInitValue())) {
					return param.getInitValue();
				}
				if (StringUtils.isNotBlank(param.getDefaultValue())) {
					return param.getDefaultValue();
				}
			}
		}
		return null;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public List<ExpParam> getParams() {
		return params;
	}

	public void setParams(List<ExpParam> params) {
		this.params = params;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	public boolean isFirstQuery() {
		return firstQuery;
	}

	public void setFirstQuery(boolean firstQuery) {
		this.firstQuery = firstQuery;
	}
	

	public boolean isShowSort() {
		return showSort;
	}
	public void setShowSort(boolean showSort) {
		this.showSort = showSort;
	}
	public boolean isShowSql() {
		return showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public ListColumn[] getColumns() {
		return columns;
	}

	public void setColumns(ListColumn[] columns) {
		this.columns = columns;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getForword() {
		return forword;
	}

	public String getTitleBundle() {
		return titleBundle;
	}

	public void setTitleBundle(String titleBundle) {
		this.titleBundle = titleBundle;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}

	public ListCondition[] getConditions() {
		return conditions;
	}

	public void setConditions(ListCondition[] conditions) {
		this.conditions = conditions;
	}

	
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		List<String> listA = list.subList(0, 3);
		for (String string : listA) {
			System.out.println(string);
		}
		System.out.println("==");
		List<String> listB = list.subList(3, list.size());
		for (String string : listB) {
			System.out.println(string);
		}
	}
}
