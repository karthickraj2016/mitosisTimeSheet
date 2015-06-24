package com.mitosis.timesheet.dao;

import java.sql.Date;
import java.util.List;

import com.mitosis.timesheet.model.TimeSheetModel;

public interface IndividualReportDao {
	
	public List<TimeSheetModel> getIndividualReport(Date from, Date todate, int employeeId);

	public double getTotalHours(Date fromdate, Date todate, int employeeId);

}
