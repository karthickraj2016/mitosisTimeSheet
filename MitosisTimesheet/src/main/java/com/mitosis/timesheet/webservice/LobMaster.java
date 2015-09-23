package com.mitosis.timesheet.webservice;

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

import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.service.LobMasterService;
import com.mitosis.timesheet.service.impl.LobMasterServiceImpl;


@Path("lobMaster")
public class LobMaster {


	LobMasterService lobMasterService  = new LobMasterServiceImpl();


	@Path("/getLobList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<LobModel> getLobList (){

		List<LobModel> lobModel = new ArrayList<LobModel>();

		lobModel=lobMasterService.getLobList();

		return lobModel;

	}


	@Path("/insertEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject insert (JSONObject jsonobject) throws JSONException{

		JSONObject jsonObject = new JSONObject();

		LobModel lobModel = new LobModel();
		
		lobModel.setLobName(jsonobject.getString("lobName"));
		
		lobModel.setSkills(jsonobject.getString("skills"));
		
		
		
		boolean validation =lobMasterService.validate(lobModel.getLobName());
		
		if(validation){
			
			boolean insert = lobMasterService.insert(lobModel);
			 jsonObject.put("value","inserted");

		}
		

		else{
			
			jsonObject.put("value","alreadyentered");
			
			
		}

		return jsonObject;

	}

}
