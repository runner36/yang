package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.winchannel.base.model.BaseOrg;
import com.winchannel.mdm.distributor.model.MdmDistributor;

public class OrgsTool {
//	static SessionFactory sessionFactory;
//	
//	static {
//	    ApplicationContext	ac =new  ClassPathXmlApplicationContext("classpath:spring/**/*.xml");
//		sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
//	}
//
//	
//	public static Map<String,BaseOrg> getOrgsByJdbc(){
//		Map<String,BaseOrg> rs = new HashMap<String, BaseOrg>();
//		List baseorgs = sessionFactory.openSession().createCriteria(BaseOrg.class).list();
//		for (int i = 0; i < baseorgs.size(); i++) {
//			BaseOrg o = (BaseOrg) baseorgs.get(i);
//			rs.put(o.getOrgCode(), o);
//		}
//		return rs;
//	}
//
//	public static Map<String,MdmDistributor> getDistsByJdbc(){
//		Map<String,MdmDistributor> rs = new HashMap<String, MdmDistributor>();
//		List baseorgs = sessionFactory.openSession().createCriteria(MdmDistributor.class).list();
//		for (int i = 0; i < baseorgs.size(); i++) {
//			MdmDistributor o = (MdmDistributor) baseorgs.get(i);
//			rs.put(o.getDistCode(), o);
//		}
//		return rs;
//	}
	
	

	public static Map<String,String> getOrgCodesFromExcel(){
		
		File orgFile = new File(
				"D:/work_yxt/working/中粮订单系统/哈哈哈/组织_导入模板(1).xls");
		InputStream in;
		try {
			in = new FileInputStream(orgFile);
			Workbook wb = WorkbookFactory.create(in);
			Sheet se = wb.getSheetAt(0);
			int num = se.getLastRowNum();
			System.out.println("行数 = " + num);
			Map<String,String> orgs = new HashMap<String,String>();
			for (int i = 0; i < num; i++) {
				Row r = se.getRow(i);
				String code = ExcelTool.getValue(r.getCell(2));
				orgs.put(code, code);
			}
			
			return orgs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
