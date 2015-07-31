package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.ProjectCostDetailsService;
import com.mitosis.timesheet.service.impl.ProjectCostDetailsServiceImpl;

@Path("projectCost")
public class ProjectCostDetails {

	ProjectCostDetailsService costService=new ProjectCostDetailsServiceImpl();

	@Path("/addHourlyProjectCostDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject  addHourlyProjectCostDetails(JSONObject jsonObject)throws JSONException{

		JSONObject json =new JSONObject();

		ProjectCostHdrModel hdrModel=new ProjectCostHdrModel();

		ProjectModel projectModel=new ProjectModel();

		projectModel.setProjectId(jsonObject.getInt("projectId"));
		hdrModel.setProject(projectModel);
		hdrModel.setProjectType(jsonObject.getString("projectType"));
		if(jsonObject.has("projectCost")){
			int cost=jsonObject.getInt("projectCost");
			BigDecimal projectCost=new BigDecimal(cost); 
			hdrModel.setProjectCost(projectCost);
		}
		hdrModel.setCurrencyCode(jsonObject.getString("currencyCode"));

		int hdrId=costService.addDetailsInHdr(hdrModel);

		ProjectCostDetailsModel detailsModel=new ProjectCostDetailsModel();

		UserDetailsModel userModel=new UserDetailsModel();
		
		JSONArray jsonArray = jsonObject.getJSONArray("empList");
		
		for(int i=0;i<jsonArray.length();i++){
		
		hdrModel.setId(hdrId);
		detailsModel.setProjectCostHdr(hdrModel);
		
				
		JSONObject jsonObject1=new JSONObject();
		
		jsonObject1.put("emp",jsonArray.get(i));
		
		int empRate=(int)jsonObject1.getJSONObject("emp").getInt("rate");
		
		int employeeId=(int)jsonObject1.getJSONObject("emp").getJSONObject("member").getInt("id");
		
		userModel.setId(employeeId);
		detailsModel.setEmployee(userModel);
		BigDecimal rate=new BigDecimal(empRate);		
		detailsModel.setRate(rate);
		detailsModel.setId(0);

		boolean insert=false;

		insert=costService.addDetailsInCostDetails(detailsModel);
		if(insert){

			json.put("value", "success");

		}else{

			json.put("value", "error");
		}
	}
		return json;
	}

	@Path("/addFixedProjectCostDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject  addFixedProjectCostDetails(JSONObject jsonObject)throws JSONException{

		JSONObject json =new JSONObject();

		ProjectCostHdrModel hdrModel=new ProjectCostHdrModel();

		ProjectModel projectModel=new ProjectModel();

		projectModel.setProjectId(jsonObject.getInt("projectId"));
		hdrModel.setProject(projectModel);
		hdrModel.setProjectType(jsonObject.getString("projectType"));
		
		int cost=jsonObject.getInt("projectCost");
		BigDecimal projectCost=new BigDecimal(cost); 
		hdrModel.setProjectCost(projectCost);

		hdrModel.setCurrencyCode(jsonObject.getString("currencyCode"));

		boolean insert=false;

		insert=costService.addFixedProjectCostDetails(hdrModel);

		if(insert){

			json.put("value", "success");

		}else{

			json.put("value", "error");
		}

		return json;
	}
	
	@Path("/projectValidation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject projectValidation(JSONObject jsonObject)throws JSONException {
		
	JSONObject json=new JSONObject();
	
	int projectId=jsonObject.getInt("projectId");
	
	boolean project=false;
	
	project=costService.projectValidation(projectId);
	
	if(project){
		
		json.put("value", "already exist");
	}
		return json;
	}
	
	@Path("/getProjectList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> getprojectlist() throws JSONException {

		List<ProjectModel> projectList = new ArrayList<ProjectModel>();

		projectList = costService.getProjectList();

		return projectList;

	}
}