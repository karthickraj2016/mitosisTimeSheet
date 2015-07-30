package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.ProjectModel;


public interface ProjectService {
	  
	public boolean save(ProjectModel project);
	public boolean update(ProjectModel project);
	public boolean removeProjectById(int projectId);
	public List<ProjectModel> showlist();
	public  boolean checkProjectName(String projectName) throws Exception;
	public  String getProjectName(int projectId) throws Exception;
	public List<CustomerDetailsModel> getCustomerList();
	public List<ProjectModel>getProjectList(int cusid);
}