package com.mitosis.timesheet.webservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.EmployeeMasterService;
import com.mitosis.timesheet.service.impl.EmployeeMasterServiceImpl;
import com.mitosis.timesheet.util.JasperUtil;

@Path("employeeMaster")
public class EmployeeMaster extends JasperUtil{
	
	EmployeeMasterService masterService=new EmployeeMasterServiceImpl();
	EmployeeMasterModel masterModel=new EmployeeMasterModel();
	
	@Context private HttpServletRequest request;
	
	@Path("/getLobList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<LobModel> getLobList()throws JSONException{
		
		List<LobModel> lobList=new ArrayList<LobModel>();
		
		lobList=masterService.getLobList();
		
		return lobList;
		
	}
	
	@Path("/addEmployeeDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject addEmployeeDetails(JSONObject jsonObject) throws JSONException, ParseException {
				
		JSONObject json=new JSONObject();
		UserDetailsModel userModel=new UserDetailsModel();
						
		userModel.setId(jsonObject.getInt("userId"));
		masterModel.setEmployee(userModel);
		masterModel.setEmployeeId(jsonObject.getString("employeeId"));
		masterModel.setFirstName(jsonObject.getString("firstName"));
		masterModel.setLastName(jsonObject.getString("lastName"));
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String joiningdateInString = jsonObject.getString("joiningDate");
		Date joiningDate = sdf.parse(joiningdateInString);
		
		masterModel.setJoiningDate(joiningDate);
		
		String expStartdateInString = jsonObject.getString("expStartDate");
		Date expStartDate = sdf.parse(expStartdateInString);
		
		masterModel.setExpStartDate(expStartDate);
		
		String asOndateInString = jsonObject.getString("asOnDate");
		Date asOnDate=sdf.parse(asOndateInString);
		masterModel.setAsOnDate(asOnDate);
		
	/*	LobModel lobModel=new LobModel();
		lobModel.setId();
		masterModel.setLob(lobModel);*/
				
		masterModel.setBillable(jsonObject.getString("billable"));
				
		boolean insert=false;
		
		insert=masterService.addEmployeeDetails(masterModel);
		
		if(insert){
			json.put("value", "success");
		}else{
			json.put("value", "error");
		}
		return json;
	}
	
	@Path("/deleteEmployeeDetailEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String deleteEmployeeDetailEntry(JSONObject jsonObject) throws JSONException {
		
		boolean delete =false;
		
		int id=jsonObject.getInt("id");
		
		delete=masterService.deleteEmployeeDetailEntry(id);

		if (delete) {
			return "success";
		}
		return "failed";
	}
	
	@Path("/showEmployeeDetailsEntryList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<EmployeeMasterModel> showEmployeeDetailsEntryList() throws JSONException {
		
		List<EmployeeMasterModel> masterModel=new ArrayList<EmployeeMasterModel>();
		
		masterModel=masterService.showEmployeeDetailsEntryList();
		
		return masterModel;
	}
	
	@Path("/updateEmployeeDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject updateEmployeeDetails(JSONObject jsonObject) throws JSONException, ParseException {
				
		JSONObject json=new JSONObject();
		UserDetailsModel userModel=new UserDetailsModel();
				
		masterModel.setId(jsonObject.getInt("id"));
			
		userModel.setId(jsonObject.getInt("userId"));
		masterModel.setEmployee(userModel);
		masterModel.setEmployeeId(jsonObject.getString("employeeId"));
		masterModel.setFirstName(jsonObject.getString("firstName"));
		masterModel.setLastName(jsonObject.getString("lastName"));
				
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String joiningdateInString = jsonObject.getString("joiningDate");
		Date joiningDate = sdf.parse(joiningdateInString);
		
		masterModel.setJoiningDate(joiningDate);
		
		String expStartdateInString = jsonObject.getString("expStartDate");
		Date expStartDate = sdf.parse(expStartdateInString);
		
		masterModel.setExpStartDate(expStartDate);
				
		masterModel.setYearsOfExperience(jsonObject.getInt("yearsOfExp"));
		
		masterModel.setMonthsOfExperience(jsonObject.getInt("monthsOfExp"));
				
		String asOndateInString = jsonObject.getString("asOnDate");
		Date asOnDate = sdf.parse(asOndateInString);
		
		masterModel.setAsOnDate(asOnDate);
		
		masterModel.setLevel(jsonObject.getInt("level"));
		
		if(jsonObject.has("lobId")){
		LobModel lobModel=new LobModel();
		lobModel.setId(jsonObject.getInt("lobId"));
		masterModel.setLob(lobModel);
		}
				
		masterModel.setBillable(jsonObject.getString("billable"));
		
		boolean update=false;
		
		update=masterService.updateEmployeeDetails(masterModel);
		
		if(update){
			json.put("value", "success");
		}else{
			json.put("value", "error");
		}
		return json;
	}
	
