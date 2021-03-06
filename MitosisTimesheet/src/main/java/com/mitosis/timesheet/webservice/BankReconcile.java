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

import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.service.BankReconcileService;
import com.mitosis.timesheet.service.impl.BankReconcileServiceImpl;

@Path("/bankReconcile")
public class BankReconcile {
	
	BankReconcileService reconcileService=new BankReconcileServiceImpl();
	
	@Path("/getPaymentDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerPaymentModel> getPaymentDetails(JSONObject jsonObject) throws JSONException, ParseException {
	
		
		String invoiceNumber=jsonObject.getString("invoiceNumber");
		
		List<CustomerPaymentModel> customerPayment = new ArrayList<CustomerPaymentModel>();
		
		customerPayment=reconcileService.getPaymentDetails(invoiceNumber);
		
		System.out.println(customerPayment);
		
		return customerPayment;
	}
	

	@Path("/getReceiptDetails")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerPaymentModel> getReceiptDetails() throws JSONException, ParseException {
	
		
		/*String receiptNumber=jsonObject.getString("receiptNumber");*/
		
		List<CustomerPaymentModel> customerPayment = new ArrayList<CustomerPaymentModel>();
		
		customerPayment=reconcileService.getReceiptDetails();
		
		System.out.println(customerPayment);
		
		return customerPayment;
	}
	
	
	@Path("/insertReconcile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject insertReconcile(JSONObject jsonObject) throws JSONException, ParseException {
		
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel();
		
		CustomerPaymentModel customerPayment = new CustomerPaymentModel();
		
		/*customerPayment=reconcileService.getReceiptDetails(jsonObject.getString("receiptNumber"));*/
    	
		
		InvoiceHdrModel invoiceHdr=new InvoiceHdrModel();
		
		JSONObject jsonobject = new JSONObject();
		
    	customerPaymentModel.setId(jsonObject.getInt("id"));
    	customerPaymentModel.setReceiptNumber(jsonObject.getString("receiptNumber"));
    	
    	BigDecimal receivedAmount=new BigDecimal(jsonObject.getString("receivedAmount"));
    	customerPaymentModel.setReceivedAmount(receivedAmount);
		
    	BigDecimal commisionAmount=new BigDecimal(jsonObject.getString("commisionAmount"));
    	customerPaymentModel.setCommisionAmount(commisionAmount);
    	
    	BigDecimal exchangeRate=new BigDecimal(jsonObject.getString("exchangeRate"));
    	customerPaymentModel.setExchangeRate(exchangeRate);
    	
    	BigDecimal paidAmount=new BigDecimal(jsonObject.getString("paidAmount"));
    	/*BigDecimal sum = paidAmount.add(customerPayment.getPaidAmount());*/
    	customerPaymentModel.setPaidAmount(paidAmount);
    	
    	BigDecimal paidAmountInr=new BigDecimal(jsonObject.getString("paidAmountInr"));
    	customerPaymentModel.setPaidAmountInr(paidAmountInr);
    	
    	DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date=sdf.parse(jsonObject.getString("receiptDate"));
		customerPaymentModel.setReceiptDate(date);
		
		Date date1=sdf.parse(jsonObject.getString("receivedDate"));
		customerPaymentModel.setBankReceivedDate(date1);
		
		customerPaymentModel.setCurrencyCode(jsonObject.getString("currencyCode"));
		
		String invoiceNum=jsonObject.getString("invoiceNumber");
    	
		invoiceHdr=reconcileService.getInvoiceHdrDetails(invoiceNum);
    	
    	customerPaymentModel.setInvoiceHdr(invoiceHdr);
    	
    	boolean insert = reconcileService.insert(customerPaymentModel);
		
		if(insert){
			jsonobject.put("value", "inserted");
				
		}else{
			
			jsonobject.put("value", "insertion failed");
		}
		
		return jsonobject;
	}
	
}
