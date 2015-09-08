package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.webservice.TeamAssignment;

public interface ProjectCostDetailsService {

	public int addDetailsInHdr(ProjectCostHdrModel hdrModel);

	public boolean addDetailsInCostDetails(ProjectCostDetailsModel detailsModel);

	public boolean addFixedProjectCostDetails(ProjectCostHdrModel hdrModel);

	public ProjectCostHdrModel projectValidation(int projectId);

	public List<ProjectModel> getProjectList();

	public List<TeamAssignmentModel> getTeamMembers(int projectId);

	public List<ProjectCostHdrModel> getAllProjectsCostHdr();

	public List<ProjectCostDetailsModel> getAllProjectsCostDetails();

}
