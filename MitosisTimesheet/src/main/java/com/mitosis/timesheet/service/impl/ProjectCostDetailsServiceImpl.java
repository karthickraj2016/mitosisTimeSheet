package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.ProjectCostDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.ProjectCostDetailsDaoImpl;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.service.ProjectCostDetailsService;

public class ProjectCostDetailsServiceImpl implements ProjectCostDetailsService {

	ProjectCostDetailsDao costDao=new ProjectCostDetailsDaoImpl();
	
	@Override
	public int addDetailsInHdr(ProjectCostHdrModel hdrModel) {
		
		int hdrId;
		
		hdrId=costDao.addDetailsInHdr(hdrModel);
		
		return hdrId;
	}

	@Override
	public boolean addDetailsInCostDetails(ProjectCostDetailsModel detailsModel) {
	
		boolean insert=false;
		
		insert=costDao.addDetailsInCostDetails(detailsModel);
		
		return insert;
	}

	@Override
	public boolean addFixedProjectCostDetails(ProjectCostHdrModel hdrModel) {
      
		boolean insert=false;
		
		insert=costDao.addFixedProjectCostDetails(hdrModel);
		
		return insert;
	}

	@Override
	public ProjectCostHdrModel projectValidation(int projectId) {
     
		ProjectCostHdrModel hdrModel=new ProjectCostHdrModel();
		
		hdrModel=costDao.projectValidation(projectId);
		
		return hdrModel;
	}

	@Override
	public List<ProjectModel> getProjectList() {

		List<ProjectModel> projectList = new ArrayList<ProjectModel>();
		
		projectList=costDao.getProjectList();
		System.out.println(projectList);
		return projectList;
	}

}
