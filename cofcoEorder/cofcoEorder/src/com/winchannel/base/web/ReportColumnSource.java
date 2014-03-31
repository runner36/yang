package com.winchannel.base.web;

import java.util.List;

import org.dom4j.Element;

import com.winchannel.core.bean.ListColumn;
/**
 * 报表加入columns 列标签 
 * 可以加入动态列
 * 实现此接口
 * */
public interface ReportColumnSource {

	public List<ListColumn> doGetColumns(Element e);
	
}
