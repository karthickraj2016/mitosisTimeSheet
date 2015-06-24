package com.mitosis.timesheet.service;

import java.sql.Date;
import java.util.List;


import com.mitosis.timesheet.model.TimeSheetModel;

public interface IndividualReportService {

	public List<TimeSheetModel> getIndividualReport(Date fromdate,Date todate, int employeeId);

	public double getTotalHours(Date fromdate, Date todate, int employeeId);

}
