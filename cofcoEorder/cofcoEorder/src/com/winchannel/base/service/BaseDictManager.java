package com.winchannel.base.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.winchannel.base.model.BaseDict;
import com.winchannel.base.model.BaseDictItem;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.StringUtils;

public class BaseDictManager extends HibernateEntityDao<BaseDictItem> {
	
	private static final Map<String, Map<String, BaseDictItem>> dicts = new HashMap<String, Map<String, BaseDictItem>>();
	private static final Map<String, BaseDictItem> items = new HashMap<String, BaseDictItem>();
	
	public BaseDictManager() {
//		this.setSessionFactory((SessionFactory) SpringContext.getBean("sessionFactory"));
//		init();
	}
	
	public void init() {
		Iterator<BaseDict> it = getAllDict().iterator();
		while (it.hasNext()) {
			dicts.put(it.next().getDictId(), new HashMap<String, BaseDictItem>());
		}
		
		List<BaseDictItem> dictItems = getAll(BaseDictItem.class);
		for (BaseDictItem item : dictItems) {
			updateCache(item);
		}
	}
	
	public void updateCache(BaseDictItem item) {
		dicts.get(item.getBaseDict().getDictId()).put(item.getDictItemId().toString(), item);
		items.put(item.getDictItemId().toString(), item);
	}
	
	public List<BaseDict> getAllDict() {
		return getAll(BaseDict.class, "sort");
	}
	
	@Override
	public BaseDictItem get(Serializable id) {
		return super.get(id);
		/*return items.get(id.toString());*/
	}
	
	@Override
	public void save(Object obj) {
		if (!this.isUnique(obj, "baseDict,itemCode")) {
			//throw new BusinessException("编码重复，保存失败");
			throw new BusinessException("base","common.codeDuplication");
		}
		this.makeSubCode(obj);
		super.save(obj);
		this.makeParentInfo(obj, "itemName");
		
		/*updateCache((BaseDictItem) obj);*/
	}
	
	@Override
	public void delete(Object obj) {
		super.delete(obj);
		
		/*BaseDictItem item = (BaseDictItem) obj;
		dicts.get(item.getBaseDict().getDictId()).remove(item.getDictItemId().toString());
		items.remove(item.getDictItemId().toString());*/
	}
	
	public List<BaseDictItem> getItems(String dictId, String state) {
		/*BaseDictItem[] itemArray = dicts.get(dictId).values().toArray(new BaseDictItem[dicts.get(dictId).size()]);
		for (int i = 0; i < itemArray.length; i++) {
			for (int j = 1; j < itemArray.length; j++) {
				if ((itemArray[i].getLevelCode() * -100000 + itemArray[i].getSort()) < (itemArray[j].getLevelCode() * -100000 + itemArray[j].getSort())) {
					BaseDictItem temp = itemArray[i];
					itemArray[i] = itemArray[j];
					itemArray[j] = temp;
				}
			}
		}
		
		List<BaseDictItem> list = new ArrayList<BaseDictItem>();
		for (BaseDictItem item : itemArray) {
			if (state.equals(item.getState())) {
				list.add(item);
			}
		}
		
		return list;*/
		
		if (StringUtils.isNotBlank(state)) {
			return findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId=? and state=" + state + "order by levelCode,sort", dictId);
		}
		else {
			return findEntity(BaseDictItem.class, "from BaseDictItem where baseDict.dictId=? order by levelCode,sort", dictId);
		}
	}
	
	public List<BaseDictItem> getItems(String dictId) {
		return getItems(dictId, "1");
	}
	
	
	
	public void dictsave(List<BaseDict> insertList, List<BaseDict> updateList) {
		for (Iterator<BaseDict> it = insertList.iterator(); it.hasNext();) {
			getSession().save(it.next());
		}
		for (Iterator<BaseDict> it = updateList.iterator(); it.hasNext();) {
			BaseDict dict = it.next();
			if (dict.isUpdate()) {
				getSession().update(dict);
			}
			else {
				getSession().delete(dict);
			}
		}
	}

} 