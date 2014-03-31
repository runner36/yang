package com.winchannel.order.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.conf.BaseConfigurator;
import com.winchannel.base.model.BaseUser;
import com.winchannel.base.utils.Constants;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.mdm.distributor.model.MdmDistributor;
import com.winchannel.mdm.distributor.service.MdmDistributorManager;
import com.winchannel.order.model.OrderAgreementLog;
import com.winchannel.order.service.OrderAgreementLogManager;

public class OrderAgreementLogAction extends StrutsEntityAction<OrderAgreementLog, OrderAgreementLogManager> {
	private MdmDistributorManager mdmDistributorManager;
	private BaseConfigurator configurator = BaseConfigurator.getInstance();
	
	public MdmDistributorManager getMdmDistributorManager() {
		return mdmDistributorManager;
	}

	public void setMdmDistributorManager(MdmDistributorManager mdmDistributorManager) {
		this.mdmDistributorManager = mdmDistributorManager;
	}

	protected void onInitForm(ActionForm form, HttpServletRequest request,
			OrderAgreementLog orderAgreementLog) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		
		if(orderAgreementLog.getMdmDistributor()!=null){
			form1.set("distCode", orderAgreementLog.getMdmDistributor().getDistCode());
			form1.set("distName",orderAgreementLog.getMdmDistributor().getDistName());
		}
		
		if(orderAgreementLog.getBaseUser()!=null){
			form1.set("userAccount", orderAgreementLog.getBaseUser().getUserAccount());
		}
		
		if(orderAgreementLog.getBaseEmployee()!=null){
			form1.set("empName", orderAgreementLog.getBaseEmployee().getEmpName());
		}
		
	}
	
	public ActionForward viewAgreementLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		StringBuffer txt=new StringBuffer();
		InputStream is=null;
		BufferedReader br=null;
			try{
			 	is=new FileInputStream(configurator.getOrderAgreementLogUrl());
			 	if(is!=null){
			 		//生产、测试环境有点不统一
			        //br=new BufferedReader(new InputStreamReader(is,"GBK"));  
			 		br=new BufferedReader(new InputStreamReader(is,"utf-8"));  
			        String s="";   
			        while((s=br.readLine())!=null)   
			        	txt.append(s).append("<br />");   
		         }
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
			  	if(is!=null){
			  		try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  	}
			  	if(br!=null){
			  		try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  	}
			}
		//System.out.println(txt.toString());
		request.setAttribute("orderAgreementLogTxt", txt.toString());
		return new ActionForward("/help/orderAgreementLog.jsp");
		
	}
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String checkAgree = request.getParameter("flag");
		response.setCharacterEncoding("gbk");
		PrintWriter wr = null;
		String exceptionMsg=null;
		try {
			wr = response.getWriter();
			if(checkAgree!=null&&checkAgree.equals("1")){
				BaseUser userInfo = (BaseUser) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				MdmDistributor mdist = mdmDistributorManager.getMdmDistributorByEmployeeId(userInfo.getBaseEmployee().getEmpId());
				OrderAgreementLog orderAgreementLog=this.entityManager.findUniqueEntity(" from OrderAgreementLog where mdmDistributor.distId=?", mdist.getDistId());
//				if(orderAgreementLog==null){
					orderAgreementLog=new OrderAgreementLog();
					orderAgreementLog.setBaseUser(userInfo);
					orderAgreementLog.setBaseEmployee(userInfo.getBaseEmployee());
					orderAgreementLog.setCreatedByName(userInfo.getBaseEmployee().getEmpName());
					orderAgreementLog.setStatus(1);
					orderAgreementLog.setMdmDistributor(mdist);
					orderAgreementLog.setCreateDate(new Date());
//				}else{
//					orderAgreementLog.setStatus(1);
//				}			
				this.entityManager.save(orderAgreementLog);
				request.getSession().setAttribute(Constants.ORDER_AGREEMENTLOG,"1");
				wr.write("SUCCESS");
			}else{
				wr.write("无效选择！");
			}
			
		} catch (Exception e) {
			exceptionMsg="协议签署失败!/n";
			log.error("协议签署失败!", e);
		}finally {
			if (wr != null) {
				if(StringUtils.isNotEmpty(exceptionMsg)){
					wr.write(exceptionMsg);
				}
				wr.flush();
				wr.close();
			}
		}
		return null;
	}
	
	
	public ActionForward importCon(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("imp");
	} 
	
	//下载
	public ActionForward downStoreImpAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "订单补充协议签订";
		String path = configurator.getOrderAgreementLogUrl();
		File file = new File(path);
		InputStream in;
		try {
    		in = new FileInputStream(file);
    		OutputStream os = response.getOutputStream();
 			response.setContentType("txt");
 			response.setHeader("Content-Disposition", "attachment; filename=" + new String((fileName + ".txt").getBytes("GBK"), "ISO-8859-1"));
 			int count = 0;
 			byte[] buf = new byte[8096];
 			while ((count = in.read(buf, 0, buf.length)) != -1) {
 				os.write(buf, 0, count);
 			}
 			os.flush(); // 刷新页面输出流
 			in.close(); // 关闭文件流程
 			os.close(); // 关闭输出流，完成下载
		} catch (Exception e) {
			request.setAttribute("mess", "<font color='#FF0000'>当前没有可下载文件!</font>");
		}
		return mapping.findForward("imp");
	}

	//导入
	public ActionForward saveimpAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		LazyValidatorForm bizImpExcelForm = (LazyValidatorForm) form;
		FormFile F = (FormFile) bizImpExcelForm.get("fileupload");
		
		// 如果没有上传文件即上传文件大小为0，就跳转会上传页
		if (F.getFileSize() == 0) {
			request.setAttribute("mess", "<font color='#FF0000'>导入失败：</font><br>导入文件为空.....");
			return mapping.findForward("imp");
		}
		String ext = F.getFileName().substring(F.getFileName().lastIndexOf(".") + 1, F.getFileName().length()); // 获取文件类型，即扩展名,通过String类的substring方法截取字符串，lastIndexOf获取某个字符串最后出现的索引。
		ext = ext.toLowerCase();// 全部转换成小写。
		if (!"txt".equals(ext)) {
			request.setAttribute("mess", "<font color='#FF0000'>导入失败：</font><br>文件不是txt类型的或文件处于打开状态！");
			return mapping.findForward("imp");
		}
		//原文件重命名
		String path1 = configurator.getOrderAgreementLogUrl();
		String path2 = configurator.getOrderAgreementLogUrlAll()+"orderAgreementLog"+String.valueOf(System.currentTimeMillis())+".txt";
		File file = new File(path1);
		file.renameTo(new File(path2));
		//保存上传文件
		InputStream is = null;
		try {
		   is = F.getInputStream();
		   String filePath=configurator.getOrderAgreementLogUrlAll();
		   filePath=filePath+"orderAgreementLog.txt";
		   FileOutputStream output=new FileOutputStream(filePath);
		   int x=0; 
		   while((x=is.read())!=-1){ 
		    output.write(x);
		    request.setAttribute("mess", "<font color='#FF0000'>导入成功 ! </font><br>");
		   } 
		   output.flush(); 
		   output.close(); 
		   is.close();
		} catch (Exception e) {
			this.saveDirectlyError(request, e.getMessage());
		}
		 F.destroy();
		return mapping.findForward("imp");
	}
	
}
