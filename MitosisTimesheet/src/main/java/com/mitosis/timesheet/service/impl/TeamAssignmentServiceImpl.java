package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.TeamAssignmentDAO;
import com.mitosis.timesheet.dao.daoImpl.TeamAssignmentDAOImpl;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.RoleDetailsModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.TeamAssignmentService;

public class TeamAssignmentServiceImpl implements TeamAssignmentService  {
	
	TeamAssignmentDAO teamDAO = new TeamAssignmentDAOImpl();
	
	@Override
	public List<TeamAssignmentModel>  showTeamList() {
		
		List<TeamAssignmentModel> teamList = new ArrayList<TeamAssignmentModel>();

		teamList = teamDAO.showTeamList();
		
		return teamList;
	}

	@Override
	public List<ProjectModel> getProjectList() {

		List<ProjectModel> projectList = new ArrayList<ProjectModel>();
		
		projectList=teamDAO.getProjectList();
		System.out.println(projectList);
		return projectList;
	}
	

	@Override
	public List<UserDetailsModel> getMemberList() {
		
		List<UserDetailsModel> memberList = new ArrayList<UserDetailsModel>();
		
		memberList=teamDAO.getMemberList();
		System.out.println(memberList);
		return memberList;
	}
	
	@Override
	public List<RoleDetailsModel> getRoleList() {
		
		List<RoleDetailsModel> roleList = new ArrayList<RoleDetailsModel>();
		
		roleList=teamDAO.getRoleList();
		System.out.println(roleList);
		return roleList;
	}
	
	@Override
	public boolean insertTeamDetails(TeamAssignmentModel teamAssignModel){
       
		boolean flag= false;
		
		flag = teamDAO.insertTeamDetails(teamAssignModel);
				
	   return flag;
	}
	
	
	 @Override
	  public boolean deleteAssignment(int id) {
		 boolean flag= false;
	  flag= teamDAO.deleteAssignment(id);
	    return flag;
	  }
	 
	 @Override
	 public boolean validateAssignment(int projectId,int memberId){
		 
		 boolean flag=false;
		 
		 flag=teamDAO.validateAssignment(projectId,memberId);
		 
		 return flag;
	 }

}
