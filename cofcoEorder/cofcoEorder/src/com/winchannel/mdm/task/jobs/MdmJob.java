package com.winchannel.mdm.task.jobs;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winchannel.mdm.task.service.MdmTaskManager;

public class MdmJob {

	public static final Log log = LogFactory.getLog("MdmJob");

	private MdmTaskManager mdmTaskManager;

	public void setMdmTaskManager(MdmTaskManager mdmTaskManager) {
		this.mdmTaskManager = mdmTaskManager;
	}

	public void execute() {
		String today = com.winchannel.core.utils.DateUtils.format(new Date(), "yyyy-MM-dd");
		log.info("MdmJob for org setting begin ... " + today);
		try {
			mdmTaskManager.saveDistOrg(today);
			mdmTaskManager.saveMdmStoreOrg(today);
			mdmTaskManager.saveEmpOrg(today);
			
			mdmTaskManager.processDistOrgLogData(today);
//			mdmTaskManager.processEmpOrgLogData(today);
//			mdmTaskManager.processStoreOrgLogData(today);
		} catch (Exception e) {
			log.error(e);
		} finally {

		}
		log.info("MdmJob for org setting end ... " + today);
	}

}
