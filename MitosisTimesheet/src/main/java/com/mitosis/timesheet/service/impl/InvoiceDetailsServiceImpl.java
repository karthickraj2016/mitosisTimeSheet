package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.InvoiceDetailsDaoImpl;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
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


	@Override
	public int getId(String invoiceNumber) {
		// TODO Auto-generated method stub
		int id  = invoiceDetailsDao.getId(invoiceNumber);
		return id;
	}


	@Override
	public List<ProjectModel> getProjectList() {
		// TODO Auto-generated method stub
		List<ProjectModel> projectModel = new ArrayList<ProjectModel>();
		
		projectModel = invoiceDetailsDao.getProjectList();
		return projectModel;
	}


	@Override
	public List<CustomerDetailsModel> getCustomerList() {
		
		
		List<CustomerDetailsModel> customerDetailsModel = new ArrayList<CustomerDetailsModel>();
		
		customerDetailsModel = invoiceDetailsDao.getCustomerList();
		// TODO Auto-generated method stub
		return customerDetailsModel;
	}


	@Override
	public List<ProjectCostHdrModel> getProjectCostHdrList() {
		// TODO Auto-generated method stub
		
		List<ProjectCostHdrModel> ProjectCosthdrList = new ArrayList<ProjectCostHdrModel>();
		
		ProjectCosthdrList = invoiceDetailsDao.getProjectCosthdrList();
 		return ProjectCosthdrList;
	}


	@Override
	public List<TeamAssignmentModel> getTeamList() {
		// TODO Auto-generated method stub
		
		List<TeamAssignmentModel> teamList = new ArrayList<TeamAssignmentModel>();
		
		teamList = invoiceDetailsDao.getTeamList();
		return teamList;
	}

}
