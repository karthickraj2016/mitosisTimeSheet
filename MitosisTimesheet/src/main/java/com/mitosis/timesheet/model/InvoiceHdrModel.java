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

import com.mitosis.timesheet.webservice.CustomerDetails;



@XmlRootElement
@Entity
@Table(name = "invoice_hdr")
public class InvoiceHdrModel {
	
	@Id
	@Column(name="invoice_number")
	private String invoiceNumber;

	@ManyToOne(targetEntity = ProjectModel.class)
	@JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
	private ProjectModel project;

	@Column(name="invoice_date")
	private Date invoiceDate;
	
	@Column(name="invoice_amount")
	private BigDecimal invoiceAmount;
	
	
	@Column(name="paid_amount")
	private BigDecimal paidAmount;
	
	@Column(name="balance_amount")
	private BigDecimal balanceAmount;
	
	@Column(name="currency_code")
	private String currencyCode;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@ManyToOne(targetEntity = CustomerDetailsModel.class)
	@JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
	private CustomerDetailsModel customer;

	@Column(name="project_type")
	private String projectType;
	
	@Transient
	private String frmEntryDate;


	public String getFrmEntryDate() {
		Date d= getInvoiceDate();
		frmEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return frmEntryDate;
	}
	public void setFrmEntryDate(String frmEntryDate) {
		this.frmEntryDate = frmEntryDate;
	}
	
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public CustomerDetailsModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDetailsModel customer) {
		this.customer = customer;
	}
	

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
}
