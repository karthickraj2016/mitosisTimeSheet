package com.mitosis.timesheet.dao;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.LeaveDetailsModel;

public interface LeaveReportDao {

	List<LeaveDetailsModel> leaveDetailList(Date fromDate, Date toDate);
	
	
	
}
