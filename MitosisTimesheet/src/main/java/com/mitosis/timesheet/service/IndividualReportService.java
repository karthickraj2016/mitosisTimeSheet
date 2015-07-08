package com.mitosis.timesheet.service;


import java.util.List;
import java.util.Date;

import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;

public interface IndividualReportService {

	public List<TimeSheetModel> getIndividualDetailReportList(Date fromdate , Date todate, int employeeId);
	
	public List<SummaryReport> getIndividualSummaryReportList(Date fromdate , Date todate, int employeeId);

	public List<SummaryReport> getIndividualSummaryReportHours(Date fromdate,
			Date toDate, int employeeId);

}
