package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public interface BankReconcileDao {

	public List<CustomerPaymentModel> getReceiptDetails();

	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber);

	public InvoiceHdrModel getInvoiceHdrDetails(String invoiceNum);
	
	public boolean insert(CustomerPaymentModel customerPaymentModel);

}
