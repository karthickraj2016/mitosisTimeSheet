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

import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.HeaderControlService;
import com.mitosis.timesheet.service.impl.HeaderControlServiceImpl;





@Path("headercontrols")
public class HeaderControls {
	

	
	@Context
	private HttpServletRequest request;
	
	HeaderControlService headerControlService = new HeaderControlServiceImpl();
		
	
	
	@GET
	@Path("/homebutton")
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject homebutton() throws JSONException {
		
		JSONObject jsonobject = new JSONObject();
		

		HttpSession session = request.getSession(true);
		
		if (session.getAttribute("userId") == null) {
			return null;
		}
		int userId =  (Integer) request.getSession().getAttribute("userId");
		
		int adminFlag = headerControlService.adminFlag(userId);
		
		jsonobject.put("adminFlag", adminFlag);
		
		
		return jsonobject;
	}
	


}
