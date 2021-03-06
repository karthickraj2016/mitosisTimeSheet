package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.dao.TeamReportDao;
import com.mitosis.timesheet.dao.daoImpl.TeamReportDaoImpl;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;
import com.mitosis.timesheet.service.TeamReportService;

public class TeamReportServiceImpl implements TeamReportService {
	
	TeamReportDao teamReportDao = new TeamReportDaoImpl();

	@Override
	public List<TeamAssignmentModel> getProjectList(int employeeId) {
		List<TeamAssignmentModel> projectList = new ArrayList<TeamAssignmentModel>();
		
		projectList = teamReportDao.getProjectList(employeeId);
		
		
		
		return projectList;
	}

	@Override
	public List<TeamAssignmentModel> getTeamList(int projectId, int role) {
		// TODO Auto-generated method stub
		List<TeamAssignmentModel> teamList = new ArrayList<TeamAssignmentModel>();
		
		
		teamList = teamReportDao.getTeamList(projectId,role);
		return teamList;
	}

	@Override
	public int getrole(int userId,int projectId) {
		
		int role = teamReportDao.getrole(userId,projectId);
		// TODO Auto-generated method stub
		return role;
	}

	@Override
	public List<TimeSheetModel> getTeamSummaryTimeSheetList(Date fromDate, Date toDate,
			int employeeId, int projectId) {
		
		List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
		
		timeSheetList = teamReportDao.getTeamSummaryTimeSheetList(fromDate,toDate, employeeId, projectId);
		// TODO Auto-generated method stub
		
		return timeSheetList;
	}


	@Override
	public List<SummaryReport> getTeamSummarySumHours(Date fromDate, Date toDate,
			int memberId, int projectId) {
		List<SummaryReport> summaryList = teamReportDao.getTeamSummarySumHours(fromDate,toDate,memberId,projectId);
		return summaryList;
	}
	
	@Override
	public int checkUserRights(int employeeId,int projectId){
	
 		int level=teamReportDao.checkUserRights(employeeId, projectId);
		
 		return level;
	
	}

	@Override
	public List<TimeSheetModel> getTeamDetailTimeSheetList(Date fromDate,
			Date toDate, int memberId, int projectId) {
	List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
		
		timeSheetList = teamReportDao.getTeamDetailTimeSheetList(fromDate,toDate, memberId, projectId);
		// TODO Auto-generated method stub
		
		return timeSheetList;
	}
	
	@Override
	public List<TimeSheetModel> getAllProjectsDetails(Date fromDate,
			Date toDate) {
	List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
		
		timeSheetList = teamReportDao.getAllProjectsDetails(fromDate,toDate);
		// TODO Auto-generated method stub
		
		return timeSheetList;
	}
	
	@Override
	public List<TimeSheetModel> getAllProjectsSummary(Date fromDate,
			Date toDate) {
	List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
		
		timeSheetList = teamReportDao.getAllProjectsSummary(fromDate,toDate);
		// TODO Auto-generated method stub
		
		return timeSheetList;
	}
	
	@Override
	public List<SummaryReport> getAllProjectsSumHours(Date fromDate, Date toDate) {
		List<SummaryReport> summaryList = teamReportDao.getAllProjectsSumHours(fromDate,toDate);
		return summaryList;
	}

	
}
