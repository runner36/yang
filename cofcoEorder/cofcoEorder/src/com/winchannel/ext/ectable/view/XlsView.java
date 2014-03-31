package com.winchannel.ext.ectable.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.calc.CalcResult;
import org.extremecomponents.table.calc.CalcUtils;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.View;
import org.extremecomponents.util.ExtremeUtils;

import com.winchannel.core.bean.ExpParam;
import com.winchannel.core.conf.ReportConfigurator;

// Referenced classes of package org.extremecomponents.table.view:
//            View

/**
 * 
 * @author Jaffery
 * 
 */
public class XlsView implements View {

	public XlsView() {
		rownum = -1;
	}

	public void beforeBody(TableModel model) {
		logger.debug("XlsView.init()");
		moneyFormat = model.getPreferences().getPreference("table.exportableformat.money");
		if (StringUtils.isEmpty(moneyFormat))
			moneyFormat = "$###,###,##0.00";
		percentFormat = model.getPreferences().getPreference("table.exportableformat.percent");
		if (StringUtils.isEmpty(percentFormat))
			percentFormat = "##0.0%";
		wb = new HSSFWorkbook();
		sheet = wb.createSheet();
		wb.setSheetName(0, "Export Workbook");
		styles = initStyles(wb);
		ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setFitHeight((short) 1);
		ps.setFitWidth((short) 1);
		createHeader(model);
	}

	public void body(TableModel model, Column column) {
		if (column.isFirstColumn()) {
			rownum++;
			cellnum = 0;
			hssfRow = sheet.createRow(rownum);
		}
		String value = column.getCellDisplay();
		HSSFCell hssfCell = hssfRow.createCell(cellnum);
		// 自定义字体样式 现在支持5种颜色
		// Red、Yellow、Green、Blue、Purple
		String styleModifier = column.getPropertyValueAsString().replace(" ",
				"");
		if (styleModifier.indexOf("<fontcolor") > -1) {
			styleModifier = styleModifier.substring(
					styleModifier.indexOf("=") + 1, styleModifier.length());
			styleModifier = styleModifier.substring(0,
					styleModifier.indexOf(">"));

			styleModifier = styleModifier.substring(0, 1).toUpperCase()
					+ styleModifier.substring(1, styleModifier.length())
							.toLowerCase();

		} else {
			styleModifier = "";
		}
		if (column.isEscapeAutoFormat())
			writeToCellAsText(hssfCell, value, styleModifier);
		else
			writeToCellFormatted(hssfCell, value, styleModifier);
		cellnum++;
	}

	public Object afterBody(TableModel model) {
		if (model.getLimit().getTotalRows() != 0)
			totals(model);
		return wb;
	}

	/**
	 * 
	 * @Title: createQueryWhere
	 * @Description: 导出文件中在顶部添加查询条件显示。使用方法：在.xml中param标签中添加
	 *               displayName（导出文件中显示的名称）、isDisplay（值是"1"时导出查询条件）属性。
	 * @param @param model
	 * @return void
	 */
	private void createQueryWhere(TableModel model) {
		ReportConfigurator configurator = (ReportConfigurator) model
				.getContext().getRequestAttribute("configurator");
		if (null == configurator) {
			return;
		}
		List<ExpParam> l = configurator.getParams();
		for (ExpParam param : l) {
			if ("1".equals(param.getIsDisplay())) {
				rownum++;
				HSSFRow titleRow = sheet.createRow(rownum);
				HSSFCell titleCell = titleRow.createCell(0);
				titleCell.setCellStyle((HSSFCellStyle) styles.get("boldStyle"));
				titleCell.setCellValue(param.getDisplayName());

				HSSFCell titleCell1 = titleRow.createCell(2);
				titleCell1
						.setCellStyle((HSSFCellStyle) styles.get("boldStyle"));
				titleCell1.setCellValue(model.getContext().getParameter(
						param.getId()));
				// sheet.addMergedRegion(new Region(rownum, (short) 0, rownum,
				// (short) (1 - 1)));
			}

		}
	}

	@SuppressWarnings("rawtypes")
	private void createHeader(TableModel model) {
		List columns = model.getColumnHandler().getHeaderColumns();
		String title = model.getExportHandler().getCurrentExport()
				.getFileName();
		if (StringUtils.isBlank(title))
			title = "Export WookBook";
		else
			title = title.substring(0, title.length() - 4);

		createQueryWhere(model);
		// rownum++;
		// HSSFRow titleRow = sheet.createRow(rownum);
		// HSSFCell titleCell = titleRow.createCell((short) 0);
		// setCellEncoding(titleCell);
		// titleCell.setCellStyle((HSSFCellStyle) styles.get("boldStyle"));
		// titleCell.setCellValue(title);
		// sheet.addMergedRegion(new Region(rownum, (short) 0, rownum,
		// (short) (columns.size() - 1)));
		wb.setSheetName(0, title);
		int hier = 0;
		for (int i = 0; i < columns.size(); i++) {
			Column column = (Column) columns.get(i);
			int len = column.getTitle().split(",").length;
			if (hier < len)
				hier = len;
		}

		for (int index = 0; index < hier; index++) {
			rownum++;
			HSSFRow row = sheet.createRow(rownum);
			String headName = "";
			int colspan = 1;
			int rowspan = 1;
			boolean isLast = false;
			for (cellnum = 0; cellnum < columns.size(); cellnum++) {
				Column column = (Column) columns.get(cellnum);
				String name[] = column.getTitle().split(",");
				if ((index + 1 > name.length || !name[index].equals(headName))
						&& !headName.equals("")) {
					if (colspan > 1) {
						for (int i = (cellnum - colspan) + 1; i <= cellnum - 1; i++) {
							HSSFCell hssfCell = row.createCell(i);
							hssfCell.setCellStyle((HSSFCellStyle) styles
									.get("titleStyle"));
						}
						sheet.addMergedRegion(new CellRangeAddress(rownum,
								rownum, cellnum - colspan, cellnum - 1));
					}
					if (rowspan > 1) {
						for (int i = rownum + 1; i <= rownum + rowspan; i++) {
							HSSFRow hssfRow = sheet.createRow(i);
							HSSFCell hssfCell = hssfRow.createCell(cellnum - 1);
							hssfCell.setCellStyle((HSSFCellStyle) styles
									.get("titleStyle"));
						}
						sheet.addMergedRegion(new CellRangeAddress(rownum,
								rownum + rowspan - 1, cellnum - 1, cellnum - 1));
					}
					HSSFCell hssfCell = row.createCell(cellnum - colspan);
					hssfCell.setCellStyle((HSSFCellStyle) styles
							.get("titleStyle"));
					hssfCell.setCellType(1);
					hssfCell.setCellValue(headName);
					int valWidth = headName.replaceAll("[^\\x00-\\xff]", "**")
							.length() * 280;
					if (isLast)
						sheet.setColumnWidth(hssfCell.getColumnIndex(),
								(short) valWidth);
					colspan = 1;
					rowspan = 1;
				} else if (!headName.equals(""))
					colspan++;
				if (index + 1 <= name.length) {
					headName = name[index];
					isLast = index == name.length - 1;
					if (isLast)
						rowspan = hier - index;
				} else {
					headName = "";
				}
			}

			if (!headName.equals("")) {
				if (colspan > 1) {
					for (int i = (cellnum - colspan) + 1; i <= cellnum - 1; i++) {
						HSSFCell hssfCell = row.createCell(i);
						hssfCell.setCellStyle((HSSFCellStyle) styles
								.get("titleStyle"));
					}
					sheet.addMergedRegion(new CellRangeAddress(rownum, rownum,
							cellnum - colspan, cellnum - 1));
				}
				if (rowspan > 1) {
					for (int i = rownum + 1; i <= rownum + rowspan; i++) {
						HSSFRow hssfRow = sheet.createRow(i);
						HSSFCell hssfCell = hssfRow.createCell(cellnum - 1);
						hssfCell.setCellStyle((HSSFCellStyle) styles
								.get("titleStyle"));
					}
					sheet.addMergedRegion(new CellRangeAddress(rownum, rownum
							+ rowspan - 1, cellnum - 1, cellnum - 1));
				}
				HSSFCell hssfCell = row.createCell(cellnum - colspan);
				hssfCell.setCellStyle((HSSFCellStyle) styles.get("titleStyle"));
				hssfCell.setCellType(1);
				hssfCell.setCellValue(headName);
				int valWidth = headName.replaceAll("[^\\x00-\\xff]", "**")
						.length() * 280;
				if (isLast)
					sheet.setColumnWidth(hssfCell.getColumnIndex(),
							(short) valWidth);
			}
		}

	}

