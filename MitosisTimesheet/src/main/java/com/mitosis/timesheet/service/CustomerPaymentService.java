package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerPaymentModel;

public interface CustomerPaymentService {
	public boolean save(CustomerPaymentModel c);
	public boolean remove(int id);
	public List<CustomerPaymentModel> showlist();
	public CustomerPaymentModel show(int id);
}
