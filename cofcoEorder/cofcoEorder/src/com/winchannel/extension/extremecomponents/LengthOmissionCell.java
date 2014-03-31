package com.winchannel.extension.extremecomponents;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;
/***
 * 加入长度超过10位加入省略号,并给予提示 
 * @author lidongbo
 * @date   2011-07-22
 */
public class LengthOmissionCell extends AbstractCell {

	@Override
	protected String getCellValue(TableModel tm, Column column) {
		Object value = column.getValue();
		if(value!=null){
			String stringValue = value.toString();
			if(stringValue.trim().length()>10){//如果超过10个长度
				return "<span title=\""+stringValue.trim()+"\">"+stringValue.trim().substring(0,9)+"...</span>";
			}else{
				return stringValue;
			}
		}
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println("180g*15包佳钙薄脆促销装".substring(0,10));
	}

}
