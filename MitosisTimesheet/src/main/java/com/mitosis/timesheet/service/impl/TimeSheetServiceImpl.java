package com.mitosis.timesheet.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.dao.TimeSheetDAO;
import com.mitosis.timesheet.dao.daoImpl.TimeSheetDAOImpl;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.pojo.TimeSheetVo;
import com.mitosis.timesheet.service.TimeSheetService;

public class TimeSheetServiceImpl implements TimeSheetService {
	
TimeSheetDAO timeSheetDAO = new TimeSheetDAOImpl();

TimeSheetVo timeSheetVo = new TimeSheetVo();
	
	

	@Override
	public  boolean create(TimeSheetModel timeSheetModel) {
		
		boolean flag= false;
		
		flag = timeSheetDAO.Create(timeSheetModel);
		
		
		
		return flag;
	}

	@Override
	public List<TimeSheetModel>  showlist(Object userId) {
		
		List<TimeSheetModel> timesheetlist = new ArrayList<TimeSheetModel>();

		timesheetlist = timeSheetDAO.showlist(userId);
		
		return timesheetlist;
	}

  @Override
  public boolean deleteTimesheet(int timesheetId) {
    
    timeSheetDAO.deleteTimesheet(timesheetId);
    return true;
  }

@Override
public double gethourslist(Date date, int userId) {
	// TODO Auto-generated method stub
	
	double hoursentered = timeSheetDAO.hourslist(userId, date);
	return hoursentered;
}

@Override
public double getprevioushours(int id) {
	double previoushours = timeSheetDAO.getprevioushours(id);
	return previoushours;
}

@Override
public List<TeamAssignmentModel> getprojectList(Object userId) {
	// TODO Auto-generated method stub
	List<TeamAssignmentModel> projectList = new ArrayList<TeamAssignmentModel>();
	
	projectList=timeSheetDAO.getprojectList(userId);
	System.out.println(projectList);
	return projectList;
}
@Override
public UserDetailsModel getUserDetails(Object userId){
	
	UserDetailsModel details = new UserDetailsModel();
	
	details=timeSheetDAO.getUserDetails(userId);

	return details;
	}
}
