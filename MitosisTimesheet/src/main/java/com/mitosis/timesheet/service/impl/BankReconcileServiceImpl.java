package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.BankReconcileDao;
import com.mitosis.timesheet.dao.daoImpl.BankReconcileDaoImpl;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.service.BankReconcileService;

public class BankReconcileServiceImpl implements BankReconcileService {

	BankReconcileDao reconcileDao=new BankReconcileDaoImpl();
	
	@Override
	public CustomerPaymentModel getReceiptDetails(String receiptNumber) {
		
		CustomerPaymentModel paymentModel=new CustomerPaymentModel();
		
		paymentModel=reconcileDao.getReceiptDetails(receiptNumber);
		
		return paymentModel;
	}

	@Override
	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber) {
		
		
		List<CustomerPaymentModel> paymentModel=new ArrayList<CustomerPaymentModel>();
		
		paymentModel=reconcileDao.getPaymentDetails(invoiceNumber);
		
		return paymentModel;
	}

	@Override
	public CustomerPaymentModel getCustomerDetail(String receiptNumber) {
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel();
		
		customerPaymentModel = reconcileDao.getCustomerDetail(receiptNumber);
		// TODO Auto-generated method stub
		return customerPaymentModel;
	}

	

	@Override
	public boolean insert(CustomerPaymentModel customerPaymentModel) {
		// TODO Auto-generated method stub
		boolean insert = reconcileDao.insert(customerPaymentModel);
		
		return insert;
	}

	@Override
	public InvoiceHdrModel getInvoiceDetails(String invoiceNumber) {
		
		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		
		invoicehdrModel = reconcileDao.getInvoiceDetails(invoiceNumber);
		// TODO Auto-generated method stub
		return invoicehdrModel;
	}

}
