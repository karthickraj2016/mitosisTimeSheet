package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.CustomerPaymentDao;
import com.mitosis.timesheet.dao.daoImpl.CustomerPaymentImpl;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.service.CustomerPaymentService;

public class CustomerPaymentServiceImpl implements CustomerPaymentService{
	CustomerPaymentDao customerPaymentDao=new CustomerPaymentImpl();
	@Override
	public boolean save(CustomerPaymentModel c) {
		return customerPaymentDao.save(c);
	}
	@Override
	public boolean remove(int id) {
		return customerPaymentDao.remove(id);
	}
	@Override
	public List<CustomerPaymentModel> showlist() {
		List<CustomerPaymentModel> companyList = new ArrayList<CustomerPaymentModel>();
		companyList = customerPaymentDao.showlist();
		return companyList;
	}
	@Override
	public CustomerPaymentModel show(int id) {
		return customerPaymentDao.show(id);
	}
	@Override
	public boolean checkReceiptNo(String rno) {
		return customerPaymentDao.checkReceiptNo(rno);
	}
	@Override
	public List<InvoiceHdrModel> pendingReceiptList() {
		List<InvoiceHdrModel> pendingReceiptList = new ArrayList<InvoiceHdrModel>();
		pendingReceiptList = customerPaymentDao.pendingReceiptList();
		return pendingReceiptList;
	} 
}
