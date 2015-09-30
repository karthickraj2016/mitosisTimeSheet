package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;

public interface CustomerPaymentService {
	public boolean save(CustomerPaymentModel c);
	public boolean remove(int id);
	public List<CustomerPaymentModel> showlist();
	public CustomerPaymentModel show(int id);
	public boolean checkReceiptNo(String receiptNo);
	public List<InvoiceHdrModel> pendingReceiptList();
}
