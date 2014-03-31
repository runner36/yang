package com.winchannel.core.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.winchannel.core.bean.ListColumn;

/**
 * @author xianghui <br>
 *         modify by Jaffery on 7/26/2011 to support poi 3.7
 */
class XlsExporter extends Exporter {

	public static final int WIDTH_MULT = 280;
	public static final int MIN_CHARS = 8;
	public static final short DEFAULT_FONT_HEIGHT = 8;

	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFPrintSetup ps;
	private short rownum = -1;
	private HSSFRow hssfRow;

	private HSSFFont font;
	private HSSFFont fontBold;
	private HSSFCellStyle titleStyle;
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle[] styles;

	public XlsExporter(ListColumn[] columns) {
		super(columns);
		wb = new HSSFWorkbook();
		sheet = wb.createSheet("Export Workbook");
		sheet.setAutobreaks(true);
		ps = sheet.getPrintSetup();
		ps.setFitHeight((short) 1);
		ps.setFitWidth((short) 1);
		initStyles();
	}

	protected void setContentType() {
		this.contentType = "xls";
	}

	private void initStyles() {
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setColor(HSSFColor.BLACK.index);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints(DEFAULT_FONT_HEIGHT);

		fontBold = wb.createFont();
		fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontBold.setColor(HSSFColor.BLACK.index);
		fontBold.setFontName(HSSFFont.FONT_ARIAL);
		fontBold.setFontHeightInPoints(DEFAULT_FONT_HEIGHT);

		titleStyle = wb.createCellStyle();
		titleStyle.setFont(fontBold);
		titleStyle.setWrapText(true);

		headerStyle = wb.createCellStyle();
		headerStyle.setFont(font);
		headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setRightBorderColor(HSSFColor.BLACK.index);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setTopBorderColor(HSSFColor.BLACK.index);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		styles = new HSSFCellStyle[columns.length];
		HSSFDataFormat format = wb.createDataFormat();
		for (int i = 0; i < columns.length; i++) {
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);

			String align = columns[i].getAlign().toLowerCase();
			if ("right".equals(align)) {
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			} else if ("center".equals(align)) {
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			}

			if ("".equals(columns[i].getCell())) {
				style.setWrapText(true);
			}

			if (!"".equals(columns[i].getFormat())) {
				style.setDataFormat(format.getFormat(columns[i].getFormat()));
			}

			styles[i] = style;

		}

	}

	public void createTitle(String title) {
		rownum++;
		HSSFRow titleRow = sheet.createRow(rownum);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,
				columns.length - 1));
	}

	public void createHeader() {
		int hier = 0;
		for (int i = 0; i < columns.length; i++) {
			int len = columns[i].getTitle().split(",").length;
			if (hier < len) {
				hier = len;
			}
		}

		for (int index = 0; index < hier; index++) {
			rownum++;
			HSSFRow row = sheet.createRow(rownum);
			String headName = "";
			int colspan = 1;
			int rowspan = 1;
			boolean isLast = false;
			short cellnum = 0;
			for (; cellnum < columns.length; cellnum++) {
				String[] name = columns[cellnum].getTitle().split(",");

				if ((index + 1 > name.length || !name[index].equals(headName))
						&& !headName.equals("")) {
					if (colspan > 1) {
						for (int i = (cellnum - colspan + 1); i <= (cellnum - 1); i++) {
							HSSFCell hssfCell = row.createCell(i);
							hssfCell.setCellStyle(headerStyle);
						}
						sheet.addMergedRegion(new CellRangeAddress(rownum,
								rownum, cellnum - colspan, cellnum - 1));
					}
					if (rowspan > 1) {
						for (int i = (rownum + 1); i < (rownum + rowspan); i++) {
							HSSFRow hssfRow = sheet.createRow(i);
							HSSFCell hssfCell = hssfRow.createCell(cellnum - 1);
							hssfCell.setCellStyle(headerStyle);
						}
						sheet.addMergedRegion(new CellRangeAddress(rownum,
								rownum + rowspan - 1, cellnum - 1, cellnum - 1));
					}

					HSSFCell hssfCell = row.createCell(cellnum - colspan);
					hssfCell.setCellStyle(headerStyle);
					hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					this.fixWidthAndPopulate(hssfCell, headName, "");

					colspan = 1;
					rowspan = 1;
				} else {
					if (!headName.equals("")) {
						colspan++;
					}
				}

				if (index + 1 <= name.length) {
					headName = name[index];
					isLast = (index == name.length - 1);
					if (isLast) {
						rowspan = hier - index;
					}
				} else {
					headName = "";
				}

			}

			if (!headName.equals("")) {
				if (colspan > 1) {
					for (int i = (cellnum - colspan + 1); i <= (cellnum - 1); i++) {
						HSSFCell hssfCell = row.createCell(i);
						hssfCell.setCellStyle(headerStyle);
					}
					sheet.addMergedRegion(new CellRangeAddress(rownum,
							rownum, cellnum - colspan, cellnum - 1));
				}
				if (rowspan > 1) {
					for (int i = (rownum + 1); i < (rownum + rowspan); i++) {
						HSSFRow hssfRow = sheet.createRow(i);
						HSSFCell hssfCell = hssfRow.createCell(cellnum - 1);
						hssfCell.setCellStyle(headerStyle);
					}
					sheet.addMergedRegion(new CellRangeAddress(rownum,
							rownum + rowspan - 1, cellnum - 1, cellnum - 1));
				}

				HSSFCell hssfCell = row.createCell(cellnum - colspan);
				hssfCell.setCellStyle(headerStyle);
				hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				this.fixWidthAndPopulate(hssfCell, headName, "");
			}
		}

	}

	/*
	 * public void createBody(List body, int bodyStart, int leftHeader) {
	 * Object[] lastRow = new Object[leftHeader]; for (int i = 0; i <
	 * body.size(); i++) { rownum++; Object[] objects = (Object[]) body.get(i);
	 * hssfRow = sheet.createRow(rownum);
	 * 
	 * boolean isEquals = true; Object lastCol = null; for (short cellnum = 0;
	 * cellnum < objects.length - bodyStart; cellnum++) { String value =
	 * columns[cellnum].format(objects[cellnum + bodyStart]);
	 * 
	 * HSSFCell hssfCell = hssfRow.createCell(cellnum);
	 * hssfCell.setEncoding(encoding);
	 * 
	 * if (cellnum < leftHeader) { hssfCell.setCellStyle(headerStyle);
	 * 
	 * if (value.equals(lastRow[cellnum]) && isEquals) {
	 * sheet.addMergedRegion(new Region(rownum - 1, cellnum, rownum, cellnum));
	 * } else { if (value.equals("")) { value = "小计"; }
	 * this.fixWidthAndPopulate(hssfCell, value, columns[cellnum].getCell());
	 * lastRow[cellnum] = value; isEquals = false; }
	 * 
	 * if (value.equals("") && value.equals(lastCol)) {
	 * sheet.addMergedRegion(new Region(rownum, (short)(cellnum - 1), rownum,
	 * cellnum)); } else { lastCol = value; }
	 * 
	 * } else { hssfCell.setCellStyle(styles[cellnum]);
	 * this.fixWidthAndPopulate(hssfCell, value, columns[cellnum].getCell()); }
	 * 
	 * } } }
	 */

	@SuppressWarnings("rawtypes")
	public void createBody(List body, int bodyStart, int leftHeader) {
		for (int i = 0; i < body.size(); i++) {
			rownum++;
			Object[] objects = (Object[]) body.get(i);
			hssfRow = sheet.createRow(rownum);

			for (int cellnum = 0; cellnum < columns.length; cellnum++) {
				ListColumn column = columns[cellnum];

				if (StringUtils.isNotBlank(column.getProperty())) {
					int valueIndex = Integer.parseInt(column.getProperty())
							+ bodyStart;

					HSSFCell hssfCell = hssfRow.createCell(cellnum);
					hssfCell.setCellStyle(styles[cellnum]);

					String value = column.format(objects[valueIndex]);
					this.fixWidthAndPopulate(hssfCell, value, column.getCell());
				}
			}
		}
	}

	public void createFooter(String footer) {
		createTitle(footer);
	}

	private void fixWidthAndPopulate(HSSFCell hssfCell, String value,
			String cell) {
		if ("number".equals(cell.toLowerCase())) {
			try {
				hssfCell.setCellValue(Double.parseDouble(value));
			} catch (Exception e) {
				hssfCell.setCellValue(value);
			}
		} else {
			hssfCell.setCellValue(value);
		}

		int cellNum = hssfCell.getColumnIndex();
		short width = (short) (value.getBytes().length * WIDTH_MULT);
		if (width > sheet.getColumnWidth(cellNum)) {
			sheet.setColumnWidth(cellNum,
					(short) (value.getBytes().length * WIDTH_MULT));
		}
	}

	public void write(OutputStream outputStream) throws IOException {
		wb.write(outputStream);
	}

}
