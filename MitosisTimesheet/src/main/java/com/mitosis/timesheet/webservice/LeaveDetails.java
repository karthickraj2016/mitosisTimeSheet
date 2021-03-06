package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

		String fromLeaveType = jsonObject.getString("fromLeaveType");


		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String fromdateInString = jsonObject.getString("fromDate");
		Date frmDate = sdf.parse(fromdateInString);

		String todateInString = jsonObject.getString("toDate");
		Date toDate = sdf.parse(todateInString);


		Calendar fromcal = Calendar.getInstance();
		Calendar tocal = Calendar.getInstance();
		fromcal.setTime(frmDate);
		tocal.setTime(toDate);

		int leavedays = 1;
		while (fromcal.before(tocal)) {
			if ((Calendar.SATURDAY != fromcal.get(Calendar.DAY_OF_WEEK))
					&&(Calendar.SUNDAY != fromcal.get(Calendar.DAY_OF_WEEK))) {
				leavedays++;
				fromcal.add(Calendar.DATE,1);
			}else {
				fromcal.add(Calendar.DATE,1);
			}
		}

		BigDecimal totalLeave = new BigDecimal(leavedays);

		if(fromLeaveType.equals("First Half") || fromLeaveType.equals("Second Half")){


			totalLeave = totalLeave.subtract(new BigDecimal(0.5),new MathContext(2));


		}

		if(jsonObject.has("toLeaveType")){
			String toLeaveType = jsonObject.getString("toLeaveType");

			if( !jsonObject.getString("toLeaveType").equals("") || !jsonObject.getString("toLeaveType").equals(null)){



				if(toLeaveType.equals("First Half") || toLeaveType.equals("Second Half")){

					totalLeave = totalLeave.subtract(new BigDecimal(0.5),new MathContext(2));


				}
			}


			leaveModel.settoLeaveType(toLeaveType);
		}




		if (jsonObject.has("reason")) {
			leaveModel.setReason(jsonObject.getString("reason"));
		}

		/*String status=jsonObject.getString("status");*/

		userModel.setId(employeeId);
		leaveModel.setEmployee(userModel);
		leaveModel.setFromDate(frmDate);
		leaveModel.setToDate(toDate);
		leaveModel.setNoOfDays(totalLeave);
		leaveModel.setfromLeaveType(fromLeaveType);

		/*leaveModel.setStatus(status);*/

		boolean validation = false;
		validation=leaveService.validateEntry(leaveModel,validation);

		if(validation){
			json.put("value", "already exist");
			return json;
		}else{

			insert=leaveService.insertLeaveEntry(leaveModel);

			if(insert){
				json.put("value", "inserted");

				return json;
			}else{
				json.put("value", "error");
				return json;
			}
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


		Calendar fromcal = Calendar.getInstance();
		Calendar tocal = Calendar.getInstance();
		fromcal.setTime(frmDate);
		tocal.setTime(toDate);

		int leavedays = 1;
		while (fromcal.before(tocal)) {
			if ((Calendar.SATURDAY != fromcal.get(Calendar.DAY_OF_WEEK))
					&&(Calendar.SUNDAY != fromcal.get(Calendar.DAY_OF_WEEK))) {
				leavedays++;
				fromcal.add(Calendar.DATE,1);
			}else {
				fromcal.add(Calendar.DATE,1);
			}
		}


		String fromLeaveType = jsonObject.getString("fromLeaveType");



		BigDecimal totalLeave = new BigDecimal(leavedays);

		if(fromLeaveType.equals("First Half") || fromLeaveType.equals("Second Half")){



			totalLeave = totalLeave.subtract(new BigDecimal(0.5),new MathContext(2));


		}

		if(jsonObject.has("toLeaveType")){

			String toLeaveType = jsonObject.getString("toLeaveType");

			if(!jsonObject.getString("toLeaveType").equals("") || !jsonObject.getString("toLeaveType").equals(null)){

				leaveModel.settoLeaveType(toLeaveType);


				if(toLeaveType.equals("First Half") || toLeaveType.equals("Second Half")){



					totalLeave = totalLeave.subtract(new BigDecimal(0.5),new MathContext(2));
				}
			}


		}




		/*String status=jsonObject.getString("status");*/

		if (jsonObject.has("id")) {
			leaveModel.setId(jsonObject.getInt("id"));
		}
		userModel.setId(employeeId);
		leaveModel.setEmployee(userModel);
		leaveModel.setFromDate(frmDate);
		leaveModel.setToDate(toDate);
		leaveModel.setNoOfDays(totalLeave);
		leaveModel.setfromLeaveType(fromLeaveType);

		/*leaveModel.setStatus(status);*/
		boolean validation = false;

		validation=leaveService.validateEntryForUpdate(leaveModel,validation);

		if(validation){

			json.put("value", "already exist");
			return json;

		}else{

			update=leaveService.updateLeaveEntry(leaveModel);

			if(update){
				json.put("value", "updated");

				return json;
			}else{
				json.put("value", "error");
				return json;
			}
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
