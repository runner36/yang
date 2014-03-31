package com.winchannel.message.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.LazyValidatorForm;

import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.core.exception.BusinessException;
import com.winchannel.core.utils.DateUtils;
import com.winchannel.core.utils.Page;
import com.winchannel.core.utils.SpringContext;
import com.winchannel.core.utils.StringUtils;
import com.winchannel.core.web.StrutsEntityAction;
import com.winchannel.message.model.BaseAuthoritys;
import com.winchannel.message.model.BaseOpinions;
import com.winchannel.message.model.BaseFileItem;
import com.winchannel.message.service.BaseOpinionManager;

public class BaseOpinionAction extends StrutsEntityAction<BaseOpinions, BaseOpinionManager> {

	private BaseOpinionManager baseOpinionManager;

	public void setBaseOpinionManager(BaseOpinionManager baseOpinionManager) {
		this.baseOpinionManager = baseOpinionManager;
	}

	@Override
	protected void onInitPage(ActionForm form, HttpServletRequest request, Page page) {
		String flag = request.getParameter("flag");

		if ("1".equals(flag)) {
			//page.put("$eq_CREATED", this.getCurrentUser(request).getBaseEmployee().getEmpName());
			page.put("$eq_created", this.getCurrentUser(request).getUserAccount());
		}
		page.put("sort", "created_by desc ");

		request.setAttribute("flag", flag);
		
		//List<BaseOpinion> list = this.baseOpinionManager.query(page);
		//request.setAttribute("list", list);
		
		super.onInitPage(form, request, page);
		//super.onInitList(form, request, list, page);
	}

	@Override
	protected void onInitForm(ActionForm form, HttpServletRequest request, BaseOpinions baseOpinion) {
		LazyValidatorForm form1 = (LazyValidatorForm) form;
		//request.setAttribute("flag", "0");
		request.setAttribute("flag", request.getParameter("flag"));
		
		if (baseOpinion.getTitle() != null) {
			form1.set("title", baseOpinion.getTitle().toString());
		}
		if (baseOpinion.getContent() != null) {
			String content = baseOpinion.getContent().toString();
			//	String subContent=content.substring(0, 10)+"...";
			//	form1.set("subContent", subContent);
			form1.set("content", content);

		}
		if (baseOpinion.getId() != null) {
			form1.set("id", baseOpinion.getId().toString());
		}

	}

