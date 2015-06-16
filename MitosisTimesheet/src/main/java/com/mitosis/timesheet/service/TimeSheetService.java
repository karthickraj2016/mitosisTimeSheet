package com.mitosis.timesheet.service;


import java.sql.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;


import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TimeSheetModel;

public interface TimeSheetService {
	
	
	public boolean create(TimeSheetModel timeSheetModel);

	public List<TimeSheetModel> showlist(Object userId);
	
	public boolean deleteTimesheet(int timesheetId);

	public double gethourslist(Date date, int userId);

	public double getprevioushours(int id);
	
	public List<ProjectModel> getprojectList();
 
	}