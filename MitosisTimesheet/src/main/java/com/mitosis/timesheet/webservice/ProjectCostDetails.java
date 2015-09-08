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
import com.mitosis.timesheet.model.TeamAssignmentModel;
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
		if(jsonObject.has("id")){
			hdrModel.setId(jsonObject.getInt("id"));			
		}
		if(jsonObject.has("projectCost")){
			
			
			if(!"".equals(jsonObject.get("projectCost")) && !jsonObject.get("projectCost").equals(null)){
			
		
			int cost=jsonObject.getInt("projectCost");
			BigDecimal projectCost=new BigDecimal(cost); 
			hdrModel.setProjectCost(projectCost);
			}
		}
		hdrModel.setCurrencyCode(jsonObject.getString("currencyCode"));

		int hdrId=costService.addDetailsInHdr(hdrModel);


		UserDetailsModel userModel=new UserDetailsModel();

		JSONArray jsonArray = jsonObject.getJSONArray("empList");

		for(int i=0;i<jsonArray.length();i++){
			ProjectCostDetailsModel detailsModel=new ProjectCostDetailsModel();
	

			detailsModel.setProjectCostHdr(hdrId);

			JSONObject jsonObject1=new JSONObject();

			jsonObject1.put("emp",jsonArray.get(i));
			
			String rateString =String.valueOf(jsonObject1.getJSONObject("emp").get("rate")); 
		
			
			Double ratedouble = Double.parseDouble(rateString);
			
			BigDecimal empRate = BigDecimal.valueOf(ratedouble);

			int employeeId=(int)jsonObject1.getJSONObject("emp").getJSONObject("member").getInt("id");

			if(jsonObject1.getJSONObject("emp").has("id")){
				int detId=(int)jsonObject1.getJSONObject("emp").getInt("id");
				detailsModel.setId(detId);
			}
			userModel.setId(employeeId);
			detailsModel.setEmployee(userModel);		
			detailsModel.setRate(empRate);


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

		if(jsonObject.has("id")){
			hdrModel.setId(jsonObject.getInt("id"));
		}

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

	public ProjectCostHdrModel projectValidation(JSONObject jsonObject)throws JSONException {

		ProjectCostHdrModel hdrModel=new ProjectCostHdrModel();

		int projectId=jsonObject.getInt("projectId");

		hdrModel=costService.projectValidation(projectId);

		return hdrModel;
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
	
	
	@Path("/getTeamMember")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TeamAssignmentModel> getTeamMember(JSONObject jsonObject) throws JSONException {
		
		
		int projectId= jsonObject.getInt("projectId");
		
		List<TeamAssignmentModel> teamMembers = new ArrayList<TeamAssignmentModel>();
		
		
		
		teamMembers = costService.getTeamMembers(projectId);


		return teamMembers;

	}
	
	
	
}