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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.RoleDetailsModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.TeamAssignmentService;
import com.mitosis.timesheet.service.impl.TeamAssignmentServiceImpl;


@Path("teamAssignment")
public class TeamAssignment {


	TeamAssignmentModel teamModel = new TeamAssignmentModel();
	TeamAssignmentService teamService = new TeamAssignmentServiceImpl();

	@Path("/showAssignedTeamList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TeamAssignmentModel> showTeamList() throws JSONException, ParseException {

		List<TeamAssignmentModel> teamList = new ArrayList<TeamAssignmentModel>();

		teamList = teamService.showTeamList();

		return teamList;

	}
	
	@Path("/showAssignedTeamListByUserProjects")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TeamAssignmentModel> showTeamListByUserProjects(JSONObject jsonobject) throws JSONException, ParseException {

		JSONArray jsonArray = jsonobject.getJSONArray("projectIds");
		
    	List<TeamAssignmentModel> teamList1= new ArrayList<TeamAssignmentModel>();
	
	    List<TeamAssignmentModel> assinedTeamList= new ArrayList<TeamAssignmentModel>();
        
	   	
		for(int i=0; i<jsonArray.length(); i++) {

			JSONObject jsonObject1 = new JSONObject();

			jsonObject1.put("projectId", jsonArray.get(i));
				
		int	projectId=(int)jsonObject1.optInt("projectId");
		
		teamList1= teamService.showTeamListById(projectId);
		
		for(TeamAssignmentModel teamList : teamList1 ){
			
		assinedTeamList.add(teamList);
			
	    }
    }
  
		return assinedTeamList;

}

	@Path("/getProjectList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> getprojectlist() throws JSONException {

		List<ProjectModel> projectList = new ArrayList<ProjectModel>();

		projectList = teamService.getProjectList();

		return projectList;

	}

	@Path("/getMemberList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDetailsModel> getMemberlist() throws JSONException {

		List<UserDetailsModel> memberList = new ArrayList<UserDetailsModel>();

		memberList = teamService.getMemberList();

		return memberList;

	}


	@Path("/getRoleList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RoleDetailsModel> getRolelist() throws JSONException {

		List<RoleDetailsModel> roleList = new ArrayList<RoleDetailsModel>();

		roleList = teamService.getRoleList();

		return roleList;

	}

	@Path("/insertTeamDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject insertTeamDetails(JSONObject jsonobject) throws JSONException, ParseException{

		JSONObject jsonObject = new JSONObject();

		ProjectModel project = new ProjectModel();

		UserDetailsModel member=new UserDetailsModel();

		RoleDetailsModel role=new RoleDetailsModel();

		boolean flag=false;

		TeamAssignmentModel teamAssignModel = new TeamAssignmentModel();

		project.setProjectId(jsonobject.getInt("projectId"));
		teamAssignModel.setProject(project);
		member.setId(jsonobject.getInt("memberId"));
		teamAssignModel.setMember(member);
		role.setId(jsonobject.getInt("roleId"));
		teamAssignModel.setRole(role);
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateInString = jsonobject.getString("releaseDate");
		Date releaseDate = sdf.parse(dateInString); 
		
		teamAssignModel.setReleaseDate(releaseDate);

		flag = teamService.insertTeamDetails(teamAssignModel);

		if(flag){
			jsonObject.put("value", "inserted");

			return jsonObject;
		}
		else{
			jsonObject.put("value", "error");
			return jsonObject;
		}
	}

	@Path("/updateTeamAssignment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateTeamAssignment(JSONObject jsonObject) throws JSONException ,Exception{

		ProjectModel project = new ProjectModel();

		UserDetailsModel member=new UserDetailsModel();

		RoleDetailsModel role=new RoleDetailsModel();

		TeamAssignmentModel teamAssignModel = new TeamAssignmentModel();

		boolean update=false;
				
		JSONObject projects =jsonObject.getJSONObject("project");
		JSONObject members =jsonObject.getJSONObject("member");
		JSONObject roles =jsonObject.getJSONObject("role");
		
		int projectid=projects.getInt("projectId");
		int memberid=members.getInt("id");
		int roleid=roles.getInt("id");
	

		teamAssignModel.setId(jsonObject.getInt("id"));		 
		project.setProjectId(projectid);
		teamAssignModel.setProject(project);
		member.setId(memberid);
		teamAssignModel.setMember(member);
		role.setId(roleid);
		teamAssignModel.setRole(role);
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateInString = jsonObject.getString("releaseDate");
		Date releaseDate = sdf.parse(dateInString); 
		
		teamAssignModel.setReleaseDate(releaseDate);
		
		update = teamService.insertTeamDetails(teamAssignModel);

		if (update) {
			return true;
		} else {
			return false;
		}

	}
	@Path("/deleteAssignment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteAssignment(JSONObject jsonObject) throws JSONException {
		boolean flag =false;
		int id=jsonObject.getInt("id");
		flag=teamService.deleteAssignment(id);

		if (flag) {
			return "success";
		}
		return "failed";
	}
	
	@Path("/validateAssignment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean validateAssignment(JSONObject jsonobject) throws JSONException {
		
		boolean flag=false;
				
		int projectId=jsonobject.getInt("projectId");
		int memberId=jsonobject.getInt("memberId");
				
		flag=teamService.validateAssignment(projectId,memberId);
		
		if(flag){
			return true;
		}else{
			return false;
		}
	}
	

}