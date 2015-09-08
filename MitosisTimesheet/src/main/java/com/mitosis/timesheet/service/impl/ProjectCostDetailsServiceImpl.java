package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.ProjectCostDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.ProjectCostDetailsDaoImpl;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
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

	@Override
	public  List<TeamAssignmentModel> getTeamMembers(int projectId) {
		List<TeamAssignmentModel> TeamMembers = new ArrayList<TeamAssignmentModel>();

		TeamMembers=costDao.getTeamMembers(projectId);
		return TeamMembers;
		
	}

	@Override
	public List<ProjectCostHdrModel> getAllProjectsCostHdr() {
		
		List<ProjectCostHdrModel> projectCostHdrModel = new ArrayList<ProjectCostHdrModel>();
		
		projectCostHdrModel = costDao.getAllProjectsCostHdr();
		
		
		// TODO Auto-generated method stub
		return projectCostHdrModel;
	}

	@Override
	public List<ProjectCostDetailsModel> getAllProjectsCostDetails() {

		List<ProjectCostDetailsModel> projectCostDetailsModel = new ArrayList<ProjectCostDetailsModel>();
		
		projectCostDetailsModel = costDao.getAllProjectsCostDetails();
		
		
		// TODO Auto-generated method stub
		return projectCostDetailsModel;
		
	}

}
