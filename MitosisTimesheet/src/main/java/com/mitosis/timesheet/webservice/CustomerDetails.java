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

import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.service.CustomerDetailsService;
import com.mitosis.timesheet.service.impl.CustomerDetailsServiceImpl;


@Path("customerDetails")
public class CustomerDetails {

	CustomerDetailsService customerService=new CustomerDetailsServiceImpl();
	CustomerDetailsModel customerModel=new CustomerDetailsModel();
	JSONObject json=new JSONObject();

	@Path("/addCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject addCustomerDetails(JSONObject jsonObject) throws JSONException {

		boolean insert=false;

		if (jsonObject.has("customerName")) {
			customerModel.setCustomerName(jsonObject.getString("customerName"));
		}
		if (jsonObject.has("email")) {
			customerModel.setEmail(jsonObject.getString("email"));
		}
		if (jsonObject.has("address")) {
			customerModel.setAddress(jsonObject.getString("address"));
		}
		if (jsonObject.has("skypeId")) {
			customerModel.setSkypeId(jsonObject.getString("skypeId"));
		}
		if (jsonObject.has("phone")) {
			customerModel.setPhone(jsonObject.getInt("phone"));
		}
		if (jsonObject.has("mobile")) {
			customerModel.setMobile(jsonObject.getInt("mobile"));
		}
		if (jsonObject.has("website")) {
			customerModel.setWebsite(jsonObject.getString("website"));
		}

		insert=customerService.addCustomerDetails(customerModel);

		if(insert){
			json.put("value", "inserted");
		}else{
			json.put("value", "error");
		}
		return json;
	}

	@Path("/updateCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject updateCustomerDetails(JSONObject jsonObject) throws JSONException ,Exception{

		boolean update;

		if (jsonObject.has("customerId")) {
			customerModel.setCustomerId(jsonObject.getInt("customerId"));
		} 
		if (jsonObject.has("customerName")) {
			customerModel.setCustomerName(jsonObject.getString("customerName"));
		}
		if (jsonObject.has("email")) {
			customerModel.setEmail(jsonObject.getString("email"));
		}
		if (jsonObject.has("address")) {
			customerModel.setAddress(jsonObject.getString("address"));
		}
		if (jsonObject.has("skypeId")) {
			customerModel.setSkypeId(jsonObject.getString("skypeId"));
		}
		if (jsonObject.has("phone")) {
			customerModel.setPhone(jsonObject.getInt("phone"));
		}
		if (jsonObject.has("mobile")) {
			customerModel.setMobile(jsonObject.getInt("mobile"));
		}
		if (jsonObject.has("website")) {
			customerModel.setWebsite(jsonObject.getString("website"));
		}

		update = customerService.updateCustomerDetails(customerModel);

		if(update){
			json.put("value", "updated");
		}else{
			json.put("value", "error");
		}
		return json;

	}

	@Path("/removeCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject removeCustomerDetail(JSONObject jsonObject) throws JSONException{

		boolean delete;

		int customerId=jsonObject.getInt("customerId");

		delete = customerService.removeCustomerById(customerId);
		if (delete) {
			json.put("value", "deleted");
		}else{
			json.put("value", "error");
		}
		return json;
	}


	@Path("/showCustomerlist")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public List<CustomerDetailsModel> showCustomerlist() throws JSONException, ParseException {

		List<CustomerDetailsModel> customerlist = new ArrayList<CustomerDetailsModel>();

		customerlist = customerService.showCustomerlist();

		return customerlist;

	}
	
	@Path("/nameValidation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject nameValidation(JSONObject jsonObject) throws JSONException{

		boolean validation=false;

		String name=jsonObject.getString("name");

		validation=customerService.nameValidation(name);

		if(validation){
			json.put("value", "Already Exist");
		}else{
			json.put("value", "Not Exist");
		}
		return json;
	}

	@Path("/mailValidation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject mailValidation(JSONObject jsonObject) throws JSONException{

		boolean validation=false;

		String mail=jsonObject.getString("mail");

		validation=customerService.mailValidation(mail);

		if(validation){
			json.put("value", "Already Exist");
		}else{
			json.put("value", "Not Exist");
		}
		return json;
	}
	
}
