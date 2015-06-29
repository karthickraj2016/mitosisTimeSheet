package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.RoleDetailsModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.UserDetailsModel;

public interface TeamAssignmentService {
	
	public List<TeamAssignmentModel> showTeamList();
	
	public List<TeamAssignmentModel> showTeamListById(int projectId);
	
	public List<ProjectModel> getProjectList();
	
	public List<UserDetailsModel> getMemberList();
	
	public List<RoleDetailsModel> getRoleList();
	
	public boolean insertTeamDetails(TeamAssignmentModel teamAssignModel);
	
	public boolean deleteAssignment(int id);
	
	public boolean validateAssignment(int projectId,int memberId);
	
}
