package com.mitosis.timesheet.dao;

import java.sql.Date;
import java.util.List;

import com.mitosis.timesheet.model.TimeSheetModel;

public interface TimeSheetListDao {
	
	public List<TimeSheetModel> getTimeSheetReport(Date from, Date todate, int employeeId);

}
