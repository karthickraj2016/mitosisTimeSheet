package com.mitosis.timesheet.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.dao.InvoiceReportsDao;
import com.mitosis.timesheet.dao.daoImpl.InvoiceReportsDaoImpl;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.service.InvoiceReportsService;


public class InvoiceReportsServiceImpl implements InvoiceReportsService{
	
	InvoiceReportsDao invoiceReportsDao = new InvoiceReportsDaoImpl();

	@Override
	public List<ProjectModel> pendingInvoiceProjectList(Date firstday, Date lastday) {
		
		List<ProjectModel> pendingProjectList = new ArrayList<ProjectModel>();
		
		pendingProjectList = invoiceReportsDao.pendingInvoiceProjectList(firstday,lastday);
		return pendingProjectList;

	}

	@Override
	public List<InvoiceHdrModel> pendingBalanceList() {
		
		List<InvoiceHdrModel> pendingBalanceList = new ArrayList<InvoiceHdrModel>();
		
		pendingBalanceList = invoiceReportsDao.pendingBalanceList();
		// TODO Auto-generated method stub
		return pendingBalanceList;
	}

}
