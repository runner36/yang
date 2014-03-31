package com.winchannel.task.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winchannel.base.utils.DateUtility;
import com.winchannel.mdm.product.service.MdmProductCache;
import com.winchannel.task.service.DataBaseTaskManager;

public class DataBaseJob {

	public static final Log	log	= LogFactory.getLog("DataBaseJob");

	private DataBaseTaskManager	dataBaseTaskManager;

	public void setDataBaseTaskManager(DataBaseTaskManager dataBaseTaskManager) {
		this.dataBaseTaskManager = dataBaseTaskManager;
	}

	public void execute() {
		String today = DateUtility.getCurrentDateTime();
		log.info("DataBaseJob   begin ... " + today);
		try {
			
			/*dataBaseTaskManager.init();
 			dataBaseTaskManager.transferProducData();
 			dataBaseTaskManager.transferDistData();
			dataBaseTaskManager.transferDistAddData();*/
			
			dataBaseTaskManager.initMdmProductCache();
//			dataBaseTaskManager.transferSapOrderAll();
			 
		} catch (Exception e) {
			log.error(e);
			dataBaseTaskManager.saveDblog("all err  ", e.getMessage());
		} finally {

		}
		log.info("DataBaseJob   end ... " + today);
	}
	
	public void executeTransSap() {
		String today = DateUtility.getCurrentDateTime();
		log.info("transferSapOrderJob   begin ... " + today);
		try {
			dataBaseTaskManager.transferSapOrderAll();			 
		} catch (Exception e) {
			log.error(e);
			dataBaseTaskManager.saveDblog("all err", e.getMessage());
		} finally {

		}
		log.info("transferSapOrderJob   end ... " + today);
	}
	

}
