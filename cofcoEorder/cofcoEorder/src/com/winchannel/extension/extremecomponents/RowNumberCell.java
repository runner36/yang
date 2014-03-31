package com.winchannel.extension.extremecomponents;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.AbstractCell;
import org.extremecomponents.table.core.TableModel;

import com.winchannel.core.utils.ECPage;
import com.winchannel.core.utils.Page;

/**
 * ECTable序号列
 * @author lidongbo
 * @date   2011-06-13
 * */
public class RowNumberCell extends AbstractCell{
	
	@Override
	protected String getCellValue(TableModel tableModel, Column column) {
		ECPage page = (ECPage)tableModel.getContext().getSessionAttribute( tableModel.getTableHandler().getTable().getTableId() );
		return String.valueOf((page.getInt(Page.PAGE_NO)-1)*page.getInt(Page.PAGE_SIZE)+tableModel.getRowHandler().increaseRowCount()/2);
	}

}
