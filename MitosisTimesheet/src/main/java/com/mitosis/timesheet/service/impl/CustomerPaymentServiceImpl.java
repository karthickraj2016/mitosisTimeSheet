package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.CustomerPaymentDao;
import com.mitosis.timesheet.dao.daoImpl.CustomerPaymentImpl;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.service.CustomerPaymentService;

public class CustomerPaymentServiceImpl implements CustomerPaymentService{
	CustomerPaymentDao cp=new CustomerPaymentImpl();
	@Override
	public boolean save(CustomerPaymentModel c) {
		return cp.save(c);
	}
	@Override
	public boolean remove(int id) {
		return cp.remove(id);
	}
	@Override
	public List<CustomerPaymentModel> showlist() {
		List<CustomerPaymentModel> companyList = new ArrayList<CustomerPaymentModel>();
		companyList = cp.showlist();
		return companyList;
	}
	@Override
	public CustomerPaymentModel show(int id) {
		return cp.show(id);
	}
}
