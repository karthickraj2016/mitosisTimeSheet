package com.mitosis.timesheet.webservice;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;
import com.mitosis.timesheet.service.IndividualReportService;
import com.mitosis.timesheet.service.impl.IndividualReportServiceImpl;



@Path("individualreport")
public class IndividualReport {
	
	IndividualReportService individualReportService  = new IndividualReportServiceImpl(); 
		
		
	
	
	@Context private HttpServletRequest request;
	
	
	@Path("/detailreport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getIndividualDetailReport(JSONObject jsonObject) throws JSONException, JRException, IOException, ParseException{
		
		
		JSONObject jsonObj = new JSONObject();
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		int employeeId =(Integer) request.getSession().getAttribute("userId");
				
		String name = jsonObject.getString("name");
		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String frmdateInString = jsonObject.getString("fromdate");
		Date fromDate = sdf.parse(frmdateInString);
		
		String todateInString = jsonObject.getString("todate");
		
		Date toDate = sdf.parse(todateInString);
		
		
		timeSheetDetailReport = individualReportService.getIndividualReport(fromDate, toDate, employeeId);
		
		
		if(timeSheetDetailReport.size()==0){
			
			
			jsonObj.put("pdfPath","norecords");
			
			return jsonObj;
		}
		
		
		JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
		          .getRealPath("/")
		          + "reports/individualDetailReport.jrxml");

		      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		     // JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
		      Map<String, Object> parameters = new HashMap<String, Object>();
		      
		      parameters.put("fromDate", frmdateInString);
		      parameters.put("toDate", todateInString);
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
	public JSONObject getIndividualSummaryReport(JSONObject jsonObject) throws JSONException, JRException, ParseException, IllegalAccessException, InvocationTargetException{
		
		
		
		JSONObject jsonObj = new JSONObject();
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		
		Object userId = session.getAttribute("userId");
		
		int employeeId =(Integer) request.getSession().getAttribute("userId");
				
		String name = jsonObject.getString("name");
		
		List<SummaryReport> timeSheetSummaryReportList = new ArrayList<SummaryReport>();
		List<SummaryReport> timeSheetHoursList = new ArrayList<SummaryReport>();
		List<TimeSheetModel> TimesheetModelList = new ArrayList<TimeSheetModel>();
		

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		
		String frmdateInString = jsonObject.getString("fromdate");
		Date frmDate = sdf.parse(frmdateInString); 
		
		String todateInString = jsonObject.getString("todate");
		Date toDate = sdf.parse(todateInString); 
		
		
		Date fromdate = frmDate;
		
		Date todate = toDate;

		
		double totalhours;
		

		timeSheetSummaryReportList = individualReportService.getIndividualSummaryReportList(fromdate, toDate, employeeId);
		
		
		if(timeSheetSummaryReportList.size()==0){
			
			
			jsonObj.put("pdfPath","norecords");
			
			return jsonObj;
		}
		
		
		if(timeSheetSummaryReportList!=null){
			
			timeSheetHoursList = individualReportService.getIndividualSummaryReportHours(fromdate, toDate, employeeId);
			System.out.println(timeSheetHoursList);
			int i =0;
			for(SummaryReport list : timeSheetHoursList){
				
				TimeSheetModel timesheetmodel = new TimeSheetModel();
				
				
				BeanUtils.copyProperties(timesheetmodel, timeSheetSummaryReportList.get(i).timeSheet);
				
				timesheetmodel.setHours(list.getHourslist());

				TimesheetModelList.add(timesheetmodel);
				
				i++;
			}
			
		}
		
		totalhours = individualReportService.getTotalHours(fromdate, toDate, employeeId);

		JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
		          .getRealPath("/")
		          + "reports/individualSummaryReport.jrxml");

		      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		     // JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
		      Map<String, Object> parameters = new HashMap<String, Object>();
		      
		      parameters.put("fromDate", frmdateInString);
		      parameters.put("toDate", todateInString);
		      parameters.put("name",name);
		      parameters.put("totalhours",totalhours);
		      
		      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(TimesheetModelList);
		      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		      String path = this.getClass().getClassLoader().getResource("/").getPath();
		      String pdfPath = path.replaceAll("WEB-INF/classes/", "");

		      JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath
		          + "reports/individualSummaryReport" + employeeId + ".pdf");

		      
		      jsonObj.put("pdfFileName","individualSummaryReport"+employeeId+".pdf");
		      jsonObj.put("pdfPath",pdfPath+ "reports/individualSummaryReport" + employeeId + ".pdf");
		   
		   
		
		return jsonObj;
		
		
		
		
		
		
		
	}
	
	
	@Path("/deletepdffile")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deletepdffile(JSONObject jsonObject) throws JSONException{
		JSONObject jsonobject = new JSONObject();
		
		System.out.println(jsonObject);
		
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
	
	@GET
	@Path("/logout")
	public String logout() {

		HttpSession session = request.getSession(true);
		session.removeAttribute("userId");

		return "success";
	}
	
	
	
	

}
