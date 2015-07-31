package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;

public interface ProjectCostDetailsService {

	public int addDetailsInHdr(ProjectCostHdrModel hdrModel);

	public boolean addDetailsInCostDetails(ProjectCostDetailsModel detailsModel);

	public boolean addFixedProjectCostDetails(ProjectCostHdrModel hdrModel);

	public boolean projectValidation(int projectId);

	public List<ProjectModel> getProjectList();

}