    @Path("/findEmployeeExpAndUpdate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject findEmployeeExpAndUpdate(JSONObject jsonObject) throws JSONException, ParseException {
    
    	JSONObject json=new JSONObject();
    	LevelMasterModel levelModel=new LevelMasterModel();
    	UserDetailsModel userModel=new UserDetailsModel();
    	
    	int yearsOfExp=jsonObject.getInt("yearsOfExp");
    	
    	levelModel=masterService.findEmployeeLevel(yearsOfExp);	    
	   
		int level=levelModel.getLevel();
		
		masterModel.setId(jsonObject.getInt("id"));
		
		userModel.setId(jsonObject.getInt("userId"));
		masterModel.setEmployee(userModel);
		masterModel.setEmployeeId(jsonObject.getString("employeeId"));
		masterModel.setFirstName(jsonObject.getString("firstName"));
		masterModel.setLastName(jsonObject.getString("lastName"));
				
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String joiningdateInString = jsonObject.getString("joiningDate");
		Date joiningDate = sdf.parse(joiningdateInString);
		
		masterModel.setJoiningDate(joiningDate);
		
		String expStartdateInString = jsonObject.getString("expStartDate");
		Date expStartDate = sdf.parse(expStartdateInString);
		
		masterModel.setExpStartDate(expStartDate);
				
		masterModel.setYearsOfExperience(jsonObject.getInt("yearsOfExp"));
		
		masterModel.setMonthsOfExperience(jsonObject.getInt("monthsOfExp"));
				
		String asOndateInString = jsonObject.getString("asOnDate");
		Date asOnDate = sdf.parse(asOndateInString);
		
		masterModel.setAsOnDate(asOnDate);
		
		masterModel.setLevel(level);
		
		LobModel lobModel=new LobModel();
		lobModel.setId(jsonObject.getInt("lobId"));
		masterModel.setLob(lobModel);
				
		masterModel.setBillable(jsonObject.getString("billable"));
		
		boolean update=false;
		
		update=masterService.updateEmployeeDetails(masterModel);
		
		if(update){
			json.put("value", "success");
		}else{
			json.put("value", "error");
		}
		return json;
	}
    
    @Path("/employeeIdValidation")
   	@POST
   	@Consumes(MediaType.APPLICATION_JSON)
   	@Produces(MediaType.APPLICATION_JSON)
   	
   	public JSONObject employeeIdValidation(JSONObject jsonObject) throws JSONException, ParseException {
    	
    	JSONObject json=new JSONObject();
 
    	String employeeId=jsonObject.getString("employeeId");
    	
    	boolean empId=false;
    	
    	empId=masterService.employeeIdValidation(employeeId,empId);
    	
    	if(empId){
    		json.put("value", "already exist");
    	}else{
    		json.put("value", "new");
    	}
     	return json;
     }
    
    @Path("/employeeValidation")
   	@POST
   	@Consumes(MediaType.APPLICATION_JSON)
   	@Produces(MediaType.APPLICATION_JSON)
   	
   	public JSONObject employeeValidation(JSONObject jsonObject) throws JSONException, ParseException {
    	
    	JSONObject json=new JSONObject();
 
    	int userId=jsonObject.getInt("userId");
    	
    	boolean empId=false;
    	
    	empId=masterService.employeeValidation(userId,empId);
    	
    	if(empId){
    		json.put("value", "already exist");
    	}else{
    		json.put("value", "new");
    	}
     	return json;
     }
    
    
    	@Path("/employeeReport")
    	@GET
    	@Consumes(MediaType.APPLICATION_JSON)
    	@Produces(MediaType.APPLICATION_JSON)
    	
    	public JSONObject employeeReport() throws Exception {
    	
    	
    	HttpSession session= request.getSession(true);
    
  	if(session.getAttribute("userId")==null){
    		return null;
    	}
    	Object userId = session.getAttribute("userId");
    
    	int employeeId =(Integer) request.getSession().getAttribute("userId");
    
    	
    	JSONObject jsonObject = new JSONObject();
    	
    	
    	
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
    	
    	jsonObject.put("report","report successful");
    	jsonObject.put("pdfFileName", "employeeDetailsReport"+employeeId+".pdf");
    	
    	
    	return jsonObject;
    }
    }
 