package com.mitosis.timesheet.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;






import com.mitosis.timesheet.dao.IndividualReportDao;
import com.mitosis.timesheet.dao.daoImpl.IndividualDetailReportDaoImpl;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;
import com.mitosis.timesheet.service.IndividualReportService;

public class IndividualReportServiceImpl implements IndividualReportService {
	
	IndividualReportDao individualReportDao = new IndividualDetailReportDaoImpl();
		
	@Override
	public List<TimeSheetModel> getIndividualReport(Date fromdate, Date todate, int employeeId) {
		
		List<TimeSheetModel> timeSheetReport = new ArrayList<TimeSheetModel>();
		timeSheetReport=individualReportDao.getIndividualReport(fromdate,todate,employeeId);		
		return timeSheetReport;
	}

	@Override
	public double getTotalHours(Date fromdate, Date todate, int employeeId) {
		// TODO Auto-generated method stub
		double totalhours = individualReportDao.getTotalHours(fromdate,todate,employeeId);
		return totalhours;
	}

	@Override
	public List<SummaryReport> getIndividualSummaryReportList(Date fromdate,
			Date todate, int employeeId) {
		
		List<SummaryReport> timeSheetReportList = new ArrayList<SummaryReport>();
		timeSheetReportList=individualReportDao.getIndividualSummaryReportList(fromdate,todate,employeeId);	
		// TODO Auto-generated method stub
		return timeSheetReportList;
	}

	@Override
	public List<SummaryReport> getIndividualSummaryReportHours(Date fromdate,
			Date toDate, int employeeId) {
		
		List<SummaryReport> timeSheetHoursList = new ArrayList<SummaryReport>();
		timeSheetHoursList = individualReportDao.getIndividualSummaryReportHours(fromdate,
			 toDate,  employeeId);
		// TODO Auto-generated method stub
		return timeSheetHoursList;
	}

	

}
