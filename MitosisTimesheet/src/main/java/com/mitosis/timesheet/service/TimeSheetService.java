package com.mitosis.timesheet.service;



import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;

public interface TimeSheetService {
	
	
	public boolean create(TimeSheetModel timeSheetModel);

	public List<TimeSheetModel> showlist(Object userId);
	
	public boolean deleteTimesheet(int timesheetId);

	public double gethourslist(Date date, int userId);

	public double getprevioushours(int id);
	
	public List<ProjectModel> getprojectList(Object userId);
	
	public UserDetailsModel getUserDetails(Object userId);
 
	}