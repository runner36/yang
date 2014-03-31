package com.winchannel.mdm.distributor.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



import com.winchannel.base.model.BaseDictItem;
import com.winchannel.base.model.BaseEmployee;
import com.winchannel.base.model.BaseOrg;
import com.winchannel.mdm.distributor.bean.DistOrgCheck;

/**
 * MdmDistributor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmDistributor implements java.io.Serializable {

	// Fields

	private Long distId;
	private BaseDictItem invoiceType;
	private BaseDictItem baseDictItem;
	private BaseOrg baseOrg;
	private MdmDistributor mdmDistributor;
	private String distName;
	private String distCode;
	private String subCode;
	private Long levelCode;
	private String distAddr;
	private String distTel;
	private String distPost;
	private String mgrName;
	private String mgrTel;
	private String linkmanName;
	private String linkmanTel;
	private Date instDate;
	private Date checkDate;
	private Date mappingDate;
	private String memo1;
	private String memo2;
	private String memo3;
	private String memo4;
	private String memo5;
	private String memo6;
	private String remark;
	private String state;
	private Long sort;
	private String createdBy;
	private Date created;
	private String updatedBy;
	private Date updated;
	private Date passBackDate;
	private Date passDataDate;
	private Date endPassDate;

	private String ownergrpCode;

	private String ownergrpName;

	private Set mdmStores = new HashSet(0);
	private Set mdmDistributors = new HashSet(0);
	private Set mdmDistEmpProdgroups = new HashSet(0);
	private Set mdmDistributorAddresses = new HashSet(0);
//	private Set mdmDistOrderempProdgroups = new HashSet(0);
	private BaseEmployee baseEmployee;
//	private Set orderInfos = new HashSet(0);

	private DistOrgCheck distOrgCheck;

	// Constructors

	/** default constructor */
	public MdmDistributor() {
	}

	/** minimal constructor */
	public MdmDistributor(Long distId) {
		this.distId = distId;
	}

	/** full constructor */
	public MdmDistributor(BaseOrg baseOrg, MdmDistributor mdmDistributor,
			BaseDictItem baseDictItem, String distName, String distCode,
			String subCode, Long levelCode, String distAddr, String distTel,
			String distPost, String mgrName, String mgrTel, String linkmanName,
			String linkmanTel, Date instDate, Date checkDate, Date mappingDate,
			String memo1, String memo2, String memo3, String memo4,
			String memo5, String memo6, String remark, String state, Long sort,
			String createdBy, Date created, String updatedBy, Date updated,
			Date passBackDate, Date passDataDate, Date endPassDate,
			String ownergrpCode, String ownergrpName, Set mdmDistEmpProdgroups,
			Set mdmDistributors, Set mdmDistributorAddresses,
			Set mdmDistOrderempProdgroups, Set orderInfos) {
		this.baseOrg = baseOrg;
		this.mdmDistributor = mdmDistributor;
		this.baseDictItem = baseDictItem;
		this.distName = distName;
		this.distCode = distCode;
		this.subCode = subCode;
		this.levelCode = levelCode;
		this.distAddr = distAddr;
		this.distTel = distTel;
		this.distPost = distPost;
		this.mgrName = mgrName;
		this.mgrTel = mgrTel;
		this.linkmanName = linkmanName;
		this.linkmanTel = linkmanTel;
		this.instDate = instDate;
		this.checkDate = checkDate;
		this.mappingDate = mappingDate;
		this.memo1 = memo1;
		this.memo2 = memo2;
		this.memo3 = memo3;
		this.memo4 = memo4;
		this.memo5 = memo5;
		this.memo6 = memo6;
		this.remark = remark;
		this.state = state;
		this.sort = sort;
		this.createdBy = createdBy;
		this.created = created;
		this.updatedBy = updatedBy;
		this.updated = updated;
		this.passBackDate = passBackDate;
		this.passDataDate = passDataDate;
		this.endPassDate = endPassDate;
		this.ownergrpCode = ownergrpCode;
		this.ownergrpName = ownergrpName;
		this.mdmDistEmpProdgroups = mdmDistEmpProdgroups;
		this.mdmDistributors = mdmDistributors;
		this.mdmDistributorAddresses = mdmDistributorAddresses;
//		this.orderInfos = orderInfos;
	}

	// Property accessors

	public Long getDistId() {
		return this.distId;
	}

	public void setDistId(Long distId) {
		this.distId = distId;
	}

	public BaseDictItem getBaseDictItem() {
		return this.baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

	public BaseOrg getBaseOrg() {
		return this.baseOrg;
	}

	public void setBaseOrg(BaseOrg baseOrg) {
		this.baseOrg = baseOrg;
	}

	public MdmDistributor getMdmDistributor() {
		return this.mdmDistributor;
	}

	public void setMdmDistributor(MdmDistributor mdmDistributor) {
		this.mdmDistributor = mdmDistributor;
	}

	public BaseDictItem getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(BaseDictItem invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BaseEmployee getBaseEmployee() {
		return baseEmployee;
	}

	public void setBaseEmployee(BaseEmployee baseEmployee) {
		this.baseEmployee = baseEmployee;
	}

	public String getDistName() {
		return this.distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public String getDistCode() {
		return this.distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getSubCode() {
		return this.subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public Long getLevelCode() {
		return this.levelCode;
	}

	public void setLevelCode(Long levelCode) {
		this.levelCode = levelCode;
	}

	public String getDistAddr() {
		return this.distAddr;
	}

	public void setDistAddr(String distAddr) {
		this.distAddr = distAddr;
	}

	public String getDistTel() {
		return this.distTel;
	}

	public void setDistTel(String distTel) {
		this.distTel = distTel;
	}

	public String getDistPost() {
		return this.distPost;
	}

	public void setDistPost(String distPost) {
		this.distPost = distPost;
	}

	public String getMgrName() {
		return this.mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	public String getMgrTel() {
		return this.mgrTel;
	}

	public void setMgrTel(String mgrTel) {
		this.mgrTel = mgrTel;
	}

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public Date getInstDate() {
		return this.instDate;
	}

	public void setInstDate(Date instDate) {
		this.instDate = instDate;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getMappingDate() {
		return this.mappingDate;
	}

	public void setMappingDate(Date mappingDate) {
		this.mappingDate = mappingDate;
	}

	public String getMemo1() {
		return this.memo1;
	}

	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getMemo2() {
		return this.memo2;
	}

	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	public String getMemo3() {
		return this.memo3;
	}

	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	public String getMemo4() {
		return this.memo4;
	}

	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}

	public String getMemo5() {
		return this.memo5;
	}

	public void setMemo5(String memo5) {
		this.memo5 = memo5;
	}

	public String getMemo6() {
		return this.memo6;
	}

	public void setMemo6(String memo6) {
		this.memo6 = memo6;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Set getMdmStores() {
		return this.mdmStores;
	}

	public void setMdmStores(Set mdmStores) {
		this.mdmStores = mdmStores;
	}

	public Set getMdmDistributors() {
		return this.mdmDistributors;
	}

	public void setMdmDistributors(Set mdmDistributors) {
		this.mdmDistributors = mdmDistributors;
	}

	public Date getPassBackDate() {
		return passBackDate;
	}

	public void setPassBackDate(Date passBackDate) {
		this.passBackDate = passBackDate;
	}

	public Date getPassDataDate() {
		return passDataDate;
	}

	public void setPassDataDate(Date passDataDate) {
		this.passDataDate = passDataDate;
	}

	public Date getEndPassDate() {
		return endPassDate;
	}

	public void setEndPassDate(Date endPassDate) {
		this.endPassDate = endPassDate;
	}

	public DistOrgCheck getDistOrgCheck() {
		return distOrgCheck;
	}

	public void setDistOrgCheck(DistOrgCheck distOrgCheck) {
		this.distOrgCheck = distOrgCheck;
	}

	public String getOwnergrpCode() {
		return ownergrpCode;
	}

	public void setOwnergrpCode(String ownergrpCode) {
		this.ownergrpCode = ownergrpCode;
	}

	public String getOwnergrpName() {
		return ownergrpName;
	}

	public void setOwnergrpName(String ownergrpName) {
		this.ownergrpName = ownergrpName;
	}

	public Set getMdmDistEmpProdgroups() {
		return mdmDistEmpProdgroups;
	}

	public void setMdmDistEmpProdgroups(Set mdmDistEmpProdgroups) {
		this.mdmDistEmpProdgroups = mdmDistEmpProdgroups;
	}

	public Set getMdmDistributorAddresses() {
		return mdmDistributorAddresses;
	}

	public void setMdmDistributorAddresses(Set mdmDistributorAddresses) {
		this.mdmDistributorAddresses = mdmDistributorAddresses;
	}


//	public Set getOrderInfos() {
//		return orderInfos;
//	}
//
//	public void setOrderInfos(Set orderInfos) {
//		this.orderInfos = orderInfos;
//	}

}