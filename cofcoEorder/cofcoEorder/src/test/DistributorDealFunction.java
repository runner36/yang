package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.mdm.distributor.model.MdmDistributor;

public class DistributorDealFunction implements DealFounction {
	
	public static void main(String[] args) {
		File orgFile = new File(
				"D:/work_yxt/working/中粮订单系统/哈哈哈/工作表 在 Basis (1).xlsx");
		File outFile = new File("D:/work_yxt/working/中粮订单系统/哈哈哈/out_"+System.currentTimeMillis()+".xls");
		new DistributorDealFunction().deal(orgFile, outFile);
	}

	@Override
	public void deal(File file, File toFile) {
		dealOrg(file, toFile);
	}

	public void dealOrg(File orgFile, File outFile) {
		dealOrg2(orgFile, outFile, null);
	}

	
	
	/**
	 * 引入策略方法dealInf
	 * 
	 * @param orgFile
	 * @param outFile
	 * @param dealInfo
	 */
	public void dealOrg2(File orgFile, File outFile, DealFounction dealInf) {

		InputStream in;
		try {
			in = new FileInputStream(orgFile);
			Workbook wb = WorkbookFactory.create(in);
			int count = 0;
			Sheet out_fail = wb.createSheet("导入失败1");
			Sheet out_succ = wb.createSheet("导入成功1");
			Sheet se = wb.getSheetAt(0);// 厨房

			int num = se.getLastRowNum();
			System.out.println("行数 = " + num);
			List<DistEntity> list = new ArrayList<DistEntity>();

			Map<String, String> orgs = OrgsTool.getOrgCodesFromExcel();
			Map<String, Integer> rs = new HashMap<String, Integer>();
			Map<String, Integer> orgrs = new HashMap<String, Integer>();
			
			int count_succ = 0;
			int count_fail = 0;

			int hrand = 2;
			for (int i = hrand; i < num; i++) {
				Row r = se.getRow(i);
			
				String orgCode1 = ExcelTool.getValue(r.getCell(getIndex("O"))); // 大区
				String orgCode2 = ExcelTool.getValue(r.getCell(getIndex("A"))); // 营销公司
				String orgCode3 = ExcelTool.getValue(r.getCell(getIndex("Q"))); // 省级
				String orgCode4 = ExcelTool.getValue(r.getCell(getIndex("S"))); // 城市群
				String orgCode = orgCode4 + "_" + orgCode3 + "_" + orgCode2
						+ "_" + orgCode1;
				String distCode = ExcelTool.getValue(r.getCell(getIndex("G")));
				String distName = ExcelTool.getValue(r.getCell(getIndex("H")));

				if (orgs.containsKey(orgCode)) {
					
					if(orgrs.containsKey(orgCode+distCode)){ //组织经销商判重
						continue;
					}
					orgrs.put(orgCode+distCode, 1);
					
					Row rr = out_succ.createRow(count_succ++);
					
					if(!rs.containsKey(distCode)){
						rs.put(distCode,0);
						rr.createCell(getIndex("E")).setCellValue(distCode);
					} else {
						rs.put(distCode,rs.get(distCode)+1);
						rr.createCell(getIndex("E")).setCellValue(distCode+ (rs.get(distCode)<10?"0"+rs.get(distCode):rs.get(distCode)));
					}
					rr.createCell(getIndex("A")).setCellValue(orgCode);
					rr.createCell(getIndex("F")).setCellValue(distName);
				} else {
					Row rr = out_fail.createRow(count_fail++);
//					rr.createCell(getIndex("A")).setCellValue(orgCode);
//					rr.createCell(getIndex("E")).setCellValue(distCode);
//					rr.createCell(getIndex("F")).setCellValue(distName);
					for(int j = 0 ;j<40;j++){
						
						rr.createCell(j).setCellValue(ExcelTool.getValue(r.getCell(j)));
					}
				}

			}
			
			System.out.println("成功数=="+count_succ);
			System.out.println("失败数=="+count_fail);
			
			FileOutputStream outIo = new FileOutputStream(outFile);
			wb.write(outIo);
			org.apache.commons.io.IOUtils.closeQuietly(in);
			org.apache.commons.io.IOUtils.closeQuietly(outIo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 引入策略方法dealInf
	 * 
	 * @param orgFile
	 * @param outFile
	 * @param dealInfo
	 */
//	public void dealOrg(File orgFile, File outFile, DealFounction dealInf) {
//
//		InputStream in;
//		try {
//			in = new FileInputStream(orgFile);
//			Workbook wb = WorkbookFactory.create(in);
//			int count = 0;
//			Sheet out_fail = wb.createSheet("导入失败1");
//			Sheet out_succ = wb.createSheet("导入成功1");
//			Sheet se = wb.getSheetAt(2);// 厨房
//
//			int num = se.getLastRowNum();
//			System.out.println("行数 = " + num);
//			List<DistEntity> list = new ArrayList<DistEntity>();
//
//			Map<String, BaseOrg> orgs = OrgsTool.getOrgsByJdbc();
//			Map<String, MdmDistributor> dists = OrgsTool.getDistsByJdbc();
//
//			
//			Map<String, Integer> rs = new HashMap<String, Integer>();
//			
//			int count_succ = 0;
//			int count_fail = 0;
//
//			int hrand = 2;
//			for (int i = hrand; i < num; i++) {
//				Row r = se.getRow(i);
//				String orgCode1 = ExcelTool.getValue(r.getCell(getIndex("O"))); // 大区
//				String orgCode2 = ExcelTool.getValue(r.getCell(getIndex("A"))); // 营销公司
//				String orgCode3 = ExcelTool.getValue(r.getCell(getIndex("Q"))); // 省级
//				String orgCode4 = ExcelTool.getValue(r.getCell(getIndex("S"))); // 城市群
//				String orgCode = orgCode4 + "_" + orgCode3 + "_" + orgCode2
//						+ "_" + orgCode1;
//				String distCode = ExcelTool.getValue(r.getCell(getIndex("G")));
//				String distName = ExcelTool.getValue(r.getCell(getIndex("H")));
//
//				if (orgs.containsKey(orgCode)) {
//					
//					//主账号设置为其活跃账户，
////					if(dists.containsKey(distCode)){
////						if(dists.get(distCode).getState().equals("1")){
////							
////						}
////					}
//					Row rr = out_succ.createRow(count_succ++);
//					
//					if(!rs.containsKey(distCode)){
//						rs.put(distCode,0);
//						rr.createCell(getIndex("E")).setCellValue(distCode);
//					} else {
//						rs.put(distCode,rs.get(distCode)+1);
//						rr.createCell(getIndex("E")).setCellValue(distCode+ (rs.get(distCode)<10?"0"+rs.get(distCode):rs.get(distCode)));
//					}
//					rr.createCell(getIndex("A")).setCellValue(orgCode);
//					rr.createCell(getIndex("F")).setCellValue(distName);
//				} else {
//					Row rr = out_fail.createRow(count_fail++);
////					rr.createCell(getIndex("A")).setCellValue(orgCode);
////					rr.createCell(getIndex("E")).setCellValue(distCode);
////					rr.createCell(getIndex("F")).setCellValue(distName);
//					for(int j = 0 ;j<40;j++){
//						
//						rr.createCell(j).setCellValue(ExcelTool.getValue(r.getCell(j)));
//					}
//				}
//
//			}
//			
//			System.out.println("成功数=="+count_succ);
//			System.out.println("失败数=="+count_fail);
//			
//			FileOutputStream outIo = new FileOutputStream(outFile);
//			wb.write(outIo);
//			org.apache.commons.io.IOUtils.closeQuietly(in);
//			org.apache.commons.io.IOUtils.closeQuietly(outIo);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Deprecated
//	int deal(final Sheet out, final Sheet se, int count) {
//
//		List<DistEntity> list = dealFile(out, se);
//
//		// 写导成功的。
//		Row r = out.createRow(count++);
//		r.createCell(2).setCellValue("");
//		r.createCell(3).setCellValue("");
//		return count;
//	}
//
//	@Deprecated
//	List<DistEntity> dealFile(final Sheet out, final Sheet se) {
//
//		int num = se.getLastRowNum();
//		System.out.println("行数 = " + num);
//		List<DistEntity> list = new ArrayList<DistEntity>();
//
//		Map<String, BaseOrg> orgs = OrgsTool.getOrgsByJdbc();
//		Map<String, MdmDistributor> dists = OrgsTool.getDistsByJdbc();
//
//		int count = 1;
//
//		int hrand = 2;
//		for (int i = hrand; i < num; i++) {
//			Row r = se.getRow(i);
//			String orgCode1 = ExcelTool.getValue(r.getCell(getIndex("O"))); // 大区
//			String orgCode2 = ExcelTool.getValue(r.getCell(getIndex("A"))); // 营销公司
//			String orgCode3 = ExcelTool.getValue(r.getCell(getIndex("Q"))); // 省级
//			String orgCode4 = ExcelTool.getValue(r.getCell(getIndex("S"))); // 城市群
//			String orgCode = orgCode4 + "_" + orgCode3 + "_" + orgCode2 + "_"
//					+ orgCode1;
//			String distCode = ExcelTool.getValue(r.getCell(getIndex("G")));
//			String distName = ExcelTool.getValue(r.getCell(getIndex("H")));
//
//			if (orgs.containsKey(orgCode)) {
//				Row rr = out.createRow(count++);
//				rr.createCell(getIndex("A")).setCellValue(orgCode);
//				rr.createCell(getIndex("E")).setCellValue(distCode);
//				rr.createCell(getIndex("F")).setCellValue(distName);
//			} else {
//
//			}
//
//		}
//		return list;
//	}

	class DistEntity {

		String orgCode;
		String distCode;
		String distName;
		String deleteFlage;

		public DistEntity(String orgCode, String distCode, String distName,
				String deleteFlage) {
			super();
			this.orgCode = orgCode;
			this.distCode = distCode;
			this.distName = distName;
			this.deleteFlage = deleteFlage;
		}
	}

	static int getIndex(String aa) {
		if(StringUtils.isEmpty(aa))
			return -1;
		if(StringUtils.length(aa)==1)
			return "abcdefghigklmnopqrstuvwxyz".indexOf(aa.toLowerCase());
		else {
//			int sum=0;
//			for(int i=aa.length();i>0;i--)
//				sum += (getIndex(aa.charAt(i))+1)*(aa.length()-i);
//			return sum;
			return 0;
		}
	}


}
