package com.mitosis.timesheet.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
@Entity
@Table(name = "project_cost_details")
public class ProjectCostDetailsModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne(targetEntity = ProjectCostHdrModel.class)
	@JoinColumn(name = "project_cost_hdr_id", nullable = false, referencedColumnName = "id")
	private ProjectCostHdrModel projectCostHdr;

	@ManyToOne(targetEntity = CustomerDetailsModel.class)
	@JoinColumn(name = "employee_id", nullable = false, referencedColumnName = "id")
	private CustomerDetailsModel employee;
	

	@Column(name="rate")
	private BigDecimal rate;
	
	@Column(name="currency_code")
	private String currencyCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProjectCostHdrModel getProjectCostHdr() {
		return projectCostHdr;
	}

	public void setProjectCostHdr(ProjectCostHdrModel projectCostHdr) {
		this.projectCostHdr = projectCostHdr;
	}

	public CustomerDetailsModel getEmployee() {
		return employee;
	}

	public void setEmployee(CustomerDetailsModel employee) {
		this.employee = employee;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	


	

}


