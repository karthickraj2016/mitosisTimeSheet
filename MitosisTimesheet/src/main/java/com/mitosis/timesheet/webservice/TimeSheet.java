package com.mitosis.timesheet.webservice;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.pojo.TimeSheetVo;
import com.mitosis.timesheet.service.ProjectService;
import com.mitosis.timesheet.service.TimeSheetService;
import com.mitosis.timesheet.service.impl.ProjectServiceImpl;
import com.mitosis.timesheet.service.impl.TimeSheetServiceImpl;

@Path("timesheet")

public class TimeSheet {

	JSONObject jsonobject = new JSONObject();
	TimeSheetService timeSheetService = new TimeSheetServiceImpl();
	@Context private HttpServletRequest request;
	ProjectService projectService = new ProjectServiceImpl();


	@Path("/insertdetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject insertDetails(JSONObject jsonobject) throws JSONException, ParseException{

		TimeSheetModel timeSheetModel = new TimeSheetModel();
		
		TimeSheetVo timesheetVo = new TimeSheetVo();

		JSONObject jsonObject = new JSONObject();
		
				DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				String dateInString = jsonobject.getString("date");
				Date frmDate = sdf.parse(dateInString); 
						

		double totalhours = 16.0;

		int userId =  (Integer) request.getSession().getAttribute("userId");

		double hoursentered= timeSheetService.gethourslist(frmDate,userId);

		double hoursallowed=totalhours-hoursentered;

		double hourentry = hoursentered + jsonobject.getDouble("hours");

		if(hoursallowed==0){

			jsonObject.put("value","done");

			return jsonObject;
		}

		if(totalhours<hourentry){

			jsonObject.put("hoursallowed",hoursallowed);
			jsonObject.put("hoursentered",hoursentered);
			jsonObject.put("value", "err_total");

			return jsonObject;
	
		}else{

			boolean flag = false;
			ProjectModel project = new ProjectModel();

			timeSheetModel.setDate(frmDate);
			timeSheetModel.setHours(jsonobject.getDouble("hours"));
			timeSheetModel.setDescription(jsonobject.getString("description"));
			project.setProjectId(jsonobject.getInt("projectId"));
			timeSheetModel.setProject(project);
			timeSheetModel.setEmployeeId((Integer) request.getSession().getAttribute("userId"));
			if (jsonobject.has("issueNumber")) {
				timeSheetModel.setIssueNumber(jsonobject.getString("issueNumber"));
			}
			if (jsonobject.has("id")) {
				timeSheetModel.setId(jsonobject.getInt("id"));
			}	
	
			flag = timeSheetService.create(timeSheetModel);

			if(flag){
				jsonObject.put("value", "inserted");

				return jsonObject;
			}
			else{
				jsonObject.put("value", "error");
				return jsonObject;
			}

		}
	}

	@Path("/showlist")
	@GET
  /*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.APPLICATION_JSON)
	public List<TimeSheetModel> showlist() throws JSONException, ParseException {
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");

		List<TimeSheetModel> timesheetlist = new ArrayList<TimeSheetModel>();

		timesheetlist = timeSheetService.showlist(userId);
		
		return timesheetlist;

	}


	@Path("/deleteTimesheet")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteTimesheet(JSONObject jsonObject) throws JSONException {
		boolean flag = timeSheetService.deleteTimesheet(jsonObject.getInt("id"));

		if (flag) {
			return "success";
		}
		return "failed";
	}

	@Path("/updateTimesheet")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject hourslist(JSONObject jsonObject) throws JSONException, ParseException {
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String dateInString = jsonObject.getString("date");
		Date frmDate = sdf.parse(dateInString); 
		

		HttpSession session= request.getSession(true);

		int userId =  (Integer) request.getSession().getAttribute("userId");

		double totalhours = 16.0;

		TimeSheetModel timeSheetModel = new TimeSheetModel();

		double previoushour = timeSheetService.getprevioushours(jsonObject.getInt("id"));

		double hoursentered= timeSheetService.gethourslist(frmDate,userId);

		hoursentered =hoursentered -previoushour;

		double hourentry = hoursentered + jsonObject.getDouble("hours");

		double hoursallowed=totalhours-hoursentered;
		
		ProjectModel project = new ProjectModel();


		if(totalhours>=hourentry){

			boolean flag = false;

			timeSheetModel.setDate(frmDate);
			timeSheetModel.setHours(jsonObject.getDouble("hours"));
			timeSheetModel.setDescription(jsonObject.getString("description"));
			
			project.setProjectId(jsonObject.getInt("ProjectId"));
			
			timeSheetModel.setProject(project);
		
			timeSheetModel.setEmployeeId((Integer) request.getSession().getAttribute("userId"));
			if(jsonObject.getString("issueNumber")=="null"){

			}
			else if (jsonObject.has("issueNumber")) {
				timeSheetModel.setIssueNumber(jsonObject.getString("issueNumber"));
			}
			if (jsonObject.has("id")) {
				timeSheetModel.setId(jsonObject.getInt("id"));
			}
			// timeSheetModel.setTaskStatus(jsonobject.getString("taskstatus"));
			// timeSheetModel.setStatus(jsonobject.getString("status"));

			flag = timeSheetService.create(timeSheetModel);

			if(flag){
				jsonObject.put("value", "inserted");

				return jsonObject;
			}
			else{
				jsonObject.put("value", "error");
				return jsonObject;
			}  

		}else if(totalhours<hourentry){

			jsonObject.put("hoursallowed", hoursallowed);
			jsonObject.put("value", "err_total");
			return jsonObject;

		}else{

			jsonObject.put("hoursallowed", hoursallowed);
			jsonObject.put("value", "hoursallowedis0");

			return jsonObject;
		}
	}
	
	
	@Path("/getprojectlist")
	@GET
  /*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<TeamAssignmentModel> getprojectlist() throws JSONException {
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		
		Object userId=session.getAttribute("userId");
		
		List<TeamAssignmentModel> projectList = new ArrayList<TeamAssignmentModel>();
		
		projectList = timeSheetService.getprojectList(userId);
				
		return projectList;
}
	
	
	@Path("/getUserDetails")
	@GET
 /* @Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.APPLICATION_JSON)
	
	public UserDetailsModel getUserDetails() throws JSONException  {
	
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		UserDetailsModel user = new UserDetailsModel();
		
		user = timeSheetService.getUserDetails(userId);
		
		return user;
   }
	
}
