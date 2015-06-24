package com.mitosis.timesheet.dao;


import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TimeSheetModel;

public interface TimeSheetDAO {
	
	
	public boolean Create(TimeSheetModel timeSheetModel);

	public List<TimeSheetModel> showlist(Object userId);
  
	public boolean deleteTimesheet(int timesheetId);
	
	public double hourslist(int userId, Date date);

	public double getprevioushours(int id);
	
	public List<ProjectModel> getprojectList();

	
  

}
