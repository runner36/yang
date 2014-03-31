package com.winchannel.message.model;

/**
 * FileItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseFileItem implements java.io.Serializable {

	// Fields

	private Long fileid;
	private BaseMessages baseMessage;
	private BaseOpinions baseOpinion;
	private String fileurl;
	private Long flag;

	// Constructors

	/** default constructor */
	public BaseFileItem() {
	}

	/** minimal constructor */
	public BaseFileItem(BaseMessages baseMessage) {
		this.baseMessage = baseMessage;
	}

	/** full constructor */
	public BaseFileItem(BaseMessages baseMessage, BaseOpinions baseOpinion,
			String fileurl, Long flag) {
		this.baseMessage = baseMessage;
		this.baseOpinion = baseOpinion;
		this.fileurl = fileurl;
		this.flag = flag;
	}

	// Property accessors

	public Long getFileid() {
		return this.fileid;
	}

	public void setFileid(Long fileid) {
		this.fileid = fileid;
	}

	public BaseMessages getBaseMessage() {
		return this.baseMessage;
	}

	public void setBaseMessage(BaseMessages baseMessage) {
		this.baseMessage = baseMessage;
	}

	public BaseOpinions getBaseOpinion() {
		return this.baseOpinion;
	}

	public void setBaseOpinion(BaseOpinions baseOpinion) {
		this.baseOpinion = baseOpinion;
	}

	public String getFileurl() {
		return this.fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

}