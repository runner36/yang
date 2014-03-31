package com.winchannel.base.service;

import java.util.Date;
import java.util.List;

import com.winchannel.base.model.BaseJob;
import com.winchannel.base.model.BaseJobLog;
import com.winchannel.core.dao.HibernateEntityDao;
import com.winchannel.core.utils.SpringContext;

public class BaseJobLogManager extends HibernateEntityDao<BaseJobLog> {

	public List<BaseJob> getJobs() {
		return this.findEntity(BaseJob.class,
				"from BaseJob where state='1' order by sort");
	}

	/**
	 * @param jobCode
	 *            任务编码
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param isComplete
	 *            是否执行成功
	 * @param execResult
	 *            执行的结果
	 * @param errorInfo
	 *            错误信息
	 * @param remark
	 *            备注
	 * @param accessory
	 *            附件的文件名称，存储在/joblogs下.
	 */
	public static void createLog(String jobCode, Date startTime, Date endTime,
			boolean isComplete, String execResult, String errorInfo,
			String remark, String accessory) {
		BaseJobLogManager manager = (BaseJobLogManager) SpringContext
				.getBean("baseJobLogManager");
		BaseJobLog jobLog = new BaseJobLog();
		jobLog.setBaseJob(jobCode);
		// jobLog.setBaseJob(manager.get(BaseJob.class, jobCode));
		jobLog.setStartTime(startTime);
		jobLog.setEndTime(endTime);
		jobLog.setIsComplete(isComplete ? "1" : "0");
		jobLog.setExecResult(execResult);
		jobLog.setErrorInfo(errorInfo);
		jobLog.setRemark(remark);
		jobLog.setAccessory(accessory);
		createLog(jobLog);
	}

	public static void createLog(BaseJobLog jobLog) {
		BaseJobLogManager manager = (BaseJobLogManager) SpringContext
				.getBean("baseJobLogManager");
		jobLog.setUpdated(new Date());
		jobLog.setUpdatedBy("system");
		manager.save(jobLog);
	}

}
