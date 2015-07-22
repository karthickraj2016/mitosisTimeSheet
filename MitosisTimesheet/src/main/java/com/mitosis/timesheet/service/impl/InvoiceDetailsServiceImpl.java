package com.mitosis.timesheet.service.impl;

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.InvoiceDetailsDaoImpl;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.service.InvoiceDetailsService;

public class InvoiceDetailsServiceImpl implements InvoiceDetailsService {
	
	InvoiceDetailsDao invoiceDetailsDao = new InvoiceDetailsDaoImpl();	


	@Override
	public boolean validateEntry(InvoiceHdrModel invoiceHdrModel) {
		// TODO Auto-generated method stub
		
		boolean validation = invoiceDetailsDao.validateEntry(invoiceHdrModel);
		return validation;
	}


	@Override
	public boolean create(InvoiceHdrModel invoiceHdrModel) {
		boolean validation = invoiceDetailsDao.create(invoiceHdrModel);
		return validation;
	}


	@Override
	public boolean create(InvoiceDetailsModel invoiceDetailsModel) {
		boolean validation = invoiceDetailsDao.create(invoiceDetailsModel);
		return validation;
	}


	@Override
	public String getInvoiceNumber() {
		// TODO Auto-generated method stub
		
		String Invoicenumber = invoiceDetailsDao.getInvoiceNumber();
		return Invoicenumber;
	}

}
