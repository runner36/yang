package com.winchannel.base.model;

import java.util.Date;

/**
 * BaseEmployeeLog generated by MyEclipse Persistence Tools
 */

public class BaseEmployeeLog implements java.io.Serializable {

	// Fields

	private Long id;

	private Long empId;

	private BaseOrg baseOrg;

	private BaseEmployee baseEmployee;

	private BaseDictItem baseDictItem;

	private BaseDictItem baseDictItemByEmpTypeId;

	private BaseDept baseDept;

	private BaseDictItem baseDictItemGeo;

	private String empCode;

	private String extCode;

	private String subCode;

	private Long levelCode;

	private String empName;

	private String empNameEn;

	private String sex;

	private String idCard;

	private String nation;

	private Date birthday;

	private String homeplace;

	private String empAddr;

	private String educate;

	private String school;

	private String subject;

	private Date employment;

	private Date dimission;

	private String duty;

	private String officePhone;

	private String homePhone;

	private String mobilePhone;

	private String isEmployee;

	private String remark;

	private String state;

	private Long sort;

	private String createdBy;

	private Date created;

	private String updatedBy;

	private Date updated;

	private String memo1;

	private String memo2;

	private String memo3;

	private String memo4;

	private String memo5;

	private Long pi1;

	private Long pi2;

	private Long pi3;

	private Long pi4;

	private Long pi5;

	private Long pi6;

	private Long pi7;

	private Long pi8;

	private String pn1;

	private String pn2;

	private String pn3;

	private String pn4;

	private String pn5;

	private String pn6;

	private String pn7;

	private String pn8;

	private String pnall;

	private Date startDate;

	private Date endDate;

	// Constructors

	/** default constructor */
	public BaseEmployeeLog() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpId() {
		return this.empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	

	public BaseOrg getBaseOrg() {
		return baseOrg;
	}

	public void setBaseOrg(BaseOrg baseOrg) {
		this.baseOrg = baseOrg;
	}

	public BaseEmployee getBaseEmployee() {
		return baseEmployee;
	}

	public void setBaseEmployee(BaseEmployee baseEmployee) {
		this.baseEmployee = baseEmployee;
	}

	public BaseDictItem getBaseDictItem() {
		return baseDictItem;
	}

	public void setBaseDictItem(BaseDictItem baseDictItem) {
		this.baseDictItem = baseDictItem;
	}

	public BaseDictItem getBaseDictItemByEmpTypeId() {
		return baseDictItemByEmpTypeId;
	}

	public void setBaseDictItemByEmpTypeId(BaseDictItem baseDictItemByEmpTypeId) {
		this.baseDictItemByEmpTypeId = baseDictItemByEmpTypeId;
	}

	public BaseDept getBaseDept() {
		return baseDept;
	}

	public void setBaseDept(BaseDept baseDept) {
		this.baseDept = baseDept;
	}

	public BaseDictItem getBaseDictItemGeo() {
		return baseDictItemGeo;
	}

	public void setBaseDictItemGeo(BaseDictItem baseDictItemGeo) {
		this.baseDictItemGeo = baseDictItemGeo;
	}

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getExtCode() {
		return this.extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
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

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpNameEn() {
		return this.empNameEn;
	}

	public void setEmpNameEn(String empNameEn) {
		this.empNameEn = empNameEn;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHomeplace() {
		return this.homeplace;
	}

	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}

	public String getEmpAddr() {
		return this.empAddr;
	}

	public void setEmpAddr(String empAddr) {
		this.empAddr = empAddr;
	}

	public String getEducate() {
		return this.educate;
	}

	public void setEducate(String educate) {
		this.educate = educate;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getEmployment() {
		return this.employment;
	}

	public void setEmployment(Date employment) {
		this.employment = employment;
	}

	public Date getDimission() {
		return this.dimission;
	}

	public void setDimission(Date dimission) {
		this.dimission = dimission;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getIsEmployee() {
		return this.isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
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

	public Long getPi1() {
		return this.pi1;
	}

	public void setPi1(Long pi1) {
		this.pi1 = pi1;
	}

	public Long getPi2() {
		return this.pi2;
	}

	public void setPi2(Long pi2) {
		this.pi2 = pi2;
	}

	public Long getPi3() {
		return this.pi3;
	}

	public void setPi3(Long pi3) {
		this.pi3 = pi3;
	}

	public Long getPi4() {
		return this.pi4;
	}

	public void setPi4(Long pi4) {
		this.pi4 = pi4;
	}

	public Long getPi5() {
		return this.pi5;
	}

	public void setPi5(Long pi5) {
		this.pi5 = pi5;
	}

	public Long getPi6() {
		return this.pi6;
	}

	public void setPi6(Long pi6) {
		this.pi6 = pi6;
	}

	public Long getPi7() {
		return this.pi7;
	}

	public void setPi7(Long pi7) {
		this.pi7 = pi7;
	}

	public Long getPi8() {
		return this.pi8;
	}

	public void setPi8(Long pi8) {
		this.pi8 = pi8;
	}

	public String getPn1() {
		return this.pn1;
	}

	public void setPn1(String pn1) {
		this.pn1 = pn1;
	}

	public String getPn2() {
		return this.pn2;
	}

	public void setPn2(String pn2) {
		this.pn2 = pn2;
	}

	public String getPn3() {
		return this.pn3;
	}

	public void setPn3(String pn3) {
		this.pn3 = pn3;
	}

	public String getPn4() {
		return this.pn4;
	}

	public void setPn4(String pn4) {
		this.pn4 = pn4;
	}

	public String getPn5() {
		return this.pn5;
	}

	public void setPn5(String pn5) {
		this.pn5 = pn5;
	}

	public String getPn6() {
		return this.pn6;
	}

	public void setPn6(String pn6) {
		this.pn6 = pn6;
	}

	public String getPn7() {
		return this.pn7;
	}

	public void setPn7(String pn7) {
		this.pn7 = pn7;
	}

	public String getPn8() {
		return this.pn8;
	}

	public void setPn8(String pn8) {
		this.pn8 = pn8;
	}

	public String getPnall() {
		return this.pnall;
	}

	public void setPnall(String pnall) {
		this.pnall = pnall;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}