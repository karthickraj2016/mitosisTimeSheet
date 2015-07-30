package com.mitosis.timesheet.pojo;

import java.util.List;

import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public class InvoiceDetailsReport {
	
	public InvoiceHdrModel invoiceHdr;
	
	
	public List<InvoiceDetailsModel> invoiceDetailsModel;
	
	public CompanyInfoModel companyInfo;
	

	public CompanyInfoModel getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfoModel companyInfo) {
		this.companyInfo = companyInfo;
	}


	
	public List<InvoiceDetailsModel> getInvoiceDetailsModel() {
		return invoiceDetailsModel;
	}

	public void setInvoiceDetailsModel(List<InvoiceDetailsModel> invoiceDetailsModel) {
		this.invoiceDetailsModel = invoiceDetailsModel;
	}



	public InvoiceHdrModel getInvoiceHdr() {
		return invoiceHdr;
	}

	public void setInvoiceHdr(InvoiceHdrModel invoiceHdr) {
		this.invoiceHdr = invoiceHdr;
	}



}
