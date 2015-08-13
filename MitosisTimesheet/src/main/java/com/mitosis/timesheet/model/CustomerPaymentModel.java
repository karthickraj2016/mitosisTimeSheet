package com.mitosis.timesheet.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "customer_payment")
public class CustomerPaymentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@ManyToOne(targetEntity = InvoiceHdrModel.class,  cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_number", nullable = false,insertable=true, updatable=true, referencedColumnName = "invoice_number")
	private InvoiceHdrModel invoiceHdr;
	
	@Column(name="receipt_date")
	private Date receiptDate;
	
	@Column(name="receipt_number")
	private String receiptNumber;
	
	@Column(name="paid_amount")
	private BigDecimal paidAmount;
	
	@Column(name="currency_code")
	private String currencyCode;
	
	@Column(name="exchange_rate")
	private BigDecimal exchangeRate;
	
	@Column(name="paid_amount_inr")
	private BigDecimal paidAmountInr;
	
	@Column(name="commision_amount")
	private BigDecimal commisionAmount;
	
	@Column(name="received_amount")
	private BigDecimal receivedAmount;
	
	@Transient
	private String receiptDateStr;
			
	public String getReceiptDateStr() {
		Date d=getReceiptDate();
		return new SimpleDateFormat("dd-MM-yyyy").format(d);		
	}
	public void setReceiptDateStr(String receiptDateStr) {
		this.receiptDateStr = receiptDateStr;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InvoiceHdrModel getInvoiceHdr() {
		return invoiceHdr;
	}

	public void setInvoiceHdr(InvoiceHdrModel invoiceHdr) {
		this.invoiceHdr = invoiceHdr;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getPaidAmountInr() {
		return paidAmountInr;
	}

	public void setPaidAmountInr(BigDecimal paidAmountInr) {
		this.paidAmountInr = paidAmountInr;
	}

	public BigDecimal getCommisionAmount() {
		return commisionAmount;
	}

	public void setCommisionAmount(BigDecimal commisionAmount) {
		this.commisionAmount = commisionAmount;
	}

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	
}
