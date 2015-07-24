package com.mitosis.timesheet.webservice;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.UserRightsService;
import com.mitosis.timesheet.service.impl.UserRightsServiceImpl;

@Path("userRights")
public class UserRights {
	
	UserDetailsModel userModel = new UserDetailsModel();
	UserRightsService rightsService = new UserRightsServiceImpl();
	
	@Path("/showUserList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDetailsModel> showUserList() throws JSONException, ParseException {

		List<UserDetailsModel> userList = new ArrayList<UserDetailsModel>();

		userList = rightsService.showUserList();

		return userList;

	}
	
	@Path("/updateRights")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateRights(JSONObject jsonobject) throws JSONException, ParseException{

		JSONObject jsonObject = new JSONObject();

		UserDetailsModel userModel=new UserDetailsModel();

		boolean flag=false;

		userModel.setId(jsonobject.getInt("id"));
		userModel.setName(jsonobject.getString("name"));
		userModel.setUserName(jsonobject.getString("userName"));
		userModel.seteMail(jsonobject.getString("eMail"));
		userModel.setPassword(jsonobject.getString("password"));
		userModel.setActive(jsonobject.getBoolean("active"));
		userModel.setManageFinance(jsonobject.getBoolean("manageFinance"));
		userModel.setManageProject(jsonobject.getBoolean("manageProject"));
		userModel.setManageTeam(jsonobject.getBoolean("manageTeam"));
		userModel.setManageCustomer(jsonobject.getBoolean("manageCustomer"));
		userModel.setManageEmployees(jsonobject.getBoolean("manageEmployees"));

		flag = rightsService.updateRights(userModel);

		if(flag){
			jsonObject.put("value", "updated");

			return jsonObject;
		}
		else{
			jsonObject.put("value", "error");
			return jsonObject;
		}
	}	
	
}
