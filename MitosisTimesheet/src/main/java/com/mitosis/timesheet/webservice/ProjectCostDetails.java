package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.ProjectCostDetailsService;
import com.mitosis.timesheet.service.impl.ProjectCostDetailsServiceImpl;
import com.mitosis.timesheet.util.JasperUtil;

@Path("projectCost")
public class ProjectCostDetails  extends JasperUtil{

	ProjectCostDetailsService costService=new ProjectCostDetailsServiceImpl();
	
	
	@Context private HttpServletRequest request;

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
			
			
			if(jsonObject1.getJSONObject("emp").has("rate") && !jsonObject1.getJSONObject("emp").get("rate").toString().equals("") && !jsonObject1.getJSONObject("emp").get("rate").equals(null)){
			
			String rateString =String.valueOf(jsonObject1.getJSONObject("emp").get("rate")); 
		
			
			Double ratedouble = Double.parseDouble(rateString);
			
			BigDecimal empRate = BigDecimal.valueOf(ratedouble);
			
			detailsModel.setRate(empRate);
			
			}

			int employeeId=(int)jsonObject1.getJSONObject("emp").getJSONObject("member").getInt("id");

			if(jsonObject1.getJSONObject("emp").has("id")){
				int detId=(int)jsonObject1.getJSONObject("emp").getInt("id");
				detailsModel.setId(detId);
			}
			userModel.setId(employeeId);
			detailsModel.setEmployee(userModel);		
			


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
	
	
	@Path("/viewAllProjectCosts")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectCostHdrModel> getAllProjects() throws JSONException {
		
		List<ProjectCostHdrModel> projectCostHdrModel = new ArrayList<ProjectCostHdrModel>();
		
		projectCostHdrModel = costService.getAllProjectsCostHdr();

		return projectCostHdrModel;

	}
	
	@Path("/pendingProjectCosts")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> pendingProjectCosts() throws Exception {
		
		
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");

		int employeeId =(Integer) request.getSession().getAttribute("userId");
		
		List<ProjectModel> projectModel = new ArrayList<ProjectModel>();
		
		String reportFilePath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "reports/employeeDetailsReport.jrxml";


		Map<String, Object> parameters = new HashMap<String, Object>();
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		String pdfPath = path.replaceAll("WEB-INF/classes/", "");
		String imagePath = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("WEB-INF/classes/", "");

		String pdfFilePath = pdfPath
				+ "reports/employeeDetailsReport" + employeeId + ".pdf";

		RenderJr(reportFilePath, parameters,pdfFilePath);


		JRBeanCollectionDataSource ds = null;

		return projectModel;

	}
	
	
	
	
}