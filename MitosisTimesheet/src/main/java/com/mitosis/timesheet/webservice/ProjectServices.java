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

import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.service.ProjectService;
import com.mitosis.timesheet.service.impl.ProjectServiceImpl;


@Path("project")
public class ProjectServices {
	ProjectModel project = new ProjectModel();
	ProjectService projectservice = new ProjectServiceImpl();

	@Path("/addproject")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addProject(JSONObject jsonObject) throws JSONException, ParseException{
		boolean add;
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String startDateInString = jsonObject.getString("startdate");
		Date startDate = sdf.parse(startDateInString); 
		String endDateInString = jsonObject.getString("enddate");
		Date endDate = sdf.parse(endDateInString); 
		CustomerDetailsModel customer=new CustomerDetailsModel();
		
		project.setProjectName(jsonObject.getString("projectname"));
		customer.setCustomerId(jsonObject.getInt("customerId"));
		project.setCustomer(customer);
		project.setBillable(jsonObject.getString("billable"));
		project.setStartDate(startDate);
		project.setEndDate(endDate);
		project.setStatus(jsonObject.getString("status"));
		add = projectservice.update(project);
		if (add==true) {
			return true;
		} else {
			return false;
		}
	}

	@Path("/updateproject")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateProject(JSONObject jsonObject) throws JSONException ,Exception{
		
		boolean update;
		CustomerDetailsModel customer=new CustomerDetailsModel();
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String startDateInString = jsonObject.getString("startdate");
		Date startDate = sdf.parse(startDateInString); 
		String endDateInString = jsonObject.getString("enddate");
		Date endDate = sdf.parse(endDateInString); 
		
		project.setProjectId(jsonObject.getInt("projectId"));
		project.setProjectName(jsonObject.getString("projectName"));
		customer.setCustomerId(jsonObject.getInt("customerId"));
		project.setCustomer(customer);
		project.setBillable(jsonObject.getString("billable"));
		project.setStartDate(startDate);
		project.setEndDate(endDate);
		project.setStatus(jsonObject.getString("status"));
		update = projectservice.update(project);
		if (update==true) {
			return true;
		} else {
			return false;
		}

	}

	@Path("/removeproject")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean removeProject(JSONObject jsonObject) throws JSONException{
		boolean delete;
		int projectId=jsonObject.getInt("projectId");
		delete = projectservice.removeProjectById(projectId);
		if (delete) {
			return true;
		} else {
			return false;
		}
	}
	@Path("/showProjectlist")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> showlist() throws JSONException, ParseException {

		List<ProjectModel> projectlist = new ArrayList<ProjectModel>();

		projectlist = projectservice.showlist();

		return projectlist;

	}

	@POST
	@Path("/nameValidation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean nameValidation(JSONObject jsonObject) throws Exception {

		boolean namevalidation,compare;
		String name="";
		int projectId=jsonObject.getInt("projectId");
		String projectName = jsonObject.getString("projectName");
		if(projectId!=0){
			name=projectservice.getProjectName(projectId);
			compare=name.equalsIgnoreCase(projectName);
			if(!compare){
				namevalidation = projectservice.checkProjectName(projectName);
				if (namevalidation != true) {
					return true;
				} else {
					return false;
				}
			}else{
				return true;
			}
		}
		namevalidation = projectservice.checkProjectName(projectName);
		System.out.println(namevalidation);
		if (namevalidation != true) {
			return true;
		} else {
			return false;
		}
	}
	
	@Path("/getCustomerList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDetailsModel> getMemberlist() throws JSONException {

		List<CustomerDetailsModel> customerList = new ArrayList<CustomerDetailsModel>();

		customerList = projectservice.getCustomerList();

		return customerList;

	}

}