	private void writeToCellAsText(HSSFCell cell, String value,
			String styleModifier) {
		if (value.trim().equals("&nbsp;"))
			value = "";
		cell.setCellStyle((HSSFCellStyle) styles.get((new StringBuilder(
				"textStyle")).append(styleModifier).toString()));
		fixWidthAndPopulate(cell, -0.99999000000000005D, value);
	}

	private void writeToCellFormatted(HSSFCell cell, String value,
			String styleModifier) {
		double numeric = -0.99999000000000005D;
		try {
			numeric = Double.parseDouble(value);
			if (value.indexOf("0") == 0 && value.indexOf(".") != 1)
				numeric = -0.99999000000000005D;
		} catch (Exception e) {
			numeric = -0.99999000000000005D;
		}
		if (value.startsWith("$") || value.endsWith("%")
				|| value.startsWith("($")) {
			boolean moneyFlag = value.startsWith("$") || value.startsWith("($");
			boolean percentFlag = value.endsWith("%");
			value = StringUtils.replace(value, "$", "");
			value = StringUtils.replace(value, "%", "");
			value = StringUtils.replace(value, ",", "");
			value = StringUtils.replace(value, "(", "-");
			value = StringUtils.replace(value, ")", "");
			try {
				numeric = Double.parseDouble(value);
			} catch (Exception e) {
				numeric = -0.99999000000000005D;
			}
			cell.setCellType(0);
			if (moneyFlag)
				cell.setCellStyle((HSSFCellStyle) styles
						.get((new StringBuilder("moneyStyle")).append(
								styleModifier).toString()));
			else if (percentFlag) {
				numeric /= 100D;
				cell.setCellStyle((HSSFCellStyle) styles
						.get((new StringBuilder("percentStyle")).append(
								styleModifier).toString()));
			}
		} else if (numeric != -0.99999000000000005D) {
			cell.setCellStyle((HSSFCellStyle) styles.get((new StringBuilder(
					"numericStyle")).append(styleModifier).toString()));
		} else {
			if (value.trim().equals("&nbsp;"))
				value = "";
			cell.setCellStyle((HSSFCellStyle) styles.get((new StringBuilder(
					"textStyle")).append(styleModifier).toString()));
		}
		fixWidthAndPopulate(cell, numeric, value);
	}

	private void fixWidthAndPopulate(HSSFCell cell, double numeric, String value) {
		int valWidth = 0;
		if (numeric != -0.99999000000000005D) {
//			cell.setCellValue(numeric);
//			valWidth = (new StringBuilder(String.valueOf(cell
//					.getNumericCellValue()))).append("$,.").toString()
//					.replaceAll("[^\\x00-\\xff]", "**").length() * 280;
			//数值类型也作为字符类型写入,否则会导致1.0 或者 -01 0002这种数字的0 都会被去掉
			cell.setCellValue(value);
			valWidth = (new StringBuilder(String.valueOf(cell
					.getStringCellValue()))).append("$,.").toString()
					.replaceAll("[^\\x00-\\xff]", "**").length() * 280;
		} else {
			cell.setCellValue(value);
			valWidth = (new StringBuilder(String.valueOf(cell
					.getStringCellValue()))).toString()
					.replaceAll("[^\\x00-\\xff]", "**").length() * 280;
			if (valWidth < 2240)
				valWidth = 2240;
		}
		if (valWidth > sheet.getColumnWidth(cell.getColumnIndex()))
			sheet.setColumnWidth(cell.getColumnIndex(), (short) valWidth);
	}

