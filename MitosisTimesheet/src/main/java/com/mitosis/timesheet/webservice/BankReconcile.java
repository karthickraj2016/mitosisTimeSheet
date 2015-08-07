package com.mitosis.timesheet.webservice;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.service.BankReconcileService;
import com.mitosis.timesheet.service.impl.BankReconcileServiceImpl;

@Path("/bankReconcile")
public class BankReconcile {
	
	BankReconcileService reconcileService=new BankReconcileServiceImpl();
	
	@Path("/getReceiptDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getReceiptDetails(JSONObject jsonObject) throws JSONException, ParseException {
		
		String ino=jsonObject.getString("invoiceNumber");
		return reconcileService.getReceiptDetails(ino);
	}
	

}
