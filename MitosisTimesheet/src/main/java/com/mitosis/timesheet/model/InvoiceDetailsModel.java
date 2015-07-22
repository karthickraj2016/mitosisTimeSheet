package com.mitosis.timesheet.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
	private int billableHours;
	
	@Column(name="total_amount")
	private BigDecimal totalAmount;
	
	
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


	public int getBillableHours() {
		return billableHours;
	}


	public void setBillableHours(int billableHours) {
		this.billableHours = billableHours;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


}
