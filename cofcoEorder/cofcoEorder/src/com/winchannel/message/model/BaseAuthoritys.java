package com.winchannel.message.model;

/**
 * BaseAuthority entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BaseAuthoritys implements java.io.Serializable {

	// Fields

	private Long id;
	private BaseMessages baseMessage;
	private BaseOpinions baseOpinion;
	private Long orgId;
	private Long flag;

	// Constructors

	/** default constructor */
	public BaseAuthoritys() {
	}

	/** full constructor */
	public BaseAuthoritys(BaseMessages baseMessage, BaseOpinions baseOpinion,
			Long orgId, Long flag) {
		this.baseMessage = baseMessage;
		this.baseOpinion = baseOpinion;
		this.orgId = orgId;
		this.flag = flag;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getFlag() {
		return this.flag;
	}

	public void setFlag(Long i) {
		this.flag = i;
	}

}