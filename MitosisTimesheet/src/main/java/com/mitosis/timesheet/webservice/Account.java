package com.mitosis.timesheet.webservice;

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

import com.google.gson.JsonObject;
import com.mitosis.timesheet.commonservice.JavaMD5Hash;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.pojo.UserDetailsVo;
import com.mitosis.timesheet.service.AccountDetailsService;
import com.mitosis.timesheet.service.impl.AccountDetailsServiceImpl;


@Path("accountdetails")
public class Account {
	
	
	@Context
	private HttpServletRequest request;
	
	AccountDetailsService accountDetailsService = new AccountDetailsServiceImpl();

	@GET
	@Path("/getaccountdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetailsModel getAccountDetails(){
		
		JSONObject jsonObject = new JSONObject();
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return userDetailsModel;
		}
		Object userId = session.getAttribute("userId");
		
		userDetailsModel = accountDetailsService.getAccountDetails(userId);
		
		return userDetailsModel;
		
		
		
	}
	
	
	@POST
	@Path("/changepassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject changePassword(JSONObject jsonObject) throws JSONException{
		
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		
		String currentpassword = jsonObject.getString("currentpassword");
		
		String newpassword = jsonObject.getString("newpassword");
		
		JSONObject jsonobject = new JSONObject();
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		
		userDetailsModel = accountDetailsService.getAccountDetails(userId);
		 
		 String encryptedCurrentPassword =JavaMD5Hash.md5(currentpassword);
		 String encryptedNewPassword = JavaMD5Hash.md5(newpassword);
		 
		 if(!encryptedCurrentPassword.equals(userDetailsModel.getPassword())){
			 
			 jsonobject.put("msg","error_currentpassword"); 
			 
			return jsonobject;

			 
		 }
		 else{
			 
			 userDetailsModel.setPassword(encryptedNewPassword);
			 System.out.println(userDetailsModel);
			 
			boolean updatepassword =  accountDetailsService.updateNewPassword(userDetailsModel);
			
			if(updatepassword){
				
				jsonobject.put("msg", "updated");
				
			}
			 
			 return jsonobject;
			 
		 }

		
	}
	
	@POST
	@Path("/checkmailid")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean checkmailid(JSONObject jsonObject) throws JSONException{
		
		String MailId = jsonObject.getString("email");
		
		boolean checkmailId = accountDetailsService.checkMailId(MailId);
		
		
		
		return checkmailId;
	
	}
	
	
	@POST
	@Path("/editdetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject editUserDetails(JSONObject jsonObject) throws JSONException{
		
		UserDetailsModel userDetailsModel  = new UserDetailsModel();
		
		JSONObject jsonobject = new JSONObject();
		
		boolean updated;
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		
		
		userDetailsModel = accountDetailsService.getAccountDetails(userId);
		
		userDetailsModel.setName(jsonObject.getString("name"));
		
		userDetailsModel.seteMail(jsonObject.getString("email"));
		
		userDetailsModel.setUserName(jsonObject.getString("username"));
				
		updated= accountDetailsService.updateDetails(userDetailsModel);
		
		if(updated){
			
			jsonobject.put("msg", "updated");
			return jsonobject;
			
		}
		else{
		
		jsonobject.put("msg", "updationerror");
		return jsonobject;
		}
		
		
		
		
		
	}
	
	@GET
	@Path("/logout")
	public String logout() {

		HttpSession session = request.getSession(true);
		session.removeAttribute("userId");

		return "success";
	}

}
