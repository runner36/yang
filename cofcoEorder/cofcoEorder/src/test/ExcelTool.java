package test;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelTool {
	
	static String getValue(Cell c) {
		if (c == null)
			return "";
		if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN)
			return c.getBooleanCellValue() ? "true" : "false";
		if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			DecimalFormat a = new DecimalFormat("###");
			return a.format(c.getNumericCellValue());
		}
		if (c.getCellType() == Cell.CELL_TYPE_STRING)
			return c.getStringCellValue();
		return "";

	}
	
}
