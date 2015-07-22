package com.mitosis.timesheet.dao;

import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public interface InvoiceDetailsDao {

	boolean validateEntry(InvoiceHdrModel invoiceHdrModel);

	boolean create(InvoiceHdrModel invoiceHdrModel);

	boolean create(InvoiceDetailsModel invoiceDetailsModel);

	public String getInvoiceNumber();

}
