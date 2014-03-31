package com.winchannel.mdm.store.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * MdmDistributor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreOrgCheck {

	// Fields
	private String flag;
	private String storeId;
	private String storeName;
	private String storeCode;
	private String initOrgId;
	private String initOrgName;
	private String sugOrgId;
	private String sugOrgName;
	private String geoId;
	private String geoFullName;
	private String lastLogOrgName;
	private String startDate;
	private String updateDate;

	private List<StoreOrgCheck> sugOrgList = new ArrayList<StoreOrgCheck>();

	public StoreOrgCheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getInitOrgId() {
		return initOrgId;
	}

	public void setInitOrgId(String initOrgId) {
		this.initOrgId = initOrgId;
	}

	public String getInitOrgName() {
		return initOrgName;
	}

	public void setInitOrgName(String initOrgName) {
		this.initOrgName = initOrgName;
	}

	public String getSugOrgId() {
		return sugOrgId;
	}

	public void setSugOrgId(String sugOrgId) {
		this.sugOrgId = sugOrgId;
	}

	public String getSugOrgName() {
		return sugOrgName;
	}

	public void setSugOrgName(String sugOrgName) {
		this.sugOrgName = sugOrgName;
	}

	public String getGeoId() {
		return geoId;
	}

	public void setGeoId(String geoId) {
		this.geoId = geoId;
	}

	public String getGeoFullName() {
		return geoFullName;
	}

	public void setGeoFullName(String geoFullName) {
		this.geoFullName = geoFullName;
	}

	public List<StoreOrgCheck> getSugOrgList() {
		return sugOrgList;
	}

	public void setSugOrgList(List<StoreOrgCheck> sugOrgList) {
		this.sugOrgList = sugOrgList;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getLastLogOrgName() {
		return lastLogOrgName;
	}

	public void setLastLogOrgName(String lastLogOrgName) {
		this.lastLogOrgName = lastLogOrgName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}