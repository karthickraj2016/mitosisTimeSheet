package com.mitosis.timesheet.dao;

import java.sql.Date;
import java.util.List;

import com.mitosis.timesheet.model.TimeSheetModel;

public interface TimeSheetReportDao {
	
	public List<TimeSheetModel> getTimeSheetDetailReport(Date from, Date todate, int employeeId);

}
