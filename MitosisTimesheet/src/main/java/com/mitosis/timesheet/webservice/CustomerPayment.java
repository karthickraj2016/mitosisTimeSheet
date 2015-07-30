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

import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.service.CustomerDetailsService;
import com.mitosis.timesheet.service.CustomerPaymentService;
import com.mitosis.timesheet.service.InvoiceDetailsService;
import com.mitosis.timesheet.service.ProjectService;
import com.mitosis.timesheet.service.impl.CustomerDetailsServiceImpl;
import com.mitosis.timesheet.service.impl.CustomerPaymentServiceImpl;
import com.mitosis.timesheet.service.impl.InvoiceDetailsServiceImpl;
import com.mitosis.timesheet.service.impl.ProjectServiceImpl;
@Path("payment")
public class CustomerPayment {
	
		CustomerPaymentModel payment=new CustomerPaymentModel();
		CustomerPaymentService paymentservice=new CustomerPaymentServiceImpl();
		InvoiceHdrModel i=new InvoiceHdrModel();
		@Path("/addPayment")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public boolean addCompany(JSONObject jsonObject) throws JSONException, ParseException{
			boolean flag=false;
			if(jsonObject.has("id")){
				payment.setId(jsonObject.getInt("id"));
			}
			DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date=sdf.parse(jsonObject.getString("receiptDate"));
			payment.setReceiptDate(date);
			String ino=jsonObject.getString("invoiceNumber");
			i.setInvoiceNumber(ino);
			payment.setInvoiceHdr(i);
			payment.setReceiptNumber(jsonObject.getString("receiptNumber"));
			payment.setPaidAmount(BigDecimal.valueOf(jsonObject.getDouble("paidAmount")));
			payment.setCurrencyCode(jsonObject.getString("currencyCode"));
			if(jsonObject.has("exchangeRate")){
			payment.setExchangeRate(BigDecimal.valueOf(jsonObject.getDouble("exchangeRate")));}
			if(jsonObject.has("paidAmountInr")){
			payment.setPaidAmountInr(BigDecimal.valueOf(jsonObject.getDouble("paidAmountInr")));}
			if(jsonObject.has("commisionAmount")){
			payment.setCommisionAmount(BigDecimal.valueOf(jsonObject.getDouble("commisionAmount")));}
			if(jsonObject.has("receivedAmount")){
			payment.setReceivedAmount(BigDecimal.valueOf(jsonObject.getDouble("receivedAmount")));}
			flag=paymentservice.save(payment);
			return flag;
	}
		@Path("/showPayment")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Object show(JSONObject j) throws JSONException, ParseException {
			int id=j.getInt("id");
			CustomerPaymentModel c=paymentservice.show(id);
			if(c==null)
			{
				JSONObject res=new JSONObject();
				res.put("result", "No record Found");
				return res;
			}
			return c;
		}
		@Path("/showPaymentlist")
		@GET
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<CustomerPaymentModel> showlist() throws JSONException, ParseException {
			List<CustomerPaymentModel> paymentlist = new ArrayList<CustomerPaymentModel>();
			paymentlist = paymentservice.showlist();
			return paymentlist;
	}
		@Path("/removePayment")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Object remove(JSONObject jsonObject) throws JSONException, ParseException {
			int companyId=jsonObject.getInt("id");
			boolean c=paymentservice.remove(companyId);
			return c;

		}
		@Path("/showCustomerlist")
		@GET
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)

		public List<CustomerDetailsModel> showCustomerlist() throws JSONException, ParseException {
			CustomerDetailsService customerService=new CustomerDetailsServiceImpl();
			List<CustomerDetailsModel> customerlist = new ArrayList<CustomerDetailsModel>();
			customerlist = customerService.showCustomerlist();
			return customerlist;

		}
		@Path("/projectList")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Object getProjects(JSONObject jsonObject) throws JSONException, ParseException {
			ProjectService p=new ProjectServiceImpl();
			int cusId=jsonObject.getInt("customerId");
			List<ProjectModel> projects=p.getProjectList(cusId);
			return projects;

		}
		@Path("/invoiceList")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Object getInvoices(JSONObject jsonObject) throws JSONException, ParseException {
			InvoiceDetailsService i=new InvoiceDetailsServiceImpl();
			int projectId=jsonObject.getInt("projectId");
			List<InvoiceHdrModel> invoiceList=i.getInvoiceList(projectId);
			return invoiceList;
		}
		@Path("/invoiceHdr")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Object getInvoiceHdr(JSONObject jsonObject) throws JSONException, ParseException {
			InvoiceDetailsService i=new InvoiceDetailsServiceImpl();
			String ino=jsonObject.getString("invoiceNumber");
			return i.getInvoiceHdr(ino);
		}
}
