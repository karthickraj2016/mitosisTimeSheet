package com.mitosis.timesheet.webservice;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;
import com.mitosis.timesheet.service.IndividualReportService;
import com.mitosis.timesheet.service.TeamReportService;
import com.mitosis.timesheet.service.impl.IndividualReportServiceImpl;
import com.mitosis.timesheet.service.impl.TeamReportServiceImpl;



@Path("teamreport")
public class TeamReport {

	TeamReportService teamReportService = new TeamReportServiceImpl();

	IndividualReportService individualReportService  = new IndividualReportServiceImpl();



	@Context private HttpServletRequest request;


	@Path("/getprojectlist")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectModel> getprojectList() throws JSONException, JRException, IOException, ParseException{
		JSONObject jsonobject = new JSONObject();

		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}


		List<ProjectModel> projectList = new ArrayList<ProjectModel>();

		projectList = teamReportService.getProjectList();



		return projectList;
	}

	@Path("/detailReport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject detailReport(JSONObject jsonObject) throws JSONException, ParseException, JRException{

		JSONObject jsonobject = new JSONObject();

		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}


		Object userId = session.getAttribute("userId");

		int employeeId =(Integer) request.getSession().getAttribute("userId");

		int projectId= jsonObject.getInt("projectId");

		String name = jsonObject.getString("name");

		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String frmdateInString = jsonObject.getString("fromdate");
		Date fromDate = sdf.parse(frmdateInString);

		String todateInString = jsonObject.getString("todate");

		Date toDate = sdf.parse(todateInString);

		/*int userId =  (Integer) request.getSession().getAttribute("userId");*/

		int role = teamReportService.getrole(employeeId);

		if(role<4){

			List<TeamAssignmentModel> TeamList = new ArrayList<TeamAssignmentModel>();
			List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();

			TeamList = teamReportService.getTeamList(projectId,role);
			System.out.println(TeamList);

			for(TeamAssignmentModel teamList:TeamList){
				int i=0;
				int memberId= teamList.getMember().getId();

				List<TimeSheetModel> timesheetList = new ArrayList<TimeSheetModel>();

				timesheetList = teamReportService.getTeamReportList(fromDate, toDate, memberId,projectId);

				timeSheetList.addAll(i,timesheetList);
				i++;
			}



			if(timeSheetList.size()==0){

				jsonobject.put("pdfPath","norecords");

				return jsonobject;


			}

			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
					.getRealPath("/")
					+ "reports/teamDetailReport.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("fromDate", frmdateInString);
			parameters.put("toDate", todateInString);
			parameters.put("name",name);

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(timeSheetList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

			String path = this.getClass().getClassLoader().getResource("/").getPath();
			String pdfPath = path.replaceAll("WEB-INF/classes/", "");

			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath
					+ "reports/teamDetailReport" + employeeId + ".pdf");


			jsonobject.put("pdfFileName","teamDetailReport"+employeeId+".pdf");
			jsonobject.put("pdfPath",pdfPath+ "reports/teamDetailReport.jrxml" + employeeId + ".pdf");


			return jsonobject;




		}

		else{

			timeSheetDetailReport = individualReportService.getIndividualReport(fromDate, toDate, employeeId);

			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
					.getRealPath("/")
					+ "reports/teamDetailReport.jrxml");

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
					+ "reports/teamDetailReport" + employeeId + ".pdf");


			jsonobject.put("pdfFileName","teamDetailReport"+employeeId+".pdf");
			jsonobject.put("pdfPath",pdfPath+ "reports/teamDetailReport.jrxml" + employeeId + ".pdf");


			return jsonobject;



		}





	}


	@Path("/summaryReport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject summaryReport(JSONObject jsonObject) throws JSONException, ParseException, JRException, IllegalAccessException, InvocationTargetException{

		JSONObject jsonobject = new JSONObject();

		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}


		Object userId = session.getAttribute("userId");

		int employeeId =(Integer) request.getSession().getAttribute("userId");

		int projectId= jsonObject.getInt("projectId");

		String name = jsonObject.getString("name");

		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String frmdateInString = jsonObject.getString("fromdate");
		Date fromDate = sdf.parse(frmdateInString);

		String todateInString = jsonObject.getString("todate");

		Date toDate = sdf.parse(todateInString);
		double totalhours=0.0;


		/*int userId =  (Integer) request.getSession().getAttribute("userId");*/

		int role = teamReportService.getrole(employeeId);

		if(role<4){

			List<TeamAssignmentModel> TeamList = new ArrayList<TeamAssignmentModel>();
			List<TimeSheetModel> timeSheetList = new ArrayList<TimeSheetModel>();
			List<TimeSheetModel> timeSheetSummedList = new ArrayList<TimeSheetModel>();
			List<SummaryReport> hourslist = new ArrayList<SummaryReport>();

			TeamList = teamReportService.getTeamList(projectId,role);

			for(TeamAssignmentModel teamList:TeamList){

				int memberId= teamList.getMember().getId();
				hourslist = teamReportService.getSumHours(fromDate, toDate, memberId,projectId);
				timeSheetList=teamReportService.getTeamReportList(fromDate, toDate, memberId,projectId);
				for(int j=0;j<hourslist.size();j++){
					timeSheetList.get(j).setHours(hourslist.get(j).hourslist);
				}
				timeSheetSummedList.addAll(timeSheetList);

				totalhours = totalhours+teamReportService.getTotalHours(fromDate, toDate, memberId,projectId);

			}

			if(timeSheetSummedList.size()==0){

				jsonobject.put("pdfPath","norecords");

				return jsonobject;


			}

			totalhours=(double) Math.round(totalhours * 100) / 100;

			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
					.getRealPath("/")
					+ "reports/teamSummaryReport.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("fromDate", frmdateInString);
			parameters.put("toDate", todateInString);
			parameters.put("name",name);
			parameters.put("totalhours", totalhours);

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(timeSheetSummedList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

			String path = this.getClass().getClassLoader().getResource("/").getPath();
			String pdfPath = path.replaceAll("WEB-INF/classes/", "");

			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath
					+ "reports/teamSummaryReport" + employeeId + ".pdf");


			jsonobject.put("pdfFileName","teamSummaryReport"+employeeId+".pdf");
			jsonobject.put("pdfPath",pdfPath+ "reports/teamSummaryReport.jrxml" + employeeId + ".pdf");


			return jsonobject;




		}

		else{

			timeSheetDetailReport = individualReportService.getIndividualReport(fromDate, toDate, employeeId);

			totalhours = individualReportService.getTotalHours(fromDate, toDate, employeeId);

			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
					.getRealPath("/")
					+ "reports/teamSummaryReport.jrxml");

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
					+ "reports/teamSummaryReport" + employeeId + ".pdf");


			jsonobject.put("pdfFileName","teamSummaryReport"+employeeId+".pdf");
			jsonobject.put("pdfPath",pdfPath+ "reports/teamSummaryReport.jrxml" + employeeId + ".pdf");


			return jsonobject;



		}





	}

	@GET
	@Path("/logout")
	public String logout() {

		HttpSession session = request.getSession(true);
		session.removeAttribute("userId");

		return "success";
	}

}
