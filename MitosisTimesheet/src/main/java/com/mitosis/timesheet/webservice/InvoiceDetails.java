package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
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

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.service.InvoiceDetailsService;
import com.mitosis.timesheet.service.impl.InvoiceDetailsServiceImpl;


@Path("invoiceDetails")
public class InvoiceDetails {
	
	InvoiceDetailsService InvoiceService = new InvoiceDetailsServiceImpl();


	@Path("/insertInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject insertInvoice(JSONObject jsonObject) throws JSONException, ParseException{

		
	
		
		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();

		InvoiceDetailsModel invoiceDetailsModel =  new InvoiceDetailsModel();

		JSONObject json = new JSONObject();
		Date todaysdate = new Date();


		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceDateString = jsonObject.getString("invoicedate");
 		Date invoiceDate = sdf.parse(InvoiceDateString);

		String invoiceNumber = InvoiceService.getInvoiceNumber();

		if(invoiceNumber == null){

			invoiceNumber ="MIT-"+1;

			invoiceHdrModel.setInvoiceNumber(invoiceNumber);


		}
		else{
			int nextNumber =1;
			String[] invoiceNumbersplit= invoiceNumber.split("_");
			nextNumber += Integer.parseInt(invoiceNumbersplit[1]);
			invoiceHdrModel.setInvoiceNumber(invoiceNumbersplit[0]+"_"+nextNumber);

		}

		int  invoiceamount =jsonObject.getInt("invoiceamt");


		BigDecimal invoiceAmt = new BigDecimal(invoiceamount);

		ProjectModel projectModel = new ProjectModel();
		projectModel.setProjectId(jsonObject.getInt("projectid"));
		CustomerDetailsModel customerModel = new CustomerDetailsModel();
		customerModel.setCustomerId(jsonObject.getInt("customerid"));

		invoiceHdrModel.setInvoiceDate(invoiceDate);
		invoiceHdrModel.setInvoiceAmount(invoiceAmt);
		invoiceHdrModel.setProject(projectModel);
		invoiceHdrModel.setCustomer(customerModel);
		invoiceHdrModel.setProjectType(jsonObject.getString("projecttype"));
		invoiceHdrModel.setCreatedDate(todaysdate);
	


		DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceFromDateString= jsonObject.getString("invoicefromdate");
		Date invoiceFromDate = sdf1.parse(InvoiceFromDateString);


		DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceToDateString= jsonObject.getString("invoicetodate");
		Date invoiceToDate = sdf2.parse(InvoiceToDateString);

		/*BigDecimal rateperhour = new BigDecimal(jsonObject.getInt("rateperhour"));*/

		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		invoicehdrModel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
		
		
		invoiceDetailsModel.setInvoice(invoicehdrModel);
		invoiceDetailsModel.setInvoiceFromDate(invoiceFromDate);
		invoiceDetailsModel.setInvoiceToDate(invoiceToDate);
		invoiceDetailsModel.setDescription(jsonObject.getString("description"));
/*		invoiceDetailsModel.setRatePerHour(rateperhour);*/
		invoiceDetailsModel.setTeamMember(jsonObject.getString("teammember"));
		invoiceDetailsModel.setBillableHours(jsonObject.getInt("billablehours"));
		
		System.out.println(invoiceDetailsModel);


		boolean validationforhdr ;
		validationforhdr=InvoiceService.validateEntry(invoiceHdrModel);

		if(validationforhdr){
			json.put("value", "already exist");
			return json;
		}
		else{
			boolean inserthdr = InvoiceService.create(invoiceHdrModel);
			boolean insertdtl = InvoiceService.create(invoiceDetailsModel);
			
			if(inserthdr && insertdtl ){

			json.put("value", "Inserted Successfully");
			return json;
			}

		}
		return json;





	}
	
	
	@Path("/updateInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject updateInvoice(JSONObject jsonObject) throws JSONException, ParseException{

		InvoiceDetailsService InvoiceService = new InvoiceDetailsServiceImpl();

		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();

		InvoiceDetailsModel invoiceDetailsModel =  new InvoiceDetailsModel();

		JSONObject json = new JSONObject();
		Date todaysdate = new Date();

		
		String invoiceNumber = jsonObject.getString("invoiceNumber");
		invoiceHdrModel.setInvoiceNumber(invoiceNumber);
		
		int id =0;
		
		id = InvoiceService.getId(invoiceNumber);
		
		
		if(jsonObject.has("invoicedate")){

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceDateString = jsonObject.getString("invoicedate");
		Date invoiceDate = sdf.parse(InvoiceDateString);
		invoiceHdrModel.setInvoiceDate(invoiceDate);
		}
		if(jsonObject.has("projectid")){
			
			ProjectModel projectModel = new ProjectModel();
			projectModel.setProjectId(jsonObject.getInt("projectid"));
			invoiceHdrModel.setProject(projectModel);
		}
		
		if(jsonObject.has("customerid")){
			
			CustomerDetailsModel customerModel = new CustomerDetailsModel();
			customerModel.setCustomerId(jsonObject.getInt("customerid"));
			invoiceHdrModel.setCustomer(customerModel);
			
		}
		
		if(jsonObject.has("invoiceamount")){
			
			BigDecimal invoiceAmt = new BigDecimal(jsonObject.getInt("invoiceamount"));
			invoiceHdrModel.setInvoiceAmount(invoiceAmt);
		}
		if(jsonObject.has("projecttype")){
			
			invoiceHdrModel.setProjectType(jsonObject.getString("projecttype"));
		
		}
		

			invoiceHdrModel.setCreatedDate(todaysdate);
	

		if(jsonObject.has("invoicefromdate")){
		
			DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			String InvoiceFromDateString= jsonObject.getString("invoicefromdate");
			Date invoiceFromDate = sdf1.parse(InvoiceFromDateString);
			invoiceDetailsModel.setInvoiceFromDate(invoiceFromDate);
			
		}
		
		if(jsonObject.has("invoicetodate")){
			
			DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
			String InvoiceToDateString= jsonObject.getString("invoicetodate");
			Date invoiceToDate = sdf2.parse(InvoiceToDateString);
			invoiceDetailsModel.setInvoiceToDate(invoiceToDate);
			
			
		}
		
		if(jsonObject.has("rateperhour")){
			
			
			BigDecimal rateperhour = new BigDecimal(jsonObject.getInt("rateperhour"));
			invoiceDetailsModel.setRatePerHour(rateperhour);
		}
		
		if(jsonObject.has("description")){
			
			
			invoiceDetailsModel.setDescription(jsonObject.getString("description"));
		}
		
		if(jsonObject.has("teammember")){
			
			invoiceDetailsModel.setTeamMember(jsonObject.getString("teammember"));
				
		}
		
		if(jsonObject.has("billablehours")){
			
			invoiceDetailsModel.setBillableHours(jsonObject.getInt("billablehours"));
			
		}
		
		
		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		invoicehdrModel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
		invoiceDetailsModel.setId(id);
		invoiceDetailsModel.setInvoice(invoicehdrModel);
		
		
			boolean inserthdr = InvoiceService.create(invoiceHdrModel);
			boolean insertdtl = InvoiceService.create(invoiceDetailsModel);
			
			if(inserthdr && insertdtl ){

			json.put("value", "updated Successfully");
			return json;
			}
			else{
				json.put("value", "updationfailed ");
				return json;
			}

		
	}
	
	
	
	@Path("/getProjectList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<ProjectModel> getProjectList() throws JSONException, ParseException{
		

		
		List<ProjectModel> projectList = new ArrayList<ProjectModel>();
	
		projectList = InvoiceService.getProjectList();
		
		
		
		return projectList;

	
	
	}
	
	
	@Path("/getCustomerList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDetailsModel> getCustomerList() throws JSONException, ParseException{
		

		
		List<CustomerDetailsModel> customerList = new ArrayList<CustomerDetailsModel>();
	
		customerList = InvoiceService.getCustomerList();
		
		
		
		return customerList;	
	
	
	
	}
	
	
	@Path("/getProjectTypeList")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectCostHdrModel> getProjectTypeList(JSONObject jsonObject) throws JSONException, ParseException{
		
		int id = jsonObject.getInt("projectId");
		
		List<ProjectCostHdrModel> projectCostHdrList = new ArrayList<ProjectCostHdrModel>();
	
		projectCostHdrList = InvoiceService.getProjectCostHdrList(id);
		
		
		
		return projectCostHdrList;	
	
		
	
	}
	
	

	@Path("/getTeamMembers")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TeamAssignmentModel> getTeamMembers() throws JSONException, ParseException{
		

		
		List<TeamAssignmentModel> TeamAssignmentList = new ArrayList<TeamAssignmentModel>();
	
		TeamAssignmentList = InvoiceService.getTeamList();
		
		
		
		return TeamAssignmentList;	
	
	
	
	}
	
	
	@Path("/deleteInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject deleteInvoice(JSONObject jsonObject) throws JSONException, ParseException{
		
		
		
		
		
		
		return jsonObject;

	
	
	}
}
