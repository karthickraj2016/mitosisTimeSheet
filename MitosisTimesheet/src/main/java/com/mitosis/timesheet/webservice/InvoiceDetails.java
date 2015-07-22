package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.service.InvoiceDetailsService;
import com.mitosis.timesheet.service.impl.InvoiceDetailsServiceImpl;


@Path("invoiceDetails")
public class InvoiceDetails {


	@Path("/insertInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject insertInvoice(JSONObject jsonObject) throws JSONException, ParseException{

		InvoiceDetailsService InvoiceService = new InvoiceDetailsServiceImpl();

		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();

		InvoiceDetailsModel invoiceDetailsModel =  new InvoiceDetailsModel();

		JSONObject json = new JSONObject();
		Date todaysdate = new Date();


		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceDateString = jsonObject.getString("invoicedate");
 		Date invoiceDate = sdf.parse(InvoiceDateString);

		String invoiceNumber = InvoiceService.getInvoiceNumber();

		if(invoiceNumber == null){

			invoiceNumber ="Mit_"+1;

			invoiceHdrModel.setInvoiceNumber(invoiceNumber);


		}
		else{
			int nextNumber =1;
			String[] invoiceNumbersplit= invoiceNumber.split("_");
			nextNumber += Integer.parseInt(invoiceNumbersplit[1]);
			invoiceHdrModel.setInvoiceNumber(invoiceNumbersplit[0]+"_"+nextNumber);

		}

		int  invoiceamount =jsonObject.getInt("invoiceamount");


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
		
		System.out.println(invoiceHdrModel);


		/*DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceFromDateString= jsonObject.getString("invoicefromdate");
		Date invoiceFromDate = sdf1.parse(InvoiceFromDateString);


		DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceToDateString= jsonObject.getString("invoicetodate");
		Date invoiceToDate = sdf2.parse(InvoiceToDateString);

		BigDecimal rateperhour = new BigDecimal(jsonObject.getInt("rateperhour"));

		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		invoicehdrModel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
		
		
		invoiceDetailsModel.setInvoice(invoicehdrModel);
		invoiceDetailsModel.setInvoiceFromDate(invoiceFromDate);
		invoiceDetailsModel.setInvoiceToDate(invoiceToDate);
		invoiceDetailsModel.setDescription(json.getString("description"));
		invoiceDetailsModel.setRatePerHour(rateperhour);
		invoiceDetailsModel.setTeamMember(jsonObject.getString("teammember"));
		invoiceDetailsModel.setBillableHours(jsonObject.getInt("billablehours"));
		
		System.out.println(invoiceDetailsModel);*/


		boolean validationforhdr = false;
		validationforhdr=InvoiceService.validateEntry(invoiceHdrModel);

		if(validationforhdr){
			json.put("value", "already exist");
			return json;
		}
		else{
			boolean inserthdr = InvoiceService.create(invoiceHdrModel);
			/*boolean insertdtl = InvoiceService.create(invoiceDetailsModel);*/

			json.put("value", "Inserted Successfully");
			return json;

		}






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
		
		if(jsonObject.has("createddate")){
			
			invoiceHdrModel.setCreatedDate(todaysdate);
		}
		
		
		String invoiceNumber = InvoiceService.getInvoiceNumber();

		if(invoiceNumber == null){

			invoiceNumber ="Mit_"+1;

			invoiceHdrModel.setInvoiceNumber(invoiceNumber);


		}
		else{
			int nextNumber =1;
			String[] invoiceNumbersplit= invoiceNumber.split("_");
			nextNumber += Integer.parseInt(invoiceNumbersplit[1]);
			invoiceHdrModel.setInvoiceNumber(invoiceNumbersplit[0]+"_"+nextNumber);

		}



		
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
			
			
			invoiceDetailsModel.setDescription(json.getString("description"));
		}
		
		if(jsonObject.has("teammember")){
			
			invoiceDetailsModel.setTeamMember(jsonObject.getString("teammember"));
				
		}
		
		if(jsonObject.has("billablehours")){
			
			invoiceDetailsModel.setBillableHours(jsonObject.getInt("billablehours"));
			
		}

		

		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		invoicehdrModel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
		
		
		invoiceDetailsModel.setInvoice(invoicehdrModel);
		

		boolean validationforhdr = false;
		validationforhdr=InvoiceService.validateEntry(invoiceHdrModel);

		if(validationforhdr){
			json.put("value", "already exist");
			return json;
		}
		else{
			boolean inserthdr = InvoiceService.create(invoiceHdrModel);
			boolean insertdtl = InvoiceService.create(invoiceDetailsModel);

			json.put("value", "Inserted Successfully");
			return json;

		}
	}
	
	@Path("/deleteInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject deleteInvoice(JSONObject jsonObject) throws JSONException, ParseException{
		
		
		
		
		
		
		return jsonObject;

	
	
	}
}
