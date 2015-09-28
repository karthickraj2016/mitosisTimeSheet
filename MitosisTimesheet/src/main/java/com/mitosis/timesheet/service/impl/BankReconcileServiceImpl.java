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
	public List<CustomerPaymentModel> getReceiptDetails() {
		
		List<CustomerPaymentModel> paymentModel=new ArrayList<CustomerPaymentModel>();
		
		paymentModel=reconcileDao.getReceiptDetails();
		
		return paymentModel;
	}

	@Override
	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber) {
		
		
		List<CustomerPaymentModel> paymentModel=new ArrayList<CustomerPaymentModel>();
		
		paymentModel=reconcileDao.getPaymentDetails(invoiceNumber);
		
		return paymentModel;
	}

	@Override
	public InvoiceHdrModel getInvoiceHdrDetails(String invoiceNum) {
		
		InvoiceHdrModel invoiceModel=reconcileDao.getInvoiceHdrDetails(invoiceNum);
	
		return invoiceModel;
	}

	@Override
	public boolean insert(CustomerPaymentModel customerPaymentModel) {
		
		boolean insert = reconcileDao.insert(customerPaymentModel);
		
		return insert;
	}

}
