package com.mitosis.timesheet.dao;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;

public interface IndividualReportDao {
	
	public List<TimeSheetModel> getIndividualDetailReportList(Date from, Date todate, int employeeId);

	public double getTotalHours(Date fromdate, Date todate, int employeeId);
	
	public List<SummaryReport> getIndividualSummaryReportList(Date fromdate, Date todate, int employeeId);

	public List<SummaryReport> getIndividualSummaryReportHours(Date fromdate,
			Date toDate, int employeeId);



}
