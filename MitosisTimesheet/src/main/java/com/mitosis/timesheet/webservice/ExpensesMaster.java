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

import com.mitosis.timesheet.model.ExpenseMasterModel;
import com.mitosis.timesheet.service.ExpenseMasterService;
import com.mitosis.timesheet.service.impl.ExpenseMasterServiceImpl;


@Path("expenses")
public class ExpensesMaster {
	
	ExpenseMasterService expenseMasterService  = new ExpenseMasterServiceImpl();
	
	@Path("showList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExpenseMasterModel> showList(){
		
		
		List<ExpenseMasterModel> showList = new ArrayList<ExpenseMasterModel>();
		
		
		showList =expenseMasterService.showList();
		
		return showList;	
		
	}
	
	@Path("insertEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject insertEntry(JSONObject jsonobject) throws JSONException{
		
		JSONObject jsonObject = new JSONObject();
	
		ExpenseMasterModel expenseMasterModel = new ExpenseMasterModel();
		
		expenseMasterModel.setDescription(jsonobject.getString("expenseDescription"));
		expenseMasterModel.setPayingMode(jsonobject.getString("payingMode"));
		expenseMasterModel.setPeriod(jsonobject.getString("period"));
		
		boolean flag = expenseMasterService.insert(expenseMasterModel);
		
		
		if(flag){
			
			jsonObject.put("value", "inserted");
			
		}
		
		
		return jsonObject;
		
		
		
	}
	
	
	@Path("updateEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateEntry(JSONObject jsonobject) throws JSONException{
	
		
		JSONObject jsonObject = new JSONObject();
		
		ExpenseMasterModel expenseMasterModel = new ExpenseMasterModel();
		
		expenseMasterModel.setId(jsonobject.getInt("id"));
		expenseMasterModel.setDescription(jsonobject.getString("expenseDescription"));
		expenseMasterModel.setPayingMode(jsonobject.getString("payingMode"));
		expenseMasterModel.setPeriod(jsonobject.getString("period"));
		
		boolean flag = expenseMasterService.insert(expenseMasterModel);
		
		
		if(flag){
			
			jsonObject.put("value", "updated");
			
		}
		
		
		
		return jsonObject;

	}

	
	@Path("deleteEntry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deleteEntry(JSONObject jsonobject) throws JSONException{
		
		
		JSONObject jsonObject = new JSONObject();
		
		
		int id = jsonobject.getInt("id");
		
		
		
		boolean flag = expenseMasterService.delete(id);
		
		
		if(flag){
			
			
			jsonObject.put("value", "deleted");
			
			
		}
		
		
		return jsonObject;
		
	
	}


}
