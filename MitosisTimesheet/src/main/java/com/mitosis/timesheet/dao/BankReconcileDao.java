package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;

public interface BankReconcileDao {

	public CustomerPaymentModel getReceiptDetails(String recieptNumber);

	public List<CustomerPaymentModel> getInvoiceDetails(String invoiceNumber);

}
