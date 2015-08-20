package com.mitosis.timesheet.service;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectModel;



public interface InvoiceReportsService {
	
	
	public List<ProjectModel> pendingInvoiceProjectList(Date firstday, Date lastday);

	public List<InvoiceHdrModel> pendingBalanceList();
	


}
