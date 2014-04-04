package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.winchannel.base.model.BaseOrg;

public class ExcelHelperJdbc2 {

	static class Entity {

		public Entity(String code, String name, String org) {
			super();
			this.code = code;
			this.name = name;
			this.org = org;
		}
		
		public Entity(String code, String name, String org,String state) {
			super();
			this.code = code;
			this.name = name;
			this.org = org;
			this.state = state;
		}

		String code;
		String name;
		String org;
		String state;
	}



	
	public static Map<String,String> getOrgCodesByJdbc(){
		
	    ApplicationContext	ac =new  ClassPathXmlApplicationContext("classpath:spring/**/*.xml");
		System.out.println(ac.containsBean("aa"));
		Map<String,String> rs = new HashMap<String, String>();
		
		SessionFactory session = (SessionFactory) ac.getBean("sessionFactory");
		List baseorgs = session.openSession().createCriteria(BaseOrg.class).list();
		for (int i = 0; i < baseorgs.size(); i++) {
			BaseOrg o = (BaseOrg) baseorgs.get(i);
			rs.put(o.getOrgCode(), o.getOrgCode());
		}
		
		return rs;
		
	}
	
	public static Map<String,String> getOrgCodes(){
		
		File orgFile = new File(
				"D:/work_yxt/workspace/DMS_ForUPdate/src/test/java/com/winchannel/examples/service/组织架构倒入-正式(完整).xlsx");
		InputStream in;
		try {
			in = new FileInputStream(orgFile);
			Workbook wb = WorkbookFactory.create(in);
			Sheet se = wb.getSheetAt(1);
			int num = se.getLastRowNum();
			System.out.println("行数 = " + num);
			Map<String,String> orgs = new HashMap<String,String>();
			for (int i = 0; i < num; i++) {
				Row r = se.getRow(i);
				String code = getValue(r.getCell(1));
				orgs.put(code, code);
			}
			
			return orgs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void deal(Map<String,String> map) throws InvalidFormatException, IOException {
		
		File file = new File(
				"D:/work_yxt/workspace/DMS_ForUPdate/src/test/java/com/winchannel/examples/service/test002.xls");
		File destFile = new File("d:/test001.xls");
		deal(map,file,destFile);
	}
	public static void deal(Map<String,String> map,File sourceFile,File destFile) throws InvalidFormatException, IOException{
		File file = sourceFile;
		InputStream in = new FileInputStream(file);
		Workbook wb = WorkbookFactory.create(in);
		//wb.setActiveSheet(2);
		Sheet se = wb.getSheetAt(2);
		Sheet ssFail = wb.createSheet("result_fail");
		int failNum=0;

		int num = se.getLastRowNum();
		System.out.println("行数 = " + num);
		Map<String, Integer> rs = new HashMap<String, Integer>();
		List<Entity> rs1 = new ArrayList<Entity>();
		Map orgs = map;
		for (int i = 0; i < num; i++) {
			Row r = se.getRow(i);
			String code = getValue(r.getCell(0));
			String name = getValue(r.getCell(1));
			String org = getValue(r.getCell(4));
			String state = getValue(r.getCell(3)).equals("0")?"0":"1";
			if(!orgs.containsKey(org)){
				Row rrs = ssFail.createRow(failNum++);
				rrs.createCell(0).setCellValue(code);
				rrs.createCell(1).setCellValue(name);
				rrs.createCell(4).setCellValue(org);
				rrs.createCell(3).setCellValue(state);
				continue;
			}
			if (!rs.containsKey(code)) {
				rs.put(code, 1);
				rs1.add(new ExcelHelperJdbc2.Entity(code, name, org,state));
			} else {
				int tm = rs.get(code);
				rs.remove(code);
				rs.put(code, tm + 1);
				rs1.add(new Entity(code
						+ (tm < 10 ? "0" + tm : tm), name, org,state));
			}
		}
		
		Sheet ss = wb.createSheet("result");
		int rownum = rs1.size();
		
		for (int j = 0; j < rownum; j++) {
			
			Row rr = ss.createRow(j);
			rr.createCell(0).setCellValue(rs1.get(j).code);
			rr.createCell(1).setCellValue(rs1.get(j).name);
			rr.createCell(2).setCellValue(rs1.get(j).org);
			rr.createCell(3).setCellValue(rs1.get(j).state);
			
		}
		
		
		File ofile = destFile;
		FileOutputStream out = new FileOutputStream(ofile);
		wb.write(out);
		out.close();
		org.apache.commons.io.IOUtils.closeQuietly(in);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException,
			IOException, InvalidFormatException {
		
//		Map a = getOrgCodesByJdbc();
//		System.out.println(a.keySet().size());
//		
	}

	private static String getValue(Cell c) {
		return ColumnTool.getValue(c);

	}

}
