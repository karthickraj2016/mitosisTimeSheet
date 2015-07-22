package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectModel;

public interface InvoiceDetailsService {

	public boolean validateEntry(InvoiceHdrModel invoiceHdrModel);

	public boolean create(InvoiceHdrModel invoiceHdrModel);

	public boolean create(InvoiceDetailsModel invoiceDetailsModel);

	public String getInvoiceNumber();

	public int getId(String invoiceNumber);

	public List<ProjectModel> getProjectList();

	public List<CustomerDetailsModel> getCustomerList();

}
