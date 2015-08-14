package com.mitosis.timesheet.webservice;

import java.io.File;
import java.io.IOException;
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

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.service.LeaveReportService;
import com.mitosis.timesheet.service.impl.LeaveReportServiceImpl;

@Path("leavereport")
public class LeaveReport {
	
	LeaveReportService leaveReportService  = new LeaveReportServiceImpl(); 
	
	@Context private HttpServletRequest request;
	
	@Path("/detailreport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject LeaveDetailReport(JSONObject jsonObject) throws JSONException, JRException, IOException, ParseException{
		
		JSONObject jsonObj = new JSONObject();
		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");
		
		int employeeId =(Integer) request.getSession().getAttribute("userId");
	
				
		String name = jsonObject.getString("name");
		
		List<LeaveDetailsModel> LeaveDetailList = new ArrayList<LeaveDetailsModel>();
		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String frmdateInString = jsonObject.getString("fromdate");
		Date fromDate = sdf.parse(frmdateInString);
		
		String todateInString = jsonObject.getString("todate");
		
		Date toDate = sdf.parse(todateInString);
		
		LeaveDetailList = leaveReportService.LeaveDetailList(fromDate, toDate);
		
		if(LeaveDetailList.size()==0){
			
			jsonObj.put("pdfPath","norecords");
			
			return jsonObj;
		}

		JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
		          .getRealPath("/")
		          + "reports/LeaveReport.jrxml");

		      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		     // JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
		      Map<String, Object> parameters = new HashMap<String, Object>();
		      
		      parameters.put("fromDate", frmdateInString);
		      parameters.put("toDate", todateInString);
		      parameters.put("name",name);
		      
		      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(LeaveDetailList);
		      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		      String path = this.getClass().getClassLoader().getResource("/").getPath();
		      String pdfPath = path.replaceAll("WEB-INF/classes/", "");
		      String pdfFilePath = pdfPath
			          + "reports/LeaveReport" + employeeId + ".pdf";
		      
		      new File(pdfFilePath).deleteOnExit();

		      JasperExportManager.exportReportToPdfFile(jasperPrint,pdfFilePath);

		      
		      jsonObj.put("pdfFileName","LeaveReport"+employeeId+".pdf");
		      jsonObj.put("pdfPath",pdfFilePath);
		   
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
	}else{
		
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
