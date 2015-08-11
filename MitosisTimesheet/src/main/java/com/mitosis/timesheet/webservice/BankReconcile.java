package com.mitosis.timesheet.webservice;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
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
		
		System.out.println(jsonObject);
		
		String invoiceNumber=jsonObject.getString("invoiceNumber");
		
		List<CustomerPaymentModel> customerPayment = new ArrayList<CustomerPaymentModel>();
		
		customerPayment=reconcileService.getPaymentDetails(invoiceNumber);
		
		System.out.println(customerPayment);
		
		return customerPayment;
	}
	

	@Path("/getReceiptDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerPaymentModel getReceiptDetails(JSONObject jsonObject) throws JSONException, ParseException {
		
		System.out.println(jsonObject);
		
		String receiptNumber=jsonObject.getString("receiptNumber");
		
		CustomerPaymentModel customerPayment = new CustomerPaymentModel();
		
		customerPayment=reconcileService.getReceiptDetails(receiptNumber);
		
		System.out.println(customerPayment);
		
		return customerPayment;
	}
	

	@Path("/insertReconcile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject insertReconcile(JSONObject jsonObject) throws JSONException, ParseException {
		
		CustomerPaymentModel customerPaymentModel = new CustomerPaymentModel();
		
		InvoiceHdrModel invoiceHdrmodel = new InvoiceHdrModel();
		
		JSONObject jsonobject = new JSONObject();
		
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String receiptStrDate = jsonObject.getString("receiptdate");
		Date receiptDate = sdf.parse(receiptStrDate);

		customerPaymentModel.setReceiptNumber(jsonObject.getString("receiptNumber"));
		CustomerPaymentModel customerPaymentmodel = new CustomerPaymentModel();
		customerPaymentmodel=reconcileService.getCustomerDetail(jsonObject.getString("receiptNumber"));
		invoiceHdrmodel=reconcileService.getInvoiceDetails(jsonObject.getString("invoiceNumber"));
		
		BigDecimal exchangeRate = new BigDecimal(jsonObject.getInt("exchangerate"));
		customerPaymentModel.setId(customerPaymentmodel.getId());
		customerPaymentModel.setExchangeRate(exchangeRate);
		BigDecimal commissionamount = new BigDecimal(jsonObject.getInt("commisionamount"));
		customerPaymentModel.setCommisionAmount(commissionamount);
		BigDecimal recievedamount = new BigDecimal(jsonObject.getInt("receivedamount"));
		customerPaymentModel.setReceivedAmount(recievedamount);
		BigDecimal paidAmountInr = new BigDecimal(jsonObject.getInt("paidAmountInr"));
		customerPaymentModel.setPaidAmountInr(paidAmountInr);	
		int totalamountpaid = jsonObject.getInt("invoiceAmount")-jsonObject.getInt("paidAmount");
		BigDecimal paidamount = new BigDecimal(totalamountpaid);
		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();
		customerPaymentModel.setReceiptDate(receiptDate);
		
		invoiceHdrModel.setInvoiceNumber(jsonObject.getString("invoiceNumber"));
		
		invoiceHdrModel.setPaidAmount(paidamount);
		
		
		invoiceHdrModel.setProject(invoiceHdrmodel.getProject());
		
		invoiceHdrModel.setCustomer(invoiceHdrmodel.getCustomer());
		
		invoiceHdrModel.setInvoiceDate(invoiceHdrmodel.getInvoiceDate());
		
		invoiceHdrModel.setInvoiceAmount(invoiceHdrmodel.getInvoiceAmount());
		
		invoiceHdrModel.setInvoiceStatus(invoiceHdrmodel.getInvoiceStatus());
		
		invoiceHdrModel.setCreatedDate(invoiceHdrmodel.getCreatedDate());
		
		invoiceHdrModel.setProjectType(invoiceHdrmodel.getProjectType());
		
		invoiceHdrModel.setCurrencyCode(invoiceHdrmodel.getCurrencyCode());
		
		
		int balamount = jsonObject.getInt("invoiceAmount")-jsonObject.getInt("paidAmount");
		
		BigDecimal balanceamount = new BigDecimal(balamount);
		
		invoiceHdrModel.setBalanceAmount(balanceamount);
		customerPaymentModel.setInvoiceHdr(invoiceHdrModel);

		customerPaymentModel.setPaidAmount(paidamount);
		
		
		System.out.println(customerPaymentModel);
		
		boolean insert = reconcileService.insert(customerPaymentModel);
		
		if(insert){
			jsonobject.put("value", "inserted");
				
		}
		else{
			
			jsonobject.put("value", "insertion failed");
			
		}
		
		
		
		return jsonobject;
		
	}
	
	

}
