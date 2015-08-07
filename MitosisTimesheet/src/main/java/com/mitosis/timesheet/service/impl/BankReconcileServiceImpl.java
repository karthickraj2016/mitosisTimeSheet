package com.mitosis.timesheet.service.impl;

import com.mitosis.timesheet.dao.BankReconcileDao;
import com.mitosis.timesheet.dao.daoImpl.BankReconcileDaoImpl;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.service.BankReconcileService;

public class BankReconcileServiceImpl implements BankReconcileService {

	BankReconcileDao reconcileDao=new BankReconcileDaoImpl();
	
	@Override
	public CustomerPaymentModel getReceiptDetails(String ino) {
		
		CustomerPaymentModel paymentModel=new CustomerPaymentModel();
		
		paymentModel=reconcileDao.getReceiptDetails(ino);
		
		return paymentModel;
	}

}
