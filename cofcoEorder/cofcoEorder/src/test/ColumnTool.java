package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ColumnTool {
	
	public static void main(String[] args) {
		
		File orgFile = new File(
				"E:/git/yangjunshuai/cofcoEorder/cofcoEorder/src/test/组织架构倒入-正式(完整).xlsx");
		File outFile = new File("d:/tmp/test2014"+System.currentTimeMillis()+".xls");
		dealOrg(orgFile,outFile);
	}
	
	public static void dealOrg(File orgFile,File outFile){
		InputStream in;
		try {
			in = new FileInputStream(orgFile);
			Workbook wb = WorkbookFactory.create(in);
			int count = 0;
			Sheet out = wb.createSheet("theout2");
			Sheet se = wb.getSheetAt(0);//厨房
			count = deal(out,se,count); //处理并写入到 the out 输出页
			count = deal(out,wb.getSheetAt(1),count);
			count = deal(out,wb.getSheetAt(2),count);
			count = deal(out,wb.getSheetAt(3),count);
			count = deal(out,wb.getSheetAt(4),count);
//			count = deal(out,wb.getSheetAt(5),count);
		System.out.println("count = "+count);
			
		FileOutputStream toFile = new FileOutputStream(outFile);
//		wb.removeSheetAt(0);
//		wb.removeSheetAt(1);
//		wb.removeSheetAt(2);
//		
		wb.write(toFile);
		toFile.close();
		org.apache.commons.io.IOUtils.closeQuietly(in);
		org.apache.commons.io.IOUtils.closeQuietly(toFile);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	static int deal(final Sheet out,final Sheet se,int count){
		Map<String,String> map = dealFile(se);
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			String name = map.get(key);
			Row r = out.createRow(count++);
			if(key.indexOf("_")<0){
				r.createCell(0).setCellValue("中国食品");
			} else {
				r.createCell(0).setCellValue(key.substring(key.indexOf("_")+1));
			}
			r.createCell(1).setCellValue(key);
			r.createCell(2).setCellValue(name);
			r.createCell(3).setCellValue(name);
		}
		return count;
	}
	
	
	static Map<String,String> dealFile(Sheet se){
		int num = se.getLastRowNum();
		System.out.println("行数 = " + num);
		Map<String,String> areas = new LinkedHashMap<String,String>();
		int rand = -1;
		int hrand =3;
		for (int i = hrand; i < num; i++) {
			Row r = se.getRow(i);
			String code1 = getValue(r.getCell(3+rand));
			String name1 = getValue(r.getCell(4+rand));
			if(!areas.containsKey(code1))
				areas.put(code1, name1);
			
			
			String code2 =getValue(r.getCell(5+rand))+"_"+code1;
			String name2 =getValue(r.getCell(6+rand));
			if(!areas.containsKey(code2))
				areas.put(code2, name2);
			
			
			String code3 =getValue(r.getCell(7+rand))+"_"+code2;
			String name3 =getValue(r.getCell(8+rand));
			if(!areas.containsKey(code3))
				areas.put(code3, name3);
			
			String code4 =getValue(r.getCell(9+rand))+"_"+code3;
			String name4 =getValue(r.getCell(10+rand));
			if(!areas.containsKey(code4))
				areas.put(code4, name4);	
		}
		return areas;
	}
	
	
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
