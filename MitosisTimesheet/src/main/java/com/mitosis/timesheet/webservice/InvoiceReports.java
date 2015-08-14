package com.mitosis.timesheet.webservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.util.JasperUtil;


@Path("invoiceReports")
public class InvoiceReports extends JasperUtil {
	
	
	@Context private HttpServletRequest request;
	
	@Path("pendingInvoiceReports")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public JSONObject pendingInvoiceReports(JSONObject jsonObject) throws Exception{
		
		
		HttpSession session= request.getSession(true);
	    
	  	if(session.getAttribute("userId")==null){
	    		return null;
	    	}
	    	Object userId = session.getAttribute("userId");
	    
	    	int employeeId =(Integer) request.getSession().getAttribute("userId");
	    	
	    	
	    	DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String firstdayString = jsonObject.getString("firstday");
			Date firstday = sdf.parse(firstdayString);
			
			DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			String lastdayString = jsonObject.getString("lastday");
			Date lastday = sdf1.parse(lastdayString);

	    	
	    	JSONObject jsonobject = new JSONObject();
	    
	    	
	    	
	    	
	    	String reportFilePath = request.getSession().getServletContext()
	    			.getRealPath("/")
	    			+ "reports/pendingInvoiceReport.jrxml";
	    
	    
	    	Map<String, Object> parameters = new HashMap<String, Object>();
	    	String path = this.getClass().getClassLoader().getResource("/").getPath();
	    	String pdfPath = path.replaceAll("WEB-INF/classes/", "");
	    	String imagePath = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("WEB-INF/classes/", "");
	    	 parameters.put("firstday", firstdayString);
			 parameters.put("lastday",lastdayString);
	    	
	    	
	    	String pdfFilePath = pdfPath
	    			+ "reports/pendingInvoiceReport" + employeeId + ".pdf";
	    	
	    	RenderJr(reportFilePath, parameters,pdfFilePath);
	    	
	    
	    	JRBeanCollectionDataSource ds = null;
	    	
	    	jsonobject.put("report","report successful");
	    	jsonobject.put("pdfFileName", "pendingInvoiceReport"+employeeId+".pdf");
	    	
	    	
	    	return jsonobject;
	
	
		
		
		
		
	}
	
	
	@Path("pendingBalanceReports")
	@GET
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject pendingBalanceReports() throws Exception{
		JSONObject jsonObject = new JSONObject();
		
		
		HttpSession session= request.getSession(true);
	    
	  	if(session.getAttribute("userId")==null){
	    		return null;
	    	}
	    	Object userId = session.getAttribute("userId");
	    
	    	int employeeId =(Integer) request.getSession().getAttribute("userId");
	    	
	    	

	    	String reportFilePath = request.getSession().getServletContext()
	    			.getRealPath("/")
	    			+ "reports/pendingBalanceReport.jrxml";
	    
	    
	    	Map<String, Object> parameters = new HashMap<String, Object>();
	    	String path = this.getClass().getClassLoader().getResource("/").getPath();
	    	String pdfPath = path.replaceAll("WEB-INF/classes/", "");
	    	String imagePath = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("WEB-INF/classes/", "");

	    	
	    	
	    	String pdfFilePath = pdfPath
	    			+ "reports/pendingBalanceReport" + employeeId + ".pdf";
	    	
	    	RenderJr(reportFilePath, parameters,pdfFilePath);
	    	
	    
	    	JRBeanCollectionDataSource ds = null;
	    	
	    	jsonObject.put("report","report successful");
	    	jsonObject.put("pdfFileName", "pendingBalanceReport"+employeeId+".pdf");
	    	
	    	
	    	return jsonObject;
	
	}
	

}