	@Override
	protected void onInitEntity(ActionForm form, HttpServletRequest request, BaseOpinions baseOpinion) {
		//String [] types=new String []{"gif","jpg","jpeg","png","bmp"};
		BaseEmployee employee = this.getCurrentUser(request).getBaseEmployee();
		if (StringUtils.isNotBlank(request.getParameter("id"))) {
			long id = Long.parseLong(request.getParameter("id"));
			baseOpinion.setUpdated(employee.getEmpName());
			baseOpinion.setUpdatedBy(DateUtils.getDate());
		} else {
			baseOpinion.setCreatedBy(DateUtils.getDate());
			baseOpinion.setCreated(this.getCurrentUser(request).getUserAccount());
			//baseOpinion.setCreated(employee.getEmpName());
		}
		LazyValidatorForm baseOpinionForm = (LazyValidatorForm) form;

		Set<BaseAuthoritys> baseAuthorities = new HashSet<BaseAuthoritys>();
		Set<BaseFileItem> fileItems = new HashSet<BaseFileItem>();
		BaseFileItem fileItem = new BaseFileItem();

		FormFile file = (FormFile) baseOpinionForm.get("filename");
		int fsize = file.getFileSize();
		if (fsize > 3 * 1024 * 1024) {
			throw new BusinessException("上传文件过大，请重新上传");
		} else {
			;
			if (!StringUtils.isNotBlank(request.getParameter("id"))) {
				String subCode = this.getCurrentOrgSubCode(request);
				List<BaseOrg> baseOrgs = this.baseOpinionManager.getBaseOrg(subCode);
				for (int i = 0; i < baseOrgs.size() - 1; i++) {
					BaseOrg baseOrg = baseOrgs.get(i);
					long str = baseOrg.getOrgId();
					BaseAuthoritys authority = new BaseAuthoritys();
					authority.setOrgId(str);
					authority.setFlag((long) 0);
					authority.setBaseOpinion(baseOpinion);
					baseAuthorities.add(authority);
				}
				baseOpinion.setBaseAuthorities(baseAuthorities);
			}
			if (StringUtils.isNotBlank(file.getFileName())) {
				InputStream stream = null;
				FileOutputStream bos = null;
				try {
					stream = file.getInputStream();
					Calendar calendar = Calendar.getInstance();
					String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
					String filepath = SpringContext.getRealPath() + "/uploadImg/" + date;

					File f = new File(filepath);
					if (!f.exists()) {
						f.mkdirs();
					}
					String filename = file.getFileName();
//					int pos = filename.lastIndexOf(".");
//					String exFilename = filename.substring(pos + 1, filename.length());
					//	 if(!Arrays.<String>asList(types).contains(exFilename)){
					//	throw new BusinessException("对不起，上传文件的扩展名是不允许的类型！");
					//	}
//					Date da = new Date();
//					String realPath = f.getAbsolutePath() + "/" + da.getTime() + "." + exFilename;
//					int cos = realPath.lastIndexOf("/");
//					String lastName = realPath.substring(cos + 1, realPath.length());
					
					String realPath = f.getAbsolutePath() +"\\"+ filename;
					
					bos = new FileOutputStream(realPath);
					int byteread = 0;
					byte[] buffer = new byte[8192];
					while ((byteread = stream.read(buffer, 0, 1892)) != -1) {
						bos.write(buffer, 0, byteread);
						bos.flush();
					}

					fileItems = new HashSet<BaseFileItem>();
					fileItems = baseOpinion.getFileItems();
					if (!fileItems.isEmpty()) {
						for (Iterator<BaseFileItem> iterator = fileItems.iterator(); iterator.hasNext();) {
							BaseFileItem fiItem = (BaseFileItem) iterator.next();
							fiItem.setFlag((long) 1);
							baseOpinionManager.save(fiItem);
						}
						baseOpinion.setFileItems(null);
					}
					String lastPath = "uploadImg/" + date + "/" + filename;
					fileItem.setFileurl(lastPath);
					fileItem.setFlag((long) 0);
					fileItems.add(fileItem);
					baseOpinion.setFileItems(fileItems);
					fileItem.setBaseOpinion(baseOpinion);

					stream.close();
					stream = null;
					bos.close();
					bos = null;

				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException("上传文件时发生错误：" + e.getMessage());
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	protected void doDeleteEntity(ActionForm form, HttpServletRequest request) {
		// TODO Auto-generated method stub
		request.setAttribute("flag", request.getParameter("flag"));
		super.doDeleteEntity(form, request);
	}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String id = request.getParameter("id");
		BaseOpinions baseOp = this.entityManager.get(new Long(id));
		for (Iterator it = baseOp.getFileItems().iterator(); it.hasNext();) {
			BaseFileItem fileItem = (BaseFileItem) it.next();
			String url = fileItem.getFileurl();
			String fileurl = url.replaceAll("/", "\\\\");
			String fileName = SpringContext.getRealPath() + fileurl;

			String[] tmpFileName = fileurl.split("\\\\");
			String name = tmpFileName[tmpFileName.length - 1];

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("GBK"), "ISO-8859-1"));
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setDateHeader("Expires", (System.currentTimeMillis() + 100000));

			OutputStream os = null;
			InputStream is = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			try {
				os = response.getOutputStream();
				is = new BufferedInputStream(new FileInputStream(fileName));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					os.write(buf, 0, readLen);

				}
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		//else
		return null;
	}

}
