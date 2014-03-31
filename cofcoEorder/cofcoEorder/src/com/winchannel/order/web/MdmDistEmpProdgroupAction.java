package com.winchannel.order.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winchannel.core.utils.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.service.BaseEmployeeManager;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.bean.DistEmpProdgroup;
import com.winchannel.order.model.MdmDistEmpProdgroup;
import com.winchannel.order.service.MdmDistEmpProdgroupManager;



/**
 * 
 * @author caishang
 * 
 */

public class MdmDistEmpProdgroupAction extends StrutsEntityAction<MdmDistEmpProdgroup, MdmDistEmpProdgroupManager> {
	private MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager;
	private MdmDistributorManager mdmDistributorManager;
	private BaseEmployeeManager baseEmployeeManager;
	

	/**
	 * 编辑页查看
	 * 
	 */		
	protected void onInitForm(ActionForm form, HttpServletRequest request, MdmDistEmpProdgroup mdmDistEmpProdgroup) {
		LazyValidatorForm mdmDistEmpProdgroupForm = (LazyValidatorForm) form;
		if (mdmDistEmpProdgroup.getMdmDistributor() != null) {
			mdmDistEmpProdgroupForm.set("distId", mdmDistEmpProdgroup.getMdmDistributor().getDistId().toString());
			mdmDistEmpProdgroupForm.set("distCode", mdmDistEmpProdgroup.getMdmDistributor().getDistCode());
			mdmDistEmpProdgroupForm.set("distName", mdmDistEmpProdgroup.getMdmDistributor().getDistName());
		}
		if (mdmDistEmpProdgroup.getBaseDictItem() != null) {
			mdmDistEmpProdgroupForm.set("prodBrandId", mdmDistEmpProdgroup.getBaseDictItem().getDictItemId().toString());
			mdmDistEmpProdgroupForm.set("prodBrandName", mdmDistEmpProdgroup.getBaseDictItem().getItemName());
		}
		if (mdmDistEmpProdgroup.getBaseEmployee() != null) {
			mdmDistEmpProdgroupForm.set("empId", mdmDistEmpProdgroup.getBaseEmployee().getEmpId().toString());
			mdmDistEmpProdgroupForm.set("empCode", mdmDistEmpProdgroup.getBaseEmployee().getEmpCode().toString());
			mdmDistEmpProdgroupForm.set("empName", mdmDistEmpProdgroup.getBaseEmployee().getEmpName().toString());
		}
		if(mdmDistEmpProdgroup.getId()!=null){
			request.setAttribute("flag","1");
		}else{
			request.setAttribute("flag","0");
		}
	}

	
	/**
	 * 
	 * 新增和修改
	 * 
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request, MdmDistEmpProdgroup mdmDistEmpProdgroup) {
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		mdmDistEmpProdgroupManager.saveMdmDistEmpProdgroup(employee,form, request, mdmDistEmpProdgroup);
		}
	
	//ajax判断是否有相同的。
	public ActionForward isSame(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		List<DistEmpProdgroup> distEmpProdgroups= new ArrayList<DistEmpProdgroup>();
		Long distId = Long.valueOf(request.getParameter("distId"));
		Long empId = Long.valueOf(request.getParameter("empId"));
		Long prodBrandId = Long.valueOf(request.getParameter("prodBrandId"));
		String effectiveTime=request.getParameter("effectiveTime");
		String expiryTime=request.getParameter("expiryTime");
		if(expiryTime==null||expiryTime.equals("")){
			expiryTime="2099-12-30";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id as id FROM MDM_DIST_EMP_PRODGROUP WHERE DIST_ID="+distId+" AND DICT_ITEM_ID="+prodBrandId+" AND EMP_ID="+empId
					+" and ((EXPIRY_TIME IS Null and EFFECTIVE_TIME <'"+expiryTime+"') or"
					+" (EXPIRY_TIME IS not Null and not('"+effectiveTime+"'>=EXPIRY_TIME or '"+expiryTime+"'<=+EFFECTIVE_TIME))"
					+")");
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			sql.append(" and ID != " + request.getParameter("id"));
		}
		distEmpProdgroups=mdmDistEmpProdgroupManager.executeSqlQuery(DistEmpProdgroup.class, sql.toString());
		String text = "";
		
		Date eff=DateUtility.strToDate(effectiveTime);
		Date exp=DateUtility.strToDate(expiryTime);
		Date now = DateUtils.getDate();
		if(exp.getTime()-eff.getTime()>0){
			text = "0";
			if (eff.before(now)&&(StringUtils.isBlank(request.getParameter("id")))) {
				text = "3";
			} 
			if (distEmpProdgroups.size() > 0) {
				text = "1";
			} 
		}else{
			text="2";
		}
		this.renderXML(response, text);
		return null;
	}
	//模版下载
	public ActionForward downStoreImpAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mbPath = "/templet/客户_产品组_业代关系维护.xls";
		String fileName = "客户_产品组_业代关系_导入模板";
		String path = request.getSession().getServletContext().getRealPath(mbPath);
		File downFile = new File(path);
		InputStream in;
		try {
			in = new FileInputStream(downFile);
			OutputStream os = response.getOutputStream();
			response.setContentType("xls");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO-8859-1"));
			int count = 0;
			byte[] buf = new byte[8096];
			while ((count = in.read(buf, 0, buf.length)) != -1) {
				os.write(buf, 0, count);

			}
			os.flush(); // 刷新页面输出流
			in.close(); // 关闭文件流程
			os.close(); // 关闭输出流，完成下载
		} catch (Exception e) {
			request.setAttribute("mess", "<font color='#FF0000'>模板路径不正确!</font>");
		}

		return null;
	}

	public ActionForward importCon(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("imp");
	} 
	//批量导入
	public ActionForward saveimpAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		String ExcelType = "application/vnd.ms-excel";
		
		LazyValidatorForm bizImpExcelForm = (LazyValidatorForm) form;
		FormFile F = (FormFile) bizImpExcelForm.get("fileupload");
		System.out.println(F.getFileSize());
		
		// 如果没有上传文件即上传文件大小为0，就跳转会上传页
		if (F.getFileSize() == 6656) {
			request.setAttribute("mess", "<font color='#FF0000'>导入失败：</font><br>导入文件为空.....");
			return mapping.findForward("imp");
		}
		// 得到上传文件名和大小
		if (!ExcelType.equals(F.getContentType())) {
			request.setAttribute("mess", "<font color='#FF0000'>导入失败：</font><br>文件不是Excel类型的或文件处于打开状态！");
			return mapping.findForward("imp");
		}
		// 获得上传文件输入流
		InputStream is = null;
		try {
			is = F.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			String mess = entityManager.saveExcel(is,request);
			request.setAttribute("mess", mess);
			
		} catch (Exception e) {
			this.saveDirectlyError(request, e.getMessage());
		}
		return mapping.findForward("imp");
	}
	
	/**
	 * @param验证客户编码
	 * @return客户名称
	 * @author 史京茹
	 */
	public ActionForward getDistName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String distCode = request.getParameter("distCode");
		MdmDistributor dist = entityManager.getMdmDistManagerId(distCode);
		if (dist != null) {
			String distName = dist.getDistId()+","+dist.getDistName();
			this.renderXML(response, distName);
		}
		return null;
	}
	/**
	 * @param验证业代编码
	 * @return业代名称
	 * @author 史京茹
	 */
	public ActionForward getEmpName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String empCode = request.getParameter("empCode");
		BaseEmployee emp = entityManager.getBaseEmployee(empCode);
		if (emp != null) {
			String empName = emp.getEmpId() + "," + emp.getEmpName();
			this.renderXML(response, empName);
		}
		return null;
	}
	
	

	public BaseEmployeeManager getBaseEmployeeManager() {
		return baseEmployeeManager;
	}

	public void setBaseEmployeeManager(BaseEmployeeManager baseEmployeeManager) {
		this.baseEmployeeManager = baseEmployeeManager;
	}

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}
    
	public MdmDistEmpProdgroupManager getMdmDistEmpProdgroupManager() {
		return mdmDistEmpProdgroupManager;
	}

	public void setMdmDistEmpProdgroupManager(
			MdmDistEmpProdgroupManager mdmDistEmpProdgroupManager) {
		this.mdmDistEmpProdgroupManager = mdmDistEmpProdgroupManager;
	}
	

}
