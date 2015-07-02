package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.dao.TeamReportDao;
import com.mitosis.timesheet.dao.daoImpl.TeamReportDaoImpl;
import com.mitosis.timesheet.model.ProjectModel;
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
	public int getrole(int userId) {
		
		int role = teamReportDao.getrole(userId);
		// TODO Auto-generated method stub
		return role;
	}

	@Override
	public List<TimeSheetModel> getTeamReportList(Date fromDate, Date toDate,
			int employeeId, int projectId) {
		
		List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
		
		timeSheetList = teamReportDao.getTeamReportList(fromDate,toDate, employeeId, projectId);
		// TODO Auto-generated method stub
		
		return timeSheetList;
	}

	@Override
	public List<TimeSheetModel> getteamReportIndividual(Date fromDate,
			Date toDate, int employeeId) {
List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
		
		timeSheetList = teamReportDao.getteamReportIndividual(fromDate,toDate, employeeId);
		// TODO Auto-generated method stub
		
		return timeSheetList;
	
	}

	@Override
	public double getTotalHours(Date fromDate, Date toDate, int memberId,
			int projectId) {
		// TODO Auto-generated method stub
		double hours = teamReportDao.getTotalHours(fromDate,toDate,memberId, projectId);
		return hours;
	}

	@Override
	public List<SummaryReport> getSumHours(Date fromDate, Date toDate,
			int memberId, int projectId) {
		List<SummaryReport> summaryList = teamReportDao.getSumHours(fromDate,toDate,memberId,projectId);
		return summaryList;
	}

}
