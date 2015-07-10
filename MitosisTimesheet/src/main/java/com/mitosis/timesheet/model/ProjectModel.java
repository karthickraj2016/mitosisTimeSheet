package com.mitosis.timesheet.model;

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
@Table(name = "project_details")
public class ProjectModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int projectId;

	@Column(name="project_name")
	private String projectName;

	@ManyToOne(targetEntity = CustomerDetailsModel.class)
	@JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
	private CustomerDetailsModel customer;


	@Column(name="billable")
	private String billable;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="status")
	private String status;
	
	@Transient
	private String startEntryDate;
	
	@Transient
	private String endEntryDate;

	public String getStartEntryDate() {
		Date d= getStartDate();
		startEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return startEntryDate;
	}
	public void setStartEntryDate(String startEntryDate) {
		this.startEntryDate = startEntryDate;
	}
		
	public String getEndEntryDate() {
		Date d= getEndDate();
		endEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return endEntryDate;
	}
	public void setEndEntryDate(String endEntryDate) {
		this.endEntryDate = endEntryDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public CustomerDetailsModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDetailsModel customer) {
		this.customer = customer;
	}

	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}


