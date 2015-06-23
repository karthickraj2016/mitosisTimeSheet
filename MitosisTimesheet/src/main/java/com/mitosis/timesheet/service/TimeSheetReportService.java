package com.mitosis.timesheet.service;

import java.sql.Date;
import java.util.List;


import com.mitosis.timesheet.model.TimeSheetModel;

public interface TimeSheetReportService {

	public List<TimeSheetModel> getTimeSheetDetailReport(Date fromdate,Date todate, int employeeId);

}
