package com.winchannel.ext.ectable.view;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.View;

/***
 * Ectable的CSV导出处理器 <BR>
 * 加入对内容中包含分隔符与双引号的兼容,避免产生分列BUG <BR>
 * 加入对linux兼容的换行符 <BR>
 * 加入对长数字的解析,避免导致过长显示0科学计数法问题 <BR>
 * 加入 正负数值0开始,不显示0问题解决 <BR>
 * 加入 数值.0不显示问题解决 <BR>
 * 重构复用代码,原子化可能变更部分,允许子类的继承与覆写
 * 
 * @author lidongbo
 */
public class CsvView implements View {

	public final static String DELIMITER = "delimiter";

	final static String DEFAULT_DELIMITER = ",";

	/**每次JSP导出都会创建新的CsvView,因此不必考虑线程安全问题*/
	protected StringBuilder plainData = new StringBuilder();

	protected String line = System.getProperty("line.separator");

	/** 写入表头内容 */
	public void beforeBody(TableModel model) {

		String delimiter = doGetDelimiter(doGetExport(model));

		List<?> columns = model.getColumnHandler().getHeaderColumns();

		for (int i = 0; i < columns.size(); i++) {

			Column column = (Column) columns.get(i);

			plainData.append(formatValue(column.getTitle())).append(delimiter);

			if (column.isLastColumn()) {

				plainData.append(line);

			}
		}
	}

	protected Export doGetExport(TableModel model) {
		return model.getExportHandler().getCurrentExport();
	}

	/**获得分隔符*/
	protected String doGetDelimiter(Export export) {
		
		String delimiter = export.getAttributeAsString(DELIMITER);
	
		return StringUtils.isBlank(delimiter) ? DEFAULT_DELIMITER : delimiter;
	
	}

	/** 写入表的内容 */
	public void body(TableModel model, Column column) {

		plainData.append(formatValue(column.getCellDisplay())).append(
				doGetDelimiter(doGetExport(model)));

		if (column.isLastColumn()) {

			plainData.append(line);
		}
		//System.out.println(plainData.length());
	}

	/** 最终渲染字符串 */
	public Object afterBody(TableModel model) {
		return plainData.toString();
	}

	/**格式化数据内容*/
	protected String formatValue(String value) {
		if (value == null) {
			return "";
		}
		StringBuilder valueBuilder = new StringBuilder();
		String trimValue = value.trim();
		valueBuilder.append(addEqualSign(trimValue)).append(doGetBothSidesSymbol()).append(handleSymbol(trimValue)).append(doGetBothSidesSymbol());
		return valueBuilder.toString();
	}
	
	/***
	 *检测格式是否需要加入"=" 号<br>
	 *<br>
	 *如果是以下特征的格式<br>
	 *则excel打开的时候会讲0去除<br>
	 *<br>
	 *对于Excel来说<br>
	 *以下左侧的文本会展现为右侧的文本<br>
	 *01234    ->  1234<br>
	 *-01234   -> -1234<br>
	 *--01234  ->  1234<br>
	 *---01234 -> -1234<br>
	 */
	protected String addEqualSign(String value){
//		String newValue = value.replaceAll("[-]+(0+.+)", "$1");
//		if(NumberUtils.isNumber(newValue)&&newValue.startsWith("0")){//是否 在 替换完数字前面的“-”号后       开始为0的数字
//			return "=";
//		}else{
//			return "";
//		}
		return "=";
	}


	/**处理双引号*/
	protected String handleSymbol(String content){
		return content.replaceAll(doGetBothSidesSymbol(), "\"\"");
	}
	
	/**获得内容两边的双引号*/
	protected String doGetBothSidesSymbol(){
		return "\"";
	}
	
	public static void main(String[] args) {
		System.out.println(NumberUtils.isNumber("-01234"));
		System.out.println("01234".replaceAll("[-]+(0+.+)", "$1"));
		System.out.println("-01234".replaceAll("[-]+(0+.+)", "$1"));
		System.out.println("--01234".replaceAll("[-]+(0+.+)", "$1"));
		System.out.println("---01234".replaceAll("[-]+(0+.+)", "$1"));
	}
}