	@SuppressWarnings("rawtypes")
	private Map initStyles(HSSFWorkbook wb) {
		return initStyles(wb, (short) 8);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map initStyles(HSSFWorkbook wb, short fontHeight) {
		Map result = new HashMap();
		HSSFCellStyle titleStyle = wb.createCellStyle();
		HSSFCellStyle textStyle = wb.createCellStyle();
		HSSFCellStyle boldStyle = wb.createCellStyle();
		HSSFCellStyle numericStyle = wb.createCellStyle();
		HSSFCellStyle numericStyleBold = wb.createCellStyle();
		HSSFCellStyle moneyStyle = wb.createCellStyle();
		HSSFCellStyle moneyStyleBold = wb.createCellStyle();
		HSSFCellStyle percentStyle = wb.createCellStyle();
		HSSFCellStyle percentStyleBold = wb.createCellStyle();
		HSSFCellStyle moneyStyle_Totals = wb.createCellStyle();
		HSSFCellStyle naStyle_Totals = wb.createCellStyle();
		HSSFCellStyle numericStyle_Totals = wb.createCellStyle();
		HSSFCellStyle percentStyle_Totals = wb.createCellStyle();
		HSSFCellStyle textStyle_Totals = wb.createCellStyle();
		result.put("titleStyle", titleStyle);
		result.put("textStyle", textStyle);
		result.put("boldStyle", boldStyle);
		result.put("numericStyle", numericStyle);
		result.put("numericStyleBold", numericStyleBold);
		result.put("moneyStyle", moneyStyle);
		result.put("moneyStyleBold", moneyStyleBold);
		result.put("percentStyle", percentStyle);
		result.put("percentStyleBold", percentStyleBold);
		result.put("moneyStyle_Totals", moneyStyle_Totals);
		result.put("naStyle_Totals", naStyle_Totals);
		result.put("numericStyle_Totals", numericStyle_Totals);
		result.put("percentStyle_Totals", percentStyle_Totals);
		result.put("textStyle_Totals", textStyle_Totals);
		HSSFDataFormat format = wb.createDataFormat();
		HSSFFont font = wb.createFont();
		font.setBoldweight((short) 400);
		font.setColor((short) 8);
		font.setFontName("Arial");
		font.setFontHeightInPoints(fontHeight);
		HSSFFont fontBold = wb.createFont();
		fontBold.setBoldweight((short) 700);
		fontBold.setColor((short) 8);
		fontBold.setFontName("Arial");
		fontBold.setFontHeightInPoints(fontHeight);
		moneyStyle.setFont(font);
		moneyStyle.setAlignment((short) 3);
		moneyStyle.setDataFormat(format.getFormat(moneyFormat));
		moneyStyleBold.setFont(fontBold);
		moneyStyleBold.setAlignment((short) 3);
		moneyStyleBold.setDataFormat(format.getFormat(moneyFormat));
		percentStyle.setFont(font);
		percentStyle.setAlignment((short) 3);
		percentStyle.setDataFormat(format.getFormat(percentFormat));
		percentStyleBold.setFont(fontBold);
		percentStyleBold.setAlignment((short) 3);
		percentStyleBold.setDataFormat(format.getFormat(percentFormat));
		numericStyle.setFont(font);
		numericStyle.setAlignment((short) 3);
		numericStyleBold.setFont(fontBold);
		numericStyleBold.setAlignment((short) 3);
		titleStyle.setFont(font);
		titleStyle.setFillForegroundColor((short) 22);
		titleStyle.setFillPattern((short) 1);
		titleStyle.setBorderBottom((short) 1);
		titleStyle.setBottomBorderColor((short) 8);
		titleStyle.setBorderLeft((short) 1);
		titleStyle.setLeftBorderColor((short) 8);
		titleStyle.setBorderRight((short) 1);
		titleStyle.setRightBorderColor((short) 8);
		titleStyle.setBorderTop((short) 1);
		titleStyle.setTopBorderColor((short) 8);
		titleStyle.setAlignment((short) 2);
		titleStyle.setVerticalAlignment((short) 1);
		textStyle.setFont(font);
		textStyle.setWrapText(true);
		textStyle.setDataFormat(format.getFormat("@"));
		boldStyle.setFont(fontBold);
		boldStyle.setWrapText(true);
		boldStyle.setDataFormat(format.getFormat("@"));
		moneyStyle_Totals.setFont(fontBold);
		moneyStyle_Totals.setFillForegroundColor((short) 22);
		moneyStyle_Totals.setFillPattern((short) 1);
		moneyStyle_Totals.setBorderBottom((short) 1);
		moneyStyle_Totals.setBottomBorderColor((short) 8);
		moneyStyle_Totals.setBorderTop((short) 1);
		moneyStyle_Totals.setTopBorderColor((short) 8);
		moneyStyle_Totals.setAlignment((short) 3);
		moneyStyle_Totals.setVerticalAlignment((short) 1);
		moneyStyle_Totals.setDataFormat(format.getFormat(moneyFormat));
		naStyle_Totals.setFont(fontBold);
		naStyle_Totals.setFillForegroundColor((short) 22);
		naStyle_Totals.setFillPattern((short) 1);
		naStyle_Totals.setBorderBottom((short) 1);
		naStyle_Totals.setBottomBorderColor((short) 8);
		naStyle_Totals.setBorderTop((short) 1);
		naStyle_Totals.setTopBorderColor((short) 8);
		naStyle_Totals.setAlignment((short) 3);
		naStyle_Totals.setVerticalAlignment((short) 1);
		numericStyle_Totals.setFont(fontBold);
		numericStyle_Totals.setFillForegroundColor((short) 22);
		numericStyle_Totals.setFillPattern((short) 1);
		numericStyle_Totals.setBorderBottom((short) 1);
		numericStyle_Totals.setBottomBorderColor((short) 8);
		numericStyle_Totals.setBorderTop((short) 1);
		numericStyle_Totals.setTopBorderColor((short) 8);
		numericStyle_Totals.setAlignment((short) 3);
		numericStyle_Totals.setVerticalAlignment((short) 1);
		percentStyle_Totals.setFont(fontBold);
		percentStyle_Totals.setFillForegroundColor((short) 22);
		percentStyle_Totals.setFillPattern((short) 1);
		percentStyle_Totals.setBorderBottom((short) 1);
		percentStyle_Totals.setBottomBorderColor((short) 8);
		percentStyle_Totals.setBorderTop((short) 1);
		percentStyle_Totals.setTopBorderColor((short) 8);
		percentStyle_Totals.setAlignment((short) 3);
		percentStyle_Totals.setVerticalAlignment((short) 1);
		percentStyle_Totals.setDataFormat(format.getFormat(percentFormat));
		textStyle_Totals.setFont(fontBold);
		textStyle_Totals.setFillForegroundColor((short) 22);
		textStyle_Totals.setFillPattern((short) 1);
		textStyle_Totals.setBorderBottom((short) 1);
		textStyle_Totals.setBottomBorderColor((short) 8);
		textStyle_Totals.setBorderTop((short) 1);
		textStyle_Totals.setTopBorderColor((short) 8);
		textStyle_Totals.setAlignment((short) 1);
		textStyle_Totals.setVerticalAlignment((short) 1);
		// 2011-04-27 添加颜色样式 begin
		// text 红色字体
		HSSFCellStyle textStyleRed = wb.createCellStyle();
		result.put("textStyleRed", textStyleRed);
		HSSFFont fontRed = wb.createFont();
		fontRed.setBoldweight((short) 400);
		fontRed.setColor((short) 2);
		fontRed.setFontName("Arial");
		fontRed.setFontHeightInPoints(fontHeight);
		textStyleRed.setFont(fontRed);
		// textStyleRed.setFillForegroundColor((short) 22);
		// textStyleRed.setFillPattern((short) 1);
		// textStyleRed.setBorderBottom((short) 1);
		// textStyleRed.setBottomBorderColor((short) 8);
		// textStyleRed.setBorderLeft((short) 1);
		// textStyleRed.setLeftBorderColor((short) 8);
		// textStyleRed.setBorderRight((short) 1);
		// textStyleRed.setRightBorderColor((short) 8);
		// textStyleRed.setBorderTop((short) 1);
		// textStyleRed.setTopBorderColor((short) 8);
		textStyleRed.setAlignment((short) 2);
		textStyleRed.setVerticalAlignment((short) 1);
		// text 黄色字体
		HSSFCellStyle textStyleYellow = wb.createCellStyle();
		result.put("textStyleYellow", textStyleYellow);
		HSSFFont fontYellow = wb.createFont();
		fontYellow.setBoldweight((short) 400);
		fontYellow.setColor((short) 5);
		fontYellow.setFontName("Arial");
		fontYellow.setFontHeightInPoints(fontHeight);
		textStyleYellow.setFont(fontYellow);
		// textStyleYellow.setFillForegroundColor((short) 22);
		// textStyleYellow.setFillPattern((short) 1);
		// textStyleYellow.setBorderBottom((short) 1);
		// textStyleYellow.setBottomBorderColor((short) 8);
		// textStyleYellow.setBorderLeft((short) 1);
		// textStyleYellow.setLeftBorderColor((short) 8);
		// textStyleYellow.setBorderRight((short) 1);
		// textStyleYellow.setRightBorderColor((short) 8);
		// textStyleYellow.setBorderTop((short) 1);
		// textStyleYellow.setTopBorderColor((short) 8);
		textStyleYellow.setAlignment((short) 2);
		textStyleYellow.setVerticalAlignment((short) 1);
		// 绿色
		HSSFCellStyle textStyleGreen = wb.createCellStyle();
		result.put("textStyleGreen", textStyleGreen);
		HSSFFont fontGreen = wb.createFont();
		fontGreen.setBoldweight((short) 400);
		fontGreen.setColor((short) 21);
		fontGreen.setFontName("Arial");
		fontGreen.setFontHeightInPoints(fontHeight);
		textStyleGreen.setFont(fontGreen);
		// textStyleGreen.setFillForegroundColor((short) 22);
		// textStyleGreen.setFillPattern((short) 1);
		// textStyleGreen.setBorderBottom((short) 1);
		// textStyleGreen.setBottomBorderColor((short) 8);
		// textStyleGreen.setBorderLeft((short) 1);
		// textStyleGreen.setLeftBorderColor((short) 8);
		// textStyleGreen.setBorderRight((short) 1);
		// textStyleGreen.setRightBorderColor((short) 8);
		// textStyleGreen.setBorderTop((short) 1);
		// textStyleGreen.setTopBorderColor((short) 8);
		textStyleGreen.setAlignment((short) 2);
		textStyleGreen.setVerticalAlignment((short) 1);
		// 蓝色
		HSSFCellStyle textStyleBlue = wb.createCellStyle();
		result.put("textStyleBlue", textStyleBlue);
		HSSFFont fontBlue = wb.createFont();
		fontBlue.setBoldweight((short) 400);
		fontBlue.setColor((short) 12);
		fontBlue.setFontName("Arial");
		fontBlue.setFontHeightInPoints(fontHeight);
		textStyleBlue.setFont(fontBlue);
		// textStyleBlue.setFillForegroundColor((short) 22);
		// textStyleBlue.setFillPattern((short) 1);
		// textStyleBlue.setBorderBottom((short) 1);
		// textStyleBlue.setBottomBorderColor((short) 8);
		// textStyleBlue.setBorderLeft((short) 1);
		// textStyleBlue.setLeftBorderColor((short) 8);
		// textStyleBlue.setBorderRight((short) 1);
		// textStyleBlue.setRightBorderColor((short) 8);
		// textStyleBlue.setBorderTop((short) 1);
		// textStyleBlue.setTopBorderColor((short) 8);
		textStyleBlue.setAlignment((short) 2);
		textStyleBlue.setVerticalAlignment((short) 1);
		// 紫色
		HSSFCellStyle textStylePurple = wb.createCellStyle();
		result.put("textStylePurple", textStylePurple);
		HSSFFont fontPurple = wb.createFont();
		fontPurple.setBoldweight((short) 400);
		fontPurple.setColor((short) 6);
		fontPurple.setFontName("Arial");
		fontPurple.setFontHeightInPoints(fontHeight);
		textStylePurple.setFont(fontPurple);
		// textStylePurple.setFillForegroundColor((short) 22);
		// textStylePurple.setFillPattern((short) 1);
		// textStylePurple.setBorderBottom((short) 1);
		// textStylePurple.setBottomBorderColor((short) 8);
		// textStylePurple.setBorderLeft((short) 1);
		// textStylePurple.setLeftBorderColor((short) 8);
		// textStylePurple.setBorderRight((short) 1);
		// textStylePurple.setRightBorderColor((short) 8);
		// textStylePurple.setBorderTop((short) 1);
		// textStylePurple.setTopBorderColor((short) 8);
		textStylePurple.setAlignment((short) 2);
		textStylePurple.setVerticalAlignment((short) 1);
		// moneyStyleRed
		HSSFCellStyle moneyStyleRed = wb.createCellStyle();
		result.put("moneyStyleRed", moneyStyleRed);
		moneyStyleRed.setFont(fontRed);
		moneyStyleRed.setAlignment((short) 3);
		moneyStyleRed.setDataFormat(format.getFormat(moneyFormat));
		//
		HSSFCellStyle moneyStyleYellow = wb.createCellStyle();
		result.put("moneyStyleYellow", moneyStyleYellow);
		moneyStyleYellow.setFont(fontYellow);
		moneyStyleYellow.setAlignment((short) 3);
		moneyStyleYellow.setDataFormat(format.getFormat(moneyFormat));
		//
		HSSFCellStyle moneyStyleGreen = wb.createCellStyle();
		result.put("moneyStyleGreen", moneyStyleGreen);
		moneyStyleGreen.setFont(fontGreen);
		moneyStyleGreen.setAlignment((short) 3);
		moneyStyleGreen.setDataFormat(format.getFormat(moneyFormat));
		//
		HSSFCellStyle moneyStyleBlue = wb.createCellStyle();
		result.put("moneyStyleBlue", moneyStyleBlue);
		moneyStyleBlue.setFont(fontBlue);
		moneyStyleBlue.setAlignment((short) 3);
		moneyStyleBlue.setDataFormat(format.getFormat(moneyFormat));
		//
		HSSFCellStyle moneyStylePurple = wb.createCellStyle();
		result.put("moneyStylePurple", moneyStylePurple);
		moneyStylePurple.setFont(fontPurple);
		moneyStylePurple.setAlignment((short) 3);
		moneyStylePurple.setDataFormat(format.getFormat(moneyFormat));
		// percentStyle
		HSSFCellStyle percentStyleRed = wb.createCellStyle();
		result.put("percentStyleRed", percentStyleRed);
		percentStyleRed.setFont(fontRed);
		percentStyleRed.setAlignment((short) 3);
		percentStyleRed.setDataFormat(format.getFormat(percentFormat));
		//
		HSSFCellStyle percentStyleYellow = wb.createCellStyle();
		result.put("percentStyleYellow", percentStyleYellow);
		percentStyleYellow.setFont(fontYellow);
		percentStyleYellow.setAlignment((short) 3);
		percentStyleYellow.setDataFormat(format.getFormat(percentFormat));
		//
		HSSFCellStyle percentStyleGreen = wb.createCellStyle();
		result.put("percentStyleGreen", percentStyleGreen);
		percentStyleGreen.setFont(fontGreen);
		percentStyleGreen.setAlignment((short) 3);
		percentStyleGreen.setDataFormat(format.getFormat(percentFormat));
		//
		HSSFCellStyle percentStyleBlue = wb.createCellStyle();
		result.put("percentStyleBlue", percentStyleBlue);
		percentStyleBlue.setFont(fontBlue);
		percentStyleBlue.setAlignment((short) 3);
		percentStyleBlue.setDataFormat(format.getFormat(percentFormat));
		//
		HSSFCellStyle percentStylePurple = wb.createCellStyle();
		result.put("percentStylePurple", percentStylePurple);
		percentStylePurple.setFont(fontPurple);
		percentStylePurple.setAlignment((short) 3);
		percentStylePurple.setDataFormat(format.getFormat(percentFormat));

		// numericStyle
		HSSFCellStyle numericStyleRed = wb.createCellStyle();
		result.put("numericStyleRed", numericStyleRed);
		numericStyleRed.setFont(fontRed);
		numericStyleRed.setAlignment((short) 3);
		//
		HSSFCellStyle numericStyleYellow = wb.createCellStyle();
		result.put("numericStyleYellow", numericStyleYellow);
		numericStyleYellow.setFont(fontYellow);
		numericStyleYellow.setAlignment((short) 3);
		//
		HSSFCellStyle numericStyleGreen = wb.createCellStyle();
		result.put("numericStyleGreen", numericStyleGreen);
		numericStyleGreen.setFont(fontGreen);
		numericStyleGreen.setAlignment((short) 3);
		//
		HSSFCellStyle numericStyleBlue = wb.createCellStyle();
		result.put("numericStyleBlue", numericStyleBlue);
		numericStyleBlue.setFont(fontBlue);
		numericStyleBlue.setAlignment((short) 3);
		//
		HSSFCellStyle numericStylePurple = wb.createCellStyle();
		result.put("numericStylePurple", numericStylePurple);
		numericStylePurple.setFont(fontPurple);
		numericStylePurple.setAlignment((short) 3);
		// 2011-04-27 添加颜色样式 end
		return result;
	}

	@SuppressWarnings("rawtypes")
	public void totals(TableModel model) {
		Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();
		if (firstCalcColumn != null) {
			int rows = firstCalcColumn.getCalc().length;
			for (int i = 0; i < rows; i++) {
				rownum++;
				HSSFRow row = sheet.createRow(rownum);
				cellnum = 0;
				for (Iterator iter = model.getColumnHandler().getColumns()
						.iterator(); iter.hasNext();) {
					Column column = (Column) iter.next();
					if (column.isFirstColumn()) {
						String calcTitle = CalcUtils
								.getFirstCalcColumnTitleByPosition(model, i);
						HSSFCell cell = row.createCell(cellnum);
						if (column.isEscapeAutoFormat())
							writeToCellAsText(cell, calcTitle, "_Totals");
						else
							writeToCellFormatted(cell, calcTitle, "_Totals");
						cellnum++;
					} else if (column.isCalculated()) {
						CalcResult calcResult = CalcUtils
								.getCalcResultsByPosition(model, column, i);
						Number value = calcResult.getValue();
						HSSFCell cell = row.createCell(cellnum);
						if (value != null) {
							if (column.isEscapeAutoFormat())
								writeToCellAsText(cell, value.toString(),
										"_Totals");
							else
								writeToCellFormatted(cell,
										ExtremeUtils.formatNumber(
												column.getFormat(), value,
												model.getLocale()), "_Totals");
						} else {
							cell.setCellStyle((HSSFCellStyle) styles
									.get("naStyle_Totals"));
							cell.setCellValue("n/a");
						}
						cellnum++;
					} else {
						HSSFCell cell = row.createCell(cellnum);
						writeToCellFormatted(cell, "", "_Totals");
						cellnum++;
					}
				}

			}

		}
	}

	private static Log logger = LogFactory.getLog(XlsView.class);
	public static final int WIDTH_MULT = 280;
	public static final int MIN_CHARS = 8;
	public static final short DEFAULT_FONT_HEIGHT = 8;
	public static final double NON_NUMERIC = -0.99999000000000005D;
	public static final String DEFAULT_MONEY_FORMAT = "$###,###,##0.00";
	public static final String DEFAULT_PERCENT_FORMAT = "##0.0%";
	public static final String NBSP = "&nbsp;";
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFPrintSetup ps;
	@SuppressWarnings("rawtypes")
	private Map styles;
	private int rownum;
	private int cellnum;
	private HSSFRow hssfRow;
	private String moneyFormat;
	private String percentFormat;

}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from:
 * D:\eclipse\workspace\framework\WebContent\WEB-INF\lib\extremecomponents
 * -1.0.1.jar Total time: 116 ms Jad reported messages/errors: Exit status: 0
 * Caught exceptions:
 */