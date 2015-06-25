package com.mitosis.timesheet.service;


import java.util.List;
import java.util.Date;


import com.mitosis.timesheet.model.TimeSheetModel;

public interface IndividualReportService {

	public List<TimeSheetModel> getIndividualReport(Date fromdate , Date todate, int employeeId);

	public double getTotalHours(Date fromdate, Date todate, int employeeId);

}
