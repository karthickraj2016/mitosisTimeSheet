package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public interface BankReconcileService {

	public CustomerPaymentModel getReceiptDetails(String receiptNumber);

	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber);

	public CustomerPaymentModel getCustomerDetail(String receiptNumber);

	public boolean insert(CustomerPaymentModel customerPaymentModel);

	public InvoiceHdrModel getInvoiceDetails(String invoiceNumber);

}
