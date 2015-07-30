package com.mitosis.timesheet.webservice;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.pojo.InvoiceDetailsReport;
import com.mitosis.timesheet.service.InvoiceDetailsService;
import com.mitosis.timesheet.service.impl.InvoiceDetailsServiceImpl;


@Path("invoiceDetails")
public class InvoiceDetails {

	InvoiceDetailsService InvoiceService = new InvoiceDetailsServiceImpl();

	@Context private HttpServletRequest request;


	@Path("/insertInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject insertInvoice(JSONObject jsonObject) throws JSONException, ParseException, IllegalAccessException, InvocationTargetException, JRException{


	HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");

		int employeeId =(Integer) request.getSession().getAttribute("userId");


		System.out.println(jsonObject);

		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();

		JSONObject json = new JSONObject();
		Date todaysdate = new Date();


		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceDateString = jsonObject.getString("invoicedate");
		Date invoiceDate = sdf.parse(InvoiceDateString);

		DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

		String invoiceNumber = InvoiceService.getInvoiceNumber();

		if(invoiceNumber == null){

			invoiceNumber ="MIT-"+1;

			invoiceHdrModel.setInvoiceNumber(invoiceNumber);


		}
		else{
			int nextNumber =1;
			String[] invoiceNumbersplit= invoiceNumber.split("-");
			System.out.println(invoiceNumbersplit);
			nextNumber += Integer.parseInt(invoiceNumbersplit[1]);
			invoiceHdrModel.setInvoiceNumber(invoiceNumbersplit[0]+"-"+nextNumber);

		}



		List<InvoiceDetailsModel> invoiceDetailList = new ArrayList<InvoiceDetailsModel>();




		JSONArray jsonarray = new JSONArray();
		jsonarray =  jsonObject.getJSONArray("invoicelist");

		System.out.println(jsonarray);



		for(int i=0;i<jsonarray.length();i++){



			InvoiceDetailsModel invoicedetail = new InvoiceDetailsModel();

			JSONObject jsonobject = new JSONObject();

			jsonobject.put("value",jsonarray.get(i));

			String InvoiceFromDateString=(String) jsonobject.getJSONObject("value").get("fromdate");
			Date invoiceFromDate = sdf1.parse(InvoiceFromDateString);

			int amt = jsonobject.getJSONObject("value").getInt("amount");

			invoicedetail.setInvoiceFromDate(invoiceFromDate);

			String InvoiceToDateString= (String) jsonobject.getJSONObject("value").get("todate");
			Date invoiceToDate = sdf2.parse(InvoiceToDateString);

			InvoiceHdrModel invoicehdrmodel = new InvoiceHdrModel();
			invoicehdrmodel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
			invoicedetail.setInvoice(invoicehdrmodel);
			


			invoicedetail.setInvoiceToDate(invoiceToDate);
			invoicedetail.setDescription((String) jsonobject.getJSONObject("value").get("description"));
			invoicedetail.setTeamMember((String) jsonobject.getJSONObject("value").get("teammember"));
			invoicedetail.setBillableHours(Integer.parseInt(jsonobject.getJSONObject("value").get("billablehours").toString()));
			BigDecimal Amt = new BigDecimal(amt);
			invoicedetail.setTotalAmount(Amt);

			invoiceDetailList.add(invoicedetail);


		}


		ProjectModel projectModel = new ProjectModel();
		projectModel.setProjectId(Integer.parseInt(jsonObject.get("projectid").toString()));
		CustomerDetailsModel customerModel = new CustomerDetailsModel();
		customerModel.setCustomerId(Integer.parseInt(jsonObject.get("customerid").toString()));

		invoiceHdrModel.setInvoiceDate(invoiceDate);
		BigDecimal invoiceAmt = new BigDecimal(jsonObject.getInt("invoiceamt"));
		invoiceHdrModel.setInvoiceAmount(invoiceAmt);
		invoiceHdrModel.setProject(projectModel);
		invoiceHdrModel.setCustomer(customerModel);
		invoiceHdrModel.setInvoiceStatus("unpaid");
		
		
		if(jsonObject.has("projecttype")){
		invoiceHdrModel.setProjectType(jsonObject.getString("projecttype"));
		
		}
		
		invoiceHdrModel.setCreatedDate(todaysdate);


		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		invoicehdrModel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
		
		String invoicenumber=invoiceHdrModel.getInvoiceNumber();


		InvoiceHdrModel invoiceHdrResult = InvoiceService.create(invoiceHdrModel);
		boolean insertdtl = InvoiceService.create(invoiceDetailList);

		if(invoiceHdrResult!=null && insertdtl){

			json.put("invoicenumber",invoicenumber);
			json.put("value", "inserted");
			
			/*CompanyInfoModel companyinfoModel = new CompanyInfoModel();
			
			companyinfoModel = InvoiceService.getCompanyInfo();

			List<InvoiceDetailsReport> invoiceReportList = new ArrayList<InvoiceDetailsReport>();
		
				InvoiceDetailsReport invoiceDetailsReport = new InvoiceDetailsReport();
				
				
				invoiceDetailsReport.setInvoiceHdr(invoiceHdrModel);
				invoiceDetailsReport.setCompanyInfo(companyinfoModel);
				invoiceDetailsReport.setInvoiceDetailsModel(invoiceDetailList);
				
				invoiceReportList.add(invoiceDetailsReport);
				

			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
					.getRealPath("/")
					+ "reports/InvoiceReport.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			 parameters.put("invoiceDetailsList", invoiceDetailsReport.getInvoiceDetailsModel());



			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(invoiceReportList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

			String path = this.getClass().getClassLoader().getResource("/").getPath();
			String pdfPath = path.replaceAll("WEB-INF/classes/", "");
			String pdfFilePath = pdfPath
					+ "reports/InvoiceReport" + employeeId + ".pdf";

			new File(pdfFilePath).deleteOnExit();

			JasperExportManager.exportReportToPdfFile(jasperPrint,pdfFilePath);

			json.put("pdfFileName","InvoiceReport"+employeeId+".pdf");
			json.put("pdfPath",pdfFilePath);
*/
		}
		return json;
	





	}


	@Path("/updateInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject updateInvoice(JSONObject jsonObject) throws JSONException, ParseException, IllegalAccessException, InvocationTargetException{

		List<InvoiceDetailsModel> invoiceDetailList = new ArrayList<InvoiceDetailsModel>();

		InvoiceDetailsService InvoiceService = new InvoiceDetailsServiceImpl();

		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();

		InvoiceDetailsModel invoiceDetailsModel =  new InvoiceDetailsModel();

		JSONObject json = new JSONObject();
		Date todaysdate = new Date();


		String invoiceNumber = jsonObject.getString("invoiceNumber");
		invoiceHdrModel.setInvoiceNumber(invoiceNumber);

		int id =0;

		id = InvoiceService.getId(invoiceNumber);


		if(jsonObject.has("invoicedate")){

			DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String InvoiceDateString = jsonObject.getString("invoicedate");
			Date invoiceDate = sdf.parse(InvoiceDateString);
			invoiceHdrModel.setInvoiceDate(invoiceDate);
		}
		if(jsonObject.has("projectid")){

			ProjectModel projectModel = new ProjectModel();
			projectModel.setProjectId(jsonObject.getInt("projectid"));
			invoiceHdrModel.setProject(projectModel);
		}

		if(jsonObject.has("customerid")){

			CustomerDetailsModel customerModel = new CustomerDetailsModel();
			customerModel.setCustomerId(jsonObject.getInt("customerid"));
			invoiceHdrModel.setCustomer(customerModel);

		}

		if(jsonObject.has("invoiceamount")){

			BigDecimal invoiceAmt = new BigDecimal(jsonObject.getInt("invoiceamount"));
			invoiceHdrModel.setInvoiceAmount(invoiceAmt);
		}
		if(jsonObject.has("projecttype")){

			invoiceHdrModel.setProjectType(jsonObject.getString("projecttype"));

		}


		invoiceHdrModel.setCreatedDate(todaysdate);


		if(jsonObject.has("invoicefromdate")){

			DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			String InvoiceFromDateString= jsonObject.getString("invoicefromdate");
			Date invoiceFromDate = sdf1.parse(InvoiceFromDateString);
			invoiceDetailsModel.setInvoiceFromDate(invoiceFromDate);

		}

		if(jsonObject.has("invoicetodate")){

			DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
			String InvoiceToDateString= jsonObject.getString("invoicetodate");
			Date invoiceToDate = sdf2.parse(InvoiceToDateString);
			invoiceDetailsModel.setInvoiceToDate(invoiceToDate);


		}

		if(jsonObject.has("rateperhour")){


			BigDecimal rateperhour = new BigDecimal(jsonObject.getInt("rateperhour"));
			invoiceDetailsModel.setRatePerHour(rateperhour);
		}

		if(jsonObject.has("description")){


			invoiceDetailsModel.setDescription(jsonObject.getString("description"));
		}

		if(jsonObject.has("teammember")){

			invoiceDetailsModel.setTeamMember(jsonObject.getString("teammember"));

		}

		if(jsonObject.has("billablehours")){

			invoiceDetailsModel.setBillableHours(jsonObject.getInt("billablehours"));

		}


		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		invoicehdrModel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
		invoiceDetailsModel.setId(id);
		invoiceDetailsModel.setInvoice(invoicehdrModel);
		return json;

/*
		 inserthdr = InvoiceService.create(invoiceHdrModel);
		boolean insertdtl = InvoiceService.create(invoiceDetailList);

		if(inserthdr && insertdtl ){

			json.put("value", "updated Successfully");
			return json;
		}
		else{
			json.put("value", "updationfailed ");
			return json;
		}*/


	}



	@Path("/getProjectList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public List<ProjectModel> getProjectList() throws JSONException, ParseException{



		List<ProjectModel> projectList = new ArrayList<ProjectModel>();

		projectList = InvoiceService.getProjectList();

		return projectList;



	}


	@Path("/getCustomerList")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDetailsModel> getCustomerList() throws JSONException, ParseException{



		List<CustomerDetailsModel> customerList = new ArrayList<CustomerDetailsModel>();

		customerList = InvoiceService.getCustomerList();

		return customerList;	



	}


	@Path("/getProjectTypeList")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectCostHdrModel> getProjectTypeList(JSONObject jsonObject) throws JSONException, ParseException{

		int id = jsonObject.getInt("projectId");

		List<ProjectCostHdrModel> projectCostHdrList = new ArrayList<ProjectCostHdrModel>();

		projectCostHdrList = InvoiceService.getProjectCostHdrList(id);

		return projectCostHdrList;	



	}



	@Path("/getTeamMembers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectCostDetailsModel> getTeamMembers(JSONObject jsonObject) throws JSONException, ParseException{



		List<ProjectCostDetailsModel> memberList = new ArrayList<ProjectCostDetailsModel>();

		int projectId=jsonObject.getInt("projectId");

		memberList = InvoiceService.getTeamList(projectId);

		return memberList;	



	}


	@Path("/deleteInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject deleteInvoice(JSONObject jsonObject) throws JSONException, ParseException{






		return jsonObject;



	}
}
