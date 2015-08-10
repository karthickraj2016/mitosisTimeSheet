package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;

public interface BankReconcileService {

	public CustomerPaymentModel getReceiptDetails(String receiptNumber);

	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber);

}
