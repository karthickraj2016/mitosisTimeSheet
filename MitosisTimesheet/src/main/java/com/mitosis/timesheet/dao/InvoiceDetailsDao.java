package com.mitosis.timesheet.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;

public interface InvoiceDetailsDao {

	public boolean validateEntry(InvoiceHdrModel invoiceHdrModel);

	public boolean create(InvoiceHdrModel invoiceHdrModel);

	public boolean create(List<InvoiceDetailsModel> invoiceDetailsModel) throws IllegalAccessException, InvocationTargetException;

	public String getInvoiceNumber();

	public int getId(String invoiceNumber);

	public List<ProjectModel> getProjectList();

	public List<CustomerDetailsModel> getCustomerList();

	public List<ProjectCostHdrModel> getProjectCosthdrList(int id);

	public List<ProjectCostDetailsModel> getTeamList(int projectId);

	public CompanyInfoModel getCompanyInfo();
	
	public List<InvoiceHdrModel> getInvoiceList(int projectId);

	public InvoiceHdrModel getInvoiceHdr(String invoiceNumber);
}
