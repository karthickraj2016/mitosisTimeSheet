package com.mitosis.timesheet.service;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;

public interface TeamReportService {
	
	public List<TeamAssignmentModel> getProjectList(int employeeId);

	public List<TeamAssignmentModel> getTeamList(int projectId, int role);
	
	public int getrole(int userId,int projectId);

	public List<TimeSheetModel> getTeamReportList(Date fromDate, Date toDate,int employeeId,int projectId);

	public List<TimeSheetModel> getteamReportIndividual(Date fromDate,
			Date toDate, int employeeId);

	public double getTotalHours(Date fromDate, Date toDate, int memberId,
			int projectId);
	
	public List<SummaryReport> getSumHours(Date fromDate, Date toDate, int memberId,
			int projectId);

    public int checkUserRights(int employeeId,int projectId);

}
