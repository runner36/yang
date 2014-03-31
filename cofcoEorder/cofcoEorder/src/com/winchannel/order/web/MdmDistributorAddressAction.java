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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.bean.DistEmpProdgroup;
import com.winchannel.order.model.MdmDistributorAddress;
import com.winchannel.order.service.MdmDistributorAddressManager;


/**
 * 
 * @author caishang
 * 
 */

public class MdmDistributorAddressAction extends StrutsEntityAction<MdmDistributorAddress, MdmDistributorAddressManager> {
	private MdmDistributorAddressManager mdmDistributorAddressManager;
	private MdmDistributorManager mdmDistributorManager;
	
	
	/**
	 * 编辑页查看
	 * 
	 */			
	protected void onInitForm(ActionForm form, HttpServletRequest request, MdmDistributorAddress mdmDistributorAddress) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		if (null != mdmDistributorAddress.getMdmDistributor()) {
			form1.set("distId", mdmDistributorAddress.getMdmDistributor().getDistId().toString());
			form1.set("distCode", mdmDistributorAddress.getMdmDistributor().getDistCode());
			form1.set("distName", mdmDistributorAddress.getMdmDistributor().getDistName());
		}
		if (null != mdmDistributorAddress.getBaseDictItem()) {
			form1.set("prodBrandId", mdmDistributorAddress.getBaseDictItem().getDictItemId().toString());
			form1.set("prodBrandName", mdmDistributorAddress.getBaseDictItem().getItemName());
		}
		if(null == mdmDistributorAddress.getId()){
			form1.set("status","1");
		}
		
	}

	//ajax判断是否有相同的客户，物料组,送达方编码一组数据
	public ActionForward isSame(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		List<MdmDistributorAddress> mdmDistributorAddress= new ArrayList<MdmDistributorAddress>();
		Long distId = Long.valueOf(request.getParameter("distId"));
		Long prodBrandId = Long.valueOf(request.getParameter("prodBrandId"));
		String shiptoCode=request.getParameter("shiptoCode");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM MDM_DISTRIBUTOR_ADDRESS WHERE DIST_ID="+distId+" AND DICT_ITEM_ID="+prodBrandId+" AND SHIPTO_CODE='"+shiptoCode+"'");
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			sql.append(" and ID != " + request.getParameter("id"));
		}
		mdmDistributorAddress=this.entityManager.executeSqlQuery(MdmDistributorAddress.class, sql.toString());
		String text = "";
		
		if (mdmDistributorAddress.size() > 0) {
			text = "1";
		}else{
			text = "0";
		}
		this.renderXML(response, text);
		return null;
	}
	
	/**
	 * 
	 * 新增和修改
	 * 
	 */
	protected void onInitEntity(ActionForm form, HttpServletRequest request, MdmDistributorAddress mdmDistributorAddress) {
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			mdmDistributorAddress.setUpdatedByname(employee.getEmpName());
			mdmDistributorAddress.setUpdatedDate(DateUtils.getDate());
			mdmDistributorAddress.setUpdatedByid(employee.getEmpId());
		}else{
			mdmDistributorAddress.setCreatedDate(DateUtils.getDate());
			mdmDistributorAddress.setCreatedByname(employee.getEmpName());
			mdmDistributorAddress.setCreatedByid(employee.getEmpId());
			mdmDistributorAddress.setStatus(new Long(1));
		}
		if (StringUtils.isNotBlank(request.getParameter("distId"))) {
			mdmDistributorAddress.setMdmDistributor(mdmDistributorManager.get(Long.valueOf(request.getParameter("distId"))));
		} else {
			mdmDistributorAddress.setMdmDistributor(null);
		}
		if (StringUtils.isNotBlank(request.getParameter("prodBrandId"))) {
			mdmDistributorAddress.setBaseDictItem(baseDictManager.get(Long.valueOf(request.getParameter("prodBrandId"))));
		} else {
			mdmDistributorAddress.setBaseDictItem(null);
		}
	     
		mdmDistributorAddressManager.save(mdmDistributorAddress);
		}
	
	
	//模版下载
	public ActionForward downStoreImpAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mbPath = "/templet/客户送达方数据.xls";
		String fileName = "客户送达方数据";
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
	
	

	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	public MdmDistributorAddressManager getMdmDistributorAddressManager() {
		return mdmDistributorAddressManager;
	}

	public void setMdmDistributorAddressManager(
			MdmDistributorAddressManager mdmDistributorAddressManager) {
		this.mdmDistributorAddressManager = mdmDistributorAddressManager;
	}
}
