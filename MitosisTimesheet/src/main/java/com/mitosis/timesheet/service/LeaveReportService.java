package com.mitosis.timesheet.service;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.LeaveDetailsModel;


public interface LeaveReportService {

	List<LeaveDetailsModel> LeaveDetailList(Date fromDate, Date toDate);
	


}
