package com.winchannel.base.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winchannel.base.utils.Constants;
import com.winchannel.core.bean.ListColumn;
import com.winchannel.core.bean.TreeNode;
import com.winchannel.core.conf.ReportConfigurator;
import com.winchannel.core.utils.MessageResourceUtils;
import com.winchannel.core.utils.Page;


public class TreeReportAction extends ReportAction {
	@Override
	protected void init() {
		this.setColumnStartIndex(2);
		super.init();
	}

	@Override
	protected void onInitData(Page page, ReportConfigurator configurator, HttpServletRequest request, List list) {
		if (list.size() == 0) {
			return;
		}
		
		Map<String, TreeNode<Object[]>> map = new HashMap<String, TreeNode<Object[]>>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			String id = obj[1] + "";
			map.put(id, new TreeNode<Object[]>(obj));
		}
		
		TreeNode<Object[]> root = new TreeNode<Object[]>(null);
		for (Iterator it = list.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			String parnetId = obj[0] + "";
			String id = obj[1] + "";
			
			TreeNode<Object[]> parent = map.get(parnetId);
			TreeNode<Object[]> self = map.get(id);
			
			if (parent != null) {
				parent.addChild(self);
			}
			else {
				root.addChild(self);
			}
		}
		
		
		for (Iterator<TreeNode<Object[]>> it = root.getChildren().iterator(); it.hasNext();) {
			(it.next().getEntity())[0] = null;
		}
		
		list.clear();
		if ("1".equals(request.getParameter("detail"))) {
			root.generateLeafEntityList(list);
		}
		else {
			root.generateEntityList(list);
		}
	}

	@Override
	protected ReportConfigurator getConfigurator(HttpServletRequest request, Page page) {
		ReportConfigurator configurator = super.getConfigurator(request, page);
		ListColumn[] columns = configurator.getColumns();
		for (int i = 0; i < columns.length; i++) {
			columns[i].setProperty(i+"");
		}
		return configurator;
	}
/*	@Override
	protected void initConfigurator(HttpServletRequest request, ReportConfigurator configurator) {
		ListColumn[] columns = configurator.getColumns();
		for (int i = 0; i < columns.length; i++) {
			columns[i].setProperty(i+"");
		}
		super.initConfigurator(request, configurator);
	}*/
	
}
