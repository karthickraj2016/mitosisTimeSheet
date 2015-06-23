package com.mitosis.timesheet.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.TimeSheetReportDao;
import com.mitosis.timesheet.dao.daoImpl.TimeSheetReportDaoImpl;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.service.TimeSheetReportService;

public class TimeSheetReportServiceImpl implements TimeSheetReportService {
	
	TimeSheetReportDao timeSheetReportDao = new TimeSheetReportDaoImpl();
		
	@Override
	public List<TimeSheetModel> getTimeSheetDetailReport(Date fromdate, Date todate, int employeeId) {
		
		List<TimeSheetModel> timeSheetReport = new ArrayList<TimeSheetModel>();
		timeSheetReport=timeSheetReportDao.getTimeSheetDetailReport(fromdate,todate,employeeId);		
		return timeSheetReport;
	}

	

}
