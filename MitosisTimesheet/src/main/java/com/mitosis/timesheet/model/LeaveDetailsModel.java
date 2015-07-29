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
@Table(name = "leave_details")
public class LeaveDetailsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(targetEntity = UserDetailsModel.class)
	@JoinColumn(name = "employee_id", nullable = false, referencedColumnName = "id")
	private UserDetailsModel employee;
	
	@Column(name="from_date")
	private Date fromDate;
	
	@Column(name="to_date")
	private Date toDate;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="status")
	private String status;
	
	@Column(name="no_of_days")
	private Integer noOfDays;
	
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	@Transient
	private String frmEntryDate;
	
	@Transient
	private String toEntryDate;

	public String getFrmEntryDate() {
		Date d= getFromDate();
		frmEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return frmEntryDate;
	}
	public void setFrmEntryDate(String frmEntryDate) {
		this.frmEntryDate = frmEntryDate;
	}
		
	public String getToEntryDate() {
		Date d= getToDate();
		toEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return toEntryDate;
	}
	public void setToEntryDate(String toEntryDate) {
		this.toEntryDate = toEntryDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDetailsModel getEmployee() {
		return employee;
	}

	public void setEmployee(UserDetailsModel employee) {
		this.employee = employee;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
