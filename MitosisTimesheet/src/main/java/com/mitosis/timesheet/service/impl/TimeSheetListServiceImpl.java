package com.mitosis.timesheet.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.TimeSheetListDao;
import com.mitosis.timesheet.dao.daoImpl.TimeSheetListDaoImpl;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.service.TimeSheetListService;

public class TimeSheetListServiceImpl implements TimeSheetListService {
	
	TimeSheetListDao timeSheetListDao = new TimeSheetListDaoImpl();
		
	@Override
	public List<TimeSheetModel> getTimeSheetReport(Date fromdate, Date todate, int employeeId) {
		
		List<TimeSheetModel> timeSheetlist = new ArrayList<TimeSheetModel>();
		timeSheetlist=timeSheetListDao.getTimeSheetReport(fromdate,todate,employeeId);		
		return timeSheetlist;
	}

	

}
