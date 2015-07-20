package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.dao.LeaveReportDao;
import com.mitosis.timesheet.dao.daoImpl.LeaveReportDaoImpl;
import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.service.LeaveReportService;


public class LeaveReportServiceImpl implements LeaveReportService {
	
	
	LeaveReportDao leaveReportDao = new LeaveReportDaoImpl();

	@Override
	public List<LeaveDetailsModel> LeaveDetailList(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		
		
		List<LeaveDetailsModel> leaveDetailList = new ArrayList<LeaveDetailsModel>();
		
		leaveDetailList= leaveReportDao.leaveDetailList(fromDate,toDate);
		return leaveDetailList;
	}

}
