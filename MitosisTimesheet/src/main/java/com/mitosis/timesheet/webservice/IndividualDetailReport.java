package com.mitosis.timesheet.webservice;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.service.IndividualReportService;

import com.mitosis.timesheet.service.impl.IndividualReportServiceImpl;



@Path("individualreport")
public class IndividualDetailReport {
	
	IndividualReportService individualReportService  = new IndividualReportServiceImpl(); 
		
		
	
	
	@Context private HttpServletRequest request;
	
	
	@Path("/detailreport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getIndividualDetailReport(JSONObject jsonObject) throws JSONException, JRException, IOException{
		
		
		JSONObject jsonObj = new JSONObject();
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		int employeeId =(Integer) request.getSession().getAttribute("userId");
				
		String name = jsonObject.getString("name");
		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		
		Date fromdate = Date.valueOf(jsonObject.getString("fromdate"));
		
		Date todate = Date.valueOf(jsonObject.getString("todate"));
		
		
		timeSheetDetailReport = individualReportService.getIndividualReport(fromdate, todate, employeeId);
		
		
		JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
		          .getRealPath("/")
		          + "reports/individualDetailReport.jrxml");

		      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		     // JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
		      Map<String, Object> parameters = new HashMap<String, Object>();
		      
		      parameters.put("fromDate", fromdate);
		      parameters.put("ToDate", todate);
		      parameters.put("name",name);
		      
		      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(timeSheetDetailReport);
		      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		      String path = this.getClass().getClassLoader().getResource("/").getPath();
		      String pdfPath = path.replaceAll("WEB-INF/classes/", "");

		      JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath
		          + "reports/individualDetailReport" + employeeId + ".pdf");

		      
		      jsonObj.put("pdfFileName","individualDetailReport"+employeeId+".pdf");
		      jsonObj.put("pdfPath",pdfPath+ "reports/individualDetailReport.jrxml" + employeeId + ".pdf");
		   
		
		return jsonObj;

	}
	
	
	@Path("/summaryreport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getIndividualSummaryReport(JSONObject jsonObject) throws JSONException, JRException{
		
		
		
		JSONObject jsonObj = new JSONObject();
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		
		Object userId = session.getAttribute("userId");
		
		int employeeId =(Integer) request.getSession().getAttribute("userId");
				
		String name = jsonObject.getString("name");
		
		List<TimeSheetModel> timeSheetSummaryReport = new ArrayList<TimeSheetModel>();
		
		Date fromdate = Date.valueOf(jsonObject.getString("fromdate"));
		
		Date todate = Date.valueOf(jsonObject.getString("todate"));
		
		double totalhours;
		

		timeSheetSummaryReport = individualReportService.getIndividualReport(fromdate, todate, employeeId);
		
		
		
		totalhours = individualReportService.getTotalHours(fromdate, todate, employeeId);
		 
		
		
		
		
		JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
		          .getRealPath("/")
		          + "reports/individualSummaryReport.jrxml");

		      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		     // JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
		      Map<String, Object> parameters = new HashMap<String, Object>();
		      
		      parameters.put("fromDate", fromdate);
		      parameters.put("ToDate", todate);
		      parameters.put("name",name);
		      parameters.put("totalhours",totalhours);
		      
		      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(timeSheetSummaryReport);
		      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		      String path = this.getClass().getClassLoader().getResource("/").getPath();
		      String pdfPath = path.replaceAll("WEB-INF/classes/", "");

		      JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath
		          + "reports/individualSummaryReport" + employeeId + ".pdf");

		      
		      jsonObj.put("pdfFileName","individualSummaryReport"+employeeId+".pdf");
		      jsonObj.put("pdfPath",pdfPath+ "reports/individualSummaryReport.jrxml" + employeeId + ".pdf");
		   
		   
		
		return jsonObj;
		
		
		
		
		
		
		
	}
	
	
	@Path("/deletepdffile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deletepdffile(JSONObject jsonObject) throws JSONException{
		JSONObject jsonobject = new JSONObject();
		
		File file = new File(jsonObject.getString("filepath"));
		
		if(file.exists()){
			
			file.delete();
			
			jsonobject.put("msg", "deleted");
			
		}
		
		else{
			
			jsonobject.put("msg", "doesntexist");
			
			
		}
		

		return jsonobject;
		
		
		
		
		
		
	}
	
	
	
	

}
