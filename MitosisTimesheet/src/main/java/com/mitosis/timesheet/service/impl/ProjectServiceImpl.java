package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.ProjectDAO;
import com.mitosis.timesheet.dao.daoImpl.ProjectDAOImpl;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.service.ProjectService;

public class ProjectServiceImpl implements ProjectService {
	
	ProjectDAO ProjectDAO = new ProjectDAOImpl();
		
			@Override
		public  boolean save(ProjectModel project) {
			
			boolean flag= false;
			
			flag = ProjectDAO.save(project);
				
			return flag;
		}

		@Override
		public  boolean update(ProjectModel project) {
			
			boolean flag= false;
			
			flag = ProjectDAO.update(project);
				
			return flag;
		}
	  
		@Override
		public  boolean removeProjectById(int projectId) {
			
			boolean flag= false;
			
			flag = ProjectDAO.removeProjectById(projectId);
				
			return flag;
		}
		
		@Override
		public List<ProjectModel>  showlist() {
			
			List<ProjectModel> projectList = new ArrayList<ProjectModel>();
	
			projectList = ProjectDAO.showlist();
			
			return projectList;
		}
		@Override
		public  boolean checkProjectName(String projectName) throws Exception {
			
			boolean flag= false;
			
			flag = ProjectDAO.checkProjectName(projectName);
				
			return flag;
		}
		@Override
		public  String getProjectName(int projectId) throws Exception {
			
			String name="";
			
			name = ProjectDAO.getProjectName(projectId);
				
			return name;
		}

		@Override
		public List<CustomerDetailsModel> getCustomerList() {
			
			List<CustomerDetailsModel> customerList = new ArrayList<CustomerDetailsModel>();
			
		     customerList = ProjectDAO.getCustomerlist();
			
			return customerList;
		}
}
