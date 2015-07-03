package com.mitosis.timesheet.dao;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;

public interface TeamReportDao {

	
	public List<TeamAssignmentModel> getProjectList(int employeeId);
	
	public List<TeamAssignmentModel> getTeamList(int projectId,int role);
	
	public int getrole(int userId,int projectId);
	
	public List<TimeSheetModel> getTeamSummaryTimeSheetList(Date fromDate, Date toDate,int memberIds,int projectId);

	
	public double getTotalHours(Date fromDate, Date toDate, int memberId,
			int projectId);

	public List<SummaryReport> getSumHours(Date fromDate, Date toDate,
			int memberId, int projectId);

	public int checkUserRights(int employeeId,int projectId);

	public List<TimeSheetModel> getTeamDetailTimeSheetList(Date date, Date toDate,
			int memberId, int projectId);

	public List<TimeSheetModel> getAllProjectsDetails(Date fromDate, Date toDate);

	public List<TimeSheetModel> getAllProjectsSummary(Date fromDate, Date toDate);

	public List<SummaryReport> getAllUserSumHours(Date fromDate, Date toDate);

	public double getAllUsersTotalHours(Date fromDate, Date toDate);

}
