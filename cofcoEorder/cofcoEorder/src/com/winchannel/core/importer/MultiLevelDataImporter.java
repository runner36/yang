package com.winchannel.core.importer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.winchannel.core.bean.DataField;
import com.winchannel.core.importer.iterator.DataIterator;
import com.winchannel.core.utils.StringUtils;

/**
 * @author xianghui
 *
 */
public class MultiLevelDataImporter extends AbstractHibernateImporter {
	
	protected Map<String, Map<String, String>> rowMap = new LinkedHashMap<String, Map<String, String>>();
	protected Map<String, Map<String, String>> result = new LinkedHashMap<String, Map<String, String>>();
	
	protected DataField parentKeyField;
	protected DataField KeyField;
	protected String parentKeyName;
	protected String keyName;
	
	protected int initErrNum=0;
	protected StringBuilder initErrors=new StringBuilder();
	
	protected String name;

	public MultiLevelDataImporter(ImpConfigurator conf, Map<String, Object> params) throws Exception {
		super(conf, params);
		parentKeyField = conf.getMlParentKey();
		KeyField = conf.getMlKey();
		parentKeyName = parentKeyField.getName();
		keyName = KeyField.getName();
		
		if (conf.getEntityClass().getSimpleName().equals("BaseOrg")) {
			name = "orgName";
		}
		else if (conf.getEntityClass().getSimpleName().equals("BaseEmployee")) {
			name = "empName";
		}
		else if (conf.getEntityClass().getSimpleName().equals("BaseDictItem")) {
			name = "itemName";
		}
	}

	@Override
	protected void setIterator(DataIterator iterator) throws Exception {
		while (iterator.hasNext()) {
			Map<String, String> row = iterator.next();
			if ((iterator.getIndex()) >= conf.getStartRow()) {
				String key = row.get(keyName);
				if (StringUtils.isNotBlank(key)) {
					if(key.equals(row.get(parentKeyName))){
						initErrNum++;
						initErrors.append(" 行:"+iterator.getIndex()+"值:("+key+") 自己不能为自己的上级 <br>");
					}else{
						rowMap.put(key, row);
					}
//					System.out.println("    put : " + row);
				}
				else {
					result.put(UUID.randomUUID().toString(), row);
//					throw new RuntimeException("Data Validate Failure : " + KeyField.getTitle() + " is required");
				}
			}
			else {
				result.put(UUID.randomUUID().toString(), row);
			}
		}
		if (initErrNum > 0) {
			throw new Exception(initErrors.toString());
		}
//		System.out.println(rowMap);

		for (Iterator<Map<String, String>> iter = rowMap.values().iterator(); iter.hasNext();) {
			addRow(iter.next());
		}
		
//		System.out.println(result);

		this.iterator = new DataIterator() {
			private Iterator<Map<String, String>> iter = result.values().iterator();
			private int index = 0;
			public int getIndex() {
				return index;
			}
			public boolean hasNext() {
				return iter.hasNext();
			}
			public Map<String, String> next() {
				index++;
				return iter.next();
			}
			public void remove() {
				iter.remove();
			}
		};
		
	}
	
	private void addRow(Map<String, String> row) {
		if (!result.containsKey(row.get(keyName))) {
			Map<String, String> parentRow = rowMap.get(row.get(parentKeyName));
			if (parentRow != null) {
				addRow(parentRow);
			}
			result.put(row.get(keyName), row);
		}
	}

	@Override
	protected void save(Object object) {
		if (persister == null) {
			return;
		}
		super.persister.makeSubCode(conf.getEntityClass(), object);
		super.persister.save(object);
		super.persister.flush();
		
		if (name != null) {
			super.persister.makeParentInfo(conf.getEntityClass(), object, name);
		}
		super.persister.evict(object);
	}
	
}
