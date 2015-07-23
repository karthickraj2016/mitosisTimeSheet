package com.mitosis.timesheet.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@Table(name = "level_master")
public class LevelMasterModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="year_from")
	private int yearFrom;
	
	@Column(name="year_to")
	private int yearTo;
	
	@Column(name="level")
	private int level;
	
	@Column(name="rate_per_hour")
	private BigDecimal ratePerHour;
	
	@Column(name="hours_per_month")
	private int hoursPerMonth;
	
	@Column(name="total_employees")
	private int totalEmployees;
	
	@Column(name="total_amount")
	private BigDecimal totalAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}

	public int getYearTo() {
		return yearTo;
	}

	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BigDecimal getRatePerHour() {
		return ratePerHour;
	}

	public void setRatePerHour(BigDecimal ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	public int getHoursPerMonth() {
		return hoursPerMonth;
	}

	public void setHoursPerMonth(int hoursPerMonth) {
		this.hoursPerMonth = hoursPerMonth;
	}

	public int getTotalEmployees() {
		return totalEmployees;
	}

	public void setTotalEmployees(int totalEmployees) {
		this.totalEmployees = totalEmployees;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
