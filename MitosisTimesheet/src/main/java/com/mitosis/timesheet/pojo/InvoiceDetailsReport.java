package com.mitosis.timesheet.pojo;

import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public class InvoiceDetailsReport {
	
	public InvoiceHdrModel invoiceHdr;
	
	
	public InvoiceDetailsModel invoiceDetailsModel;
	
	public InvoiceDetailsModel getInvoiceDetailsModel() {
		return invoiceDetailsModel;
	}

	public void setInvoiceDetailsModel(InvoiceDetailsModel invoiceDetailsModel) {
		this.invoiceDetailsModel = invoiceDetailsModel;
	}

	public InvoiceHdrModel getInvoiceHdr() {
		return invoiceHdr;
	}

	public void setInvoiceHdr(InvoiceHdrModel invoiceHdr) {
		this.invoiceHdr = invoiceHdr;
	}



}
