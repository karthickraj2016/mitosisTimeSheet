package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public interface BankReconcileService {

	public CustomerPaymentModel getReceiptDetails(String receiptNumber);

	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber);

	public InvoiceHdrModel getInvoiceHdrDetails(String invoiceNum);
	
	public boolean insert(CustomerPaymentModel customerPaymentModel);

}
