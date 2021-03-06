package com.mitosis.timesheet.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.InvoiceDetailsDaoImpl;
import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
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
	public InvoiceHdrModel create(InvoiceHdrModel invoiceHdrModel) {
		InvoiceHdrModel invoicehdrModel = invoiceDetailsDao.create(invoiceHdrModel);
		return invoicehdrModel;
	}


	@Override
	public boolean create(List<InvoiceDetailsModel> invoiceDetailsModel) throws IllegalAccessException, InvocationTargetException {
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
	public List<ProjectCostHdrModel> getProjectCostHdrList(int id) {
		// TODO Auto-generated method stub

		List<ProjectCostHdrModel> ProjectCosthdrList = new ArrayList<ProjectCostHdrModel>();

		ProjectCosthdrList = invoiceDetailsDao.getProjectCosthdrList(id);
		return ProjectCosthdrList;
	}


	@Override
	public List<ProjectCostDetailsModel> getTeamList(int projectId) {
		// TODO Auto-generated method stub

		List<ProjectCostDetailsModel> teamMembersList = new ArrayList<ProjectCostDetailsModel>();

		teamMembersList = invoiceDetailsDao.getTeamList(projectId);
		return teamMembersList;
	}


	@Override
	public CompanyInfoModel getCompanyInfo() {
		CompanyInfoModel companyInfo = new CompanyInfoModel();

		companyInfo = invoiceDetailsDao.getCompanyInfo();
		return companyInfo;
	}

	@Override
	public List<InvoiceHdrModel> getInvoiceList(int projectId) {
		List<InvoiceHdrModel> invoiceList = new ArrayList<InvoiceHdrModel>();
		invoiceList = invoiceDetailsDao.getInvoiceList(projectId);
		return invoiceList;
	} 

	@Override
	public InvoiceHdrModel getInvoiceHdr(String invoiceNumber){
		return invoiceDetailsDao.getInvoiceHdr(invoiceNumber);
	}

	@Override
	public List<InvoiceDetailsModel> getInvoiceDetails(int invoiceno){
		List<InvoiceDetailsModel> invoiceDetailsList = new ArrayList<InvoiceDetailsModel>();
		invoiceDetailsList = invoiceDetailsDao.getInvoiceDetails(invoiceno);

		return invoiceDetailsList;





	}


	@Override
	public ProjectCostHdrModel getCostHdrId(int projectId) {

		ProjectCostHdrModel projectCostHdrModel = new ProjectCostHdrModel();

		projectCostHdrModel = invoiceDetailsDao.getCostHdrId(projectId);
		// TODO Auto-generated method stub
		return projectCostHdrModel;
	}


	@Override
	public ProjectCostDetailsModel getMemberRate(int projectCostId,int memberId) {
		
		
		ProjectCostDetailsModel projectCostDetailsModel = new ProjectCostDetailsModel();

		projectCostDetailsModel = invoiceDetailsDao.getMemberRate(projectCostId,memberId);
		
		
		return projectCostDetailsModel;
	}


}
