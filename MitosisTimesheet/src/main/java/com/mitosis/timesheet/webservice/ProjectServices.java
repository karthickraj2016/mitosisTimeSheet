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
	public boolean addProject(JSONObject jsonObject) throws JSONException{
		  boolean add;
		  project.setProjectName(jsonObject.getString("projectname"));
		  project.setCustomerName(jsonObject.getString("customername"));
		  project.setBillable(jsonObject.getString("billable"));
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
		  project.setProjectId(jsonObject.getInt("projectId"));
		  project.setProjectName(jsonObject.getString("projectName"));
		  project.setCustomerName(jsonObject.getString("customerName"));
		  project.setBillable(jsonObject.getString("billable"));
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
 
}
