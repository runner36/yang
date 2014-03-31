package com.winchannel.core.importer.iterator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @author xianghui
 *
 */
public class ExcelDataIterator implements DataIterator {

	private Sheet sheet;
	private int rows;
	private int index;
	
	public ExcelDataIterator(InputStream is) throws Exception {
		sheet = Workbook.getWorkbook(is).getSheet(0);
		rows = sheet.getRows();
		index = 0;
	}

	public boolean hasNext() {
		return index < rows;
	}

	public Map<String, String> next() {
		Cell[] cells = sheet.getRow(index++);
		Map<String, String> rowMap = new HashMap<String, String>();
		for (int m = 0; m < cells.length; m++) {
			rowMap.put((m+1) + "", cells[m].getContents().trim());
		}
		return rowMap;
	}

	public void remove() {
	}
	
	public int getIndex() {
		return index;
	}

}
