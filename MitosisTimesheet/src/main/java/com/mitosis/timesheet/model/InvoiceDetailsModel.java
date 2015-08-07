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
@Table(name = "invoice_details")
public class InvoiceDetailsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne(targetEntity = InvoiceHdrModel.class)
	@JoinColumn(name = "invoice_Number", nullable = false, unique=true ,referencedColumnName = "invoice_Number")
	private InvoiceHdrModel invoice;


	@Column(name="team_member")
	private String teamMember;
	
	@Column(name="description")
	private String description;
	
	@Column(name="invoice_from_date")
	private Date invoiceFromDate;
	
	@Column(name="invoice_to_date")
	private Date invoiceToDate;
	
	@Column(name="rate_per_hour")
	private BigDecimal ratePerHour;
	
	@Column(name="billable_hours")
	private Integer billableHours;
	
	@Column(name="total_amount")
	private BigDecimal totalAmount;
	
	@Transient
	private String frmEntryDate;
	
	@Transient
	private String toEntryDate;

	public String getToEntryDate() {
		Date d= getInvoiceToDate();
		toEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return toEntryDate;
	}
	public void setToEntryDate(String toEntryDate) {
		this.toEntryDate = toEntryDate;
	}
	public String getFrmEntryDate() {
		Date d= getInvoiceFromDate();
		frmEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return frmEntryDate;
	}
	public void setFrmEntryDate(String frmEntryDate) {
		this.frmEntryDate = frmEntryDate;
	}
	
	
	public InvoiceHdrModel getInvoice() {
		return invoice;
	}


	public void setInvoice(InvoiceHdrModel invoice) {
		this.invoice = invoice;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTeamMember() {
		return teamMember;
	}


	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getInvoiceFromDate() {
		return invoiceFromDate;
	}


	public void setInvoiceFromDate(Date invoiceFromDate) {
		this.invoiceFromDate = invoiceFromDate;
	}


	public Date getInvoiceToDate() {
		return invoiceToDate;
	}


	public void setInvoiceToDate(Date invoiceToDate) {
		this.invoiceToDate = invoiceToDate;
	}


	public BigDecimal getRatePerHour() {
		return ratePerHour;
	}


	public void setRatePerHour(BigDecimal ratePerHour) {
		this.ratePerHour = ratePerHour;
	}


	public Integer getBillableHours() {
		return billableHours;
	}


	public void setBillableHours(Integer billableHours) {
		this.billableHours = billableHours;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


}
