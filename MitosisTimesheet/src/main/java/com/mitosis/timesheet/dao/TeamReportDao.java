package com.mitosis.timesheet.dao;

import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;

public interface TeamReportDao {

	
	public List<ProjectModel> getProjectList();
	
	public List<TeamAssignmentModel> getTeamList(int projectId,int role);
	
	public int getrole(int userId);
	
	public List<TimeSheetModel> getTeamReportList(Date fromDate, Date toDate,int employeeId);

}
