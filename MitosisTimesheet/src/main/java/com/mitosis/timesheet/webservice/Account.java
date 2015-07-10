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

import com.mitosis.timesheet.commonservice.JavaMD5Hash;
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
	public UserDetailsVo getAccountDetails(){
		
		JSONObject jsonObject = new JSONObject();
		
		UserDetailsVo userDetailsVo = new UserDetailsVo();
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return userDetailsVo;
		}
		Object userId = session.getAttribute("userId");
		
		userDetailsVo = accountDetailsService.getAccountDetails(userId);
		
		return userDetailsVo;
		
		
		
	}
	
	
	@POST
	@Path("/changepassword")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject changePassword(JSONObject jsonObject) throws JSONException{
		
		
		UserDetailsVo userDetailsVo = new UserDetailsVo();
		
		
		String currentpassword = jsonObject.getString("currentpassword");
		
		String newpassword = jsonObject.getString("newpassword");
		
		JSONObject jsonobject = new JSONObject();
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		
		 userDetailsVo = accountDetailsService.getAccountDetails(userId);
		 
		 String encryptedCurrentPassword =JavaMD5Hash.md5(currentpassword);
		 String encryptedNewPassword = JavaMD5Hash.md5(newpassword);
		 
		 if(!encryptedCurrentPassword.equals(userDetailsVo.getPassword())){
			 
			 jsonobject.put("msg","error_currentpassword"); 
			 
			return jsonobject;

			 
		 }
		 else{
			 
			 userDetailsVo.setPassword(encryptedNewPassword);
			 
			boolean updatepassword =  accountDetailsService.updateNewPassword(userDetailsVo);
			
			if(updatepassword){
				
				jsonobject.put("msg", "updated");
				
			}
			 
			 return jsonobject;
			 
		 }

		
	}
	
	
	@POST
	@Path("/editdetails")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject editUserDetails(JSONObject jsonObject) throws JSONException{
		
		UserDetailsVo userDetailsVo  = new UserDetailsVo();
		
		JSONObject jsonobject = new JSONObject();
		
		boolean updated;
		
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		
		
		userDetailsVo = accountDetailsService.getAccountDetails(userId);
		
		userDetailsVo.setName(jsonObject.getString("name"));
		
		userDetailsVo.seteMail(jsonObject.getString("email"));
		
		userDetailsVo.setUserName(jsonObject.getString("username"));
				
		updated= accountDetailsService.updateDetails(userDetailsVo);
		
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
