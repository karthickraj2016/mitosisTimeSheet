package com.mitosis.timesheet.service;

import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public interface InvoiceDetailsService {

	public boolean validateEntry(InvoiceHdrModel invoiceHdrModel);

	public boolean create(InvoiceHdrModel invoiceHdrModel);

	public boolean create(InvoiceDetailsModel invoiceDetailsModel);

	public String getInvoiceNumber();

}
