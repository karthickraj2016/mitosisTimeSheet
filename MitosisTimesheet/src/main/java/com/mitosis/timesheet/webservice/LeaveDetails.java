package com.mitosis.timesheet.webservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.LeaveDetailsService;
import com.mitosis.timesheet.service.impl.LeaveDetailsServiceImpl;


@Path("leaveDetails")
public class LeaveDetails {
	
	LeaveDetailsService leaveService=new LeaveDetailsServiceImpl();
	
	
	@Path("/insertLeaveEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject insertLeaveEntry(JSONObject jsonObject) throws JSONException, ParseException{
		
	boolean insert=false;
	
	JSONObject json=new JSONObject();
	
	LeaveDetailsModel leaveModel=new LeaveDetailsModel();
	UserDetailsModel userModel=new UserDetailsModel();
	
	int employeeId=jsonObject.getInt("employeeId");
	
	DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String fromdateInString = jsonObject.getString("fromDate");
	Date frmDate = sdf.parse(fromdateInString);
		
	String todateInString = jsonObject.getString("toDate");
	Date toDate = sdf.parse(todateInString);
	
	if (jsonObject.has("reason")) {
		leaveModel.setReason(jsonObject.getString("reason"));
	}
	
	/*String status=jsonObject.getString("status");*/
	
	userModel.setId(employeeId);
	leaveModel.setEmployee(userModel);
	leaveModel.setFromDate(frmDate);
	leaveModel.setToDate(toDate);
	/*leaveModel.setStatus(status);*/
	
	insert=leaveService.insertLeaveEntry(leaveModel);
	
	if(insert){
		json.put("value", "inserted");

		return json;
	}else{
		json.put("value", "error");
		return json;
	}
	
	}
	
	@Path("/updateLeaveEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject updateLeaveEntry(JSONObject jsonObject) throws JSONException, ParseException{
		
	boolean update=false;
	
    JSONObject json=new JSONObject();
	
	LeaveDetailsModel leaveModel=new LeaveDetailsModel();
	UserDetailsModel userModel=new UserDetailsModel();
	
	int employeeId=jsonObject.getInt("employeeId");
	
	DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String fromdateInString = jsonObject.getString("fromDate");
	Date frmDate = sdf.parse(fromdateInString);
		
	String todateInString = jsonObject.getString("toDate");
	Date toDate = sdf.parse(todateInString);
	
	if (jsonObject.has("reason")) {
		leaveModel.setReason(jsonObject.getString("reason"));
	}
	
	/*String status=jsonObject.getString("status");*/
	
	if (jsonObject.has("id")) {
		leaveModel.setId(jsonObject.getInt("id"));
	}
	userModel.setId(employeeId);
	leaveModel.setEmployee(userModel);
	leaveModel.setFromDate(frmDate);
	leaveModel.setToDate(toDate);
	/*leaveModel.setStatus(status);*/
	
	update=leaveService.updateLeaveEntry(leaveModel);
	
	if(update){
		json.put("value", "updated");

		return json;
	}else{
		json.put("value", "error");
		return json;
	}
	
	}
	
	@Path("/deleteLeaveEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String deleteLeaveEntry(JSONObject jsonObject) throws JSONException {
		
		boolean delete =false;
		
		int id=jsonObject.getInt("id");
		
		delete=leaveService.deleteLeaveEntry(id);

		if (delete) {
			return "success";
		}
		return "failed";
	}
	
	@Path("/showLeaveEntryList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<LeaveDetailsModel> showLeaveEntryList() throws JSONException {
		
		List<LeaveDetailsModel> leaveModel=new ArrayList<LeaveDetailsModel>();
		
		leaveModel=leaveService.showLeaveEntryList();
		
		return leaveModel;
	}

		
}
