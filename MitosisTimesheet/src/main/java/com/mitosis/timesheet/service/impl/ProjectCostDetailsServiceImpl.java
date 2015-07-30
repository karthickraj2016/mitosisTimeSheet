package com.mitosis.timesheet.service.impl;

import com.mitosis.timesheet.dao.ProjectCostDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.ProjectCostDetailsDaoImpl;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
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
	public boolean projectValidation(int projectId) {
     
		boolean project=false;
		
		project=costDao.projectValidation(projectId);
		
		return project;
	}

}