package com.winchannel.mdm.calendar.model;

import java.util.Date;
import org.extremecomponents.table.tag.TagUtils;
/**
 * MdmCalendar entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MdmCalendar implements java.io.Serializable {

	// Fields

	private Long calId;
	private Long calYear;
	private Long calQuarter;
	private Long calMonth;
	private Long calWeek;
	private Long calWeekOfMonth;
	private Long calDay;
	private Date calDate;

	// Constructors

	/** default constructor */
	public MdmCalendar() {
	}

	/** minimal constructor */
	public MdmCalendar(Long calId) {
		this.calId = calId;
	}

	/** full constructor */
	public MdmCalendar(Long calId, Long calYear, Long calQuarter,
			Long calMonth, Long calWeek, Long calWeekOfMonth, Long calDay,
			Date calDate) {
		this.calId = calId;
		this.calYear = calYear;
		this.calQuarter = calQuarter;
		this.calMonth = calMonth;
		this.calWeek = calWeek;
		this.calWeekOfMonth = calWeekOfMonth;
		this.calDay = calDay;
		this.calDate = calDate;
	}

	// Property accessors

	public Long getCalId() {
		return this.calId;
	}

	public void setCalId(Long calId) {
		this.calId = calId;
	}

	public Long getCalYear() {
		return this.calYear;
	}

	public void setCalYear(Long calYear) {
		this.calYear = calYear;
	}

	public Long getCalQuarter() {
		return this.calQuarter;
	}

	public void setCalQuarter(Long calQuarter) {
		this.calQuarter = calQuarter;
	}

	public Long getCalMonth() {
		return this.calMonth;
	}

	public void setCalMonth(Long calMonth) {
		this.calMonth = calMonth;
	}

	public Long getCalWeek() {
		return this.calWeek;
	}

	public void setCalWeek(Long calWeek) {
		this.calWeek = calWeek;
	}

	public Long getCalWeekOfMonth() {
		return this.calWeekOfMonth;
	}

	public void setCalWeekOfMonth(Long calWeekOfMonth) {
		this.calWeekOfMonth = calWeekOfMonth;
	}

	public Long getCalDay() {
		return this.calDay;
	}

	public void setCalDay(Long calDay) {
		this.calDay = calDay;
	}

	public Date getCalDate() {
		return this.calDate;
	}

	public void setCalDate(Date calDate) {
		this.calDate = calDate;
	}

}