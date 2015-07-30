package com.mitosis.timesheet.dao;

import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;

public interface ProjectCostDetailsDao {

	public int addDetailsInHdr(ProjectCostHdrModel hdrModel);

	public boolean addDetailsInCostDetails(ProjectCostDetailsModel detailsModel);

	public boolean addFixedProjectCostDetails(ProjectCostHdrModel hdrModel);

	public boolean projectValidation(int projectId);

}
