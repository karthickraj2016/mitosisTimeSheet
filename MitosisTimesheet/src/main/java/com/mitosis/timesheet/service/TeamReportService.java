package com.mitosis.timesheet.service;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;

public interface TeamReportService {
	
	public List<TeamAssignmentModel> getProjectList(int employeeId);

	public List<TeamAssignmentModel> getTeamList(int projectId, int role);
	
	public int getrole(int userId,int projectId);

	public List<TimeSheetModel> getTeamReportList(Date fromDate, Date toDate,int employeeId,int projectId);

	
	public double getTotalHours(Date fromDate, Date toDate, int memberId,
			int projectId);
	
	public List<SummaryReport> getSumHours(Date fromDate, Date toDate, int memberId,
			int projectId);

    public int checkUserRights(int employeeId,int projectId);

	public List<TimeSheetModel> getTeamReportDetailList(Date fromDate,
			Date toDate, int memberId, int projectId);

	public List<TimeSheetModel> getAllProjectsDetails(Date fromDate, Date toDate);

	public List<TimeSheetModel> getAllProjectsSummary(Date fromDate, Date toDate);

	public List<SummaryReport> getAllUsersSumHours(Date fromDate, Date toDate);

	public double getAllUsersTotalHours(Date fromDate, Date toDate);

}
