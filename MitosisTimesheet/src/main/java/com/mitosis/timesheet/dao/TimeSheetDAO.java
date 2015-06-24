package com.mitosis.timesheet.dao;


import java.util.Date;
import java.util.List;

import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;

public interface TimeSheetDAO {
	
	
	public boolean Create(TimeSheetModel timeSheetModel);

	public List<TimeSheetModel> showlist(Object userId);
  
	public boolean deleteTimesheet(int timesheetId);
	
	public double hourslist(int userId, Date date);

	public double getprevioushours(int id);
	
	public List<TeamAssignmentModel> getprojectList(Object userId);

	public UserDetailsModel getUserDetails(Object userId);

}
