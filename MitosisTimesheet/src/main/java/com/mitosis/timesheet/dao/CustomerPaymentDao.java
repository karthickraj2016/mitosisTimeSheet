package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;

public interface CustomerPaymentDao {
	public boolean save(CustomerPaymentModel c);
	public boolean remove(int id);
	public List<CustomerPaymentModel> showlist();
	public CustomerPaymentModel show(int id);
	public boolean checkReceiptNo(String receiptNo);
}
