package com.mitosis.timesheet.service;

import java.sql.Date;
import java.util.List;


import com.mitosis.timesheet.model.TimeSheetModel;

public interface TimeSheetListService {

	public List<TimeSheetModel> getTimeSheetReport(Date fromdate,Date todate, int employeeId);

}
