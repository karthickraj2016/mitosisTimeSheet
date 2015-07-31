package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
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

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.service.LevelMasterService;
import com.mitosis.timesheet.service.impl.LevelMasterServiceImpl;

@Path("levelMaster")
public class LevelMaster {
	
	LevelMasterService levelService=new LevelMasterServiceImpl();
	
	@Path("/addLevelDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject addLevelDetails(JSONObject jsonObject) throws JSONException, ParseException {
		
		JSONObject json=new JSONObject();
		
		LevelMasterModel levelModel=new LevelMasterModel();
		
		levelModel.setYearFrom(jsonObject.getInt("yearFrom"));
		levelModel.setYearTo(jsonObject.getInt("yearTo"));
		levelModel.setLevel(jsonObject.getInt("level"));
		int rate=jsonObject.getInt("ratePerHour");
		BigDecimal rateperHour=new BigDecimal(rate);
		levelModel.setRatePerHour(rateperHour);
		levelModel.setHoursPerMonth(jsonObject.getInt("hoursPerMonth"));
				
        boolean insert=false;
		
		insert=levelService.addLevelDetails(levelModel);
		
		if(insert){
			json.put("value", "success");
		}else{
			json.put("value", "error");
		}
		return json;
	}
	
	@Path("/updateLevelDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject updateLevelDetails(JSONObject jsonObject) throws JSONException, ParseException {
		
		JSONObject json=new JSONObject();
		
		LevelMasterModel levelModel=new LevelMasterModel();
		
		levelModel.setId(jsonObject.getInt("id"));
		levelModel.setYearFrom(jsonObject.getInt("yearFrom"));
		levelModel.setYearTo(jsonObject.getInt("yearTo"));
		levelModel.setLevel(jsonObject.getInt("level"));
		int rate=jsonObject.getInt("ratePerHour");
		BigDecimal rateperHour=new BigDecimal(rate);
		levelModel.setRatePerHour(rateperHour);
		levelModel.setHoursPerMonth(jsonObject.getInt("hoursPerMonth"));
		levelModel.setNumberOfEmployees(jsonObject.getInt("numberOfEmployees"));
		int amount=jsonObject.getInt("totalAmount");
		BigDecimal totalAmount=new BigDecimal(amount);
		levelModel.setTotalAmount(totalAmount);
		int amountInr=jsonObject.getInt("totalAmountINR");
		BigDecimal totalAmountINR=new BigDecimal(amountInr);
		levelModel.setTotalAmountINR(totalAmountINR);
        
		boolean update=false;
		
		update=levelService.addLevelDetails(levelModel);
		
		if(update){
			json.put("value", "success");
		}else{
			json.put("value", "error");
		}
		return json;
	}
	
	
	@Path("/deleteLevelDetailEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String deleteLevelDetailEntry(JSONObject jsonObject) throws JSONException {
		
		boolean delete =false;
		
		int id=jsonObject.getInt("id");
		
		delete=levelService.deleteLevelDetailEntry(id);

		if (delete) {
			return "success";
		}
		return "failed";
	}
	
	@Path("/showlevelDetails")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<LevelMasterModel> showLevelDetails() throws JSONException {
		
		List<LevelMasterModel> levelModel=new ArrayList<LevelMasterModel>();
		
		levelModel=levelService.showLevelDetails();
		
		return levelModel;
	}
	
	
	@Path("/calculateAndUpdateEstimation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject calculateAndUpdateEstimation(JSONObject jsonObject) throws JSONException{
		
		JSONObject json=new JSONObject();
		
		LevelMasterModel levelModel=new LevelMasterModel();
		
		int level=jsonObject.getInt("level");
		
		int numberOfEmployees=levelService.findCountOfEmpPerLevel(level);
	
		System.out.println(numberOfEmployees);
		
		int amount=jsonObject.getInt("amount");
		int totAmount=amount*numberOfEmployees;
		int inrRate=jsonObject.getInt("inrRate");

		int totAmountInr=totAmount*inrRate; 
		
		BigDecimal totalAmount=new BigDecimal(totAmount);
		BigDecimal totalAmountINR=new BigDecimal(totAmountInr);		
		
		levelModel.setId(jsonObject.getInt("id"));
		levelModel.setYearFrom(jsonObject.getInt("yearFrom"));
		levelModel.setYearTo(jsonObject.getInt("yearTo"));
		levelModel.setLevel(jsonObject.getInt("level"));
		int rate=jsonObject.getInt("ratePerHour");
		BigDecimal rateperHour=new BigDecimal(rate);
		levelModel.setRatePerHour(rateperHour);
		levelModel.setHoursPerMonth(jsonObject.getInt("hoursPerMonth"));
		levelModel.setNumberOfEmployees(numberOfEmployees);
		levelModel.setTotalAmount(totalAmount);
		levelModel.setTotalAmountINR(totalAmountINR);
		
        boolean update=false;
		
		update=levelService.addLevelDetails(levelModel);
		
		if(update){
			json.put("value", "success");
		}else{
			json.put("value", "error");
		}
		return json;
	}
	
	@Path("/getEmployeesByLevel")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<EmployeeMasterModel> getEmployeesByLevel(JSONObject jsonObject)throws JSONException{
		
		List<EmployeeMasterModel> levelModel=new ArrayList<EmployeeMasterModel>();
		
		int level=jsonObject.getInt("level");
		
		levelModel=levelService.getEmployeesByLevel(level);
		
		return levelModel;
	}
}
