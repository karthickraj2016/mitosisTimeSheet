package com.mitosis.timesheet.webservice;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
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
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.codec.binary.Base64;
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

		InvoiceHdrModel invoiceHdrModel = new InvoiceHdrModel();

		JSONObject json = new JSONObject();
		Date todaysdate = new Date();


		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String InvoiceDateString = jsonObject.getString("invoicedate");
		Date invoiceDate = sdf.parse(InvoiceDateString);

		DateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

		String invoiceNumber = InvoiceService.getInvoiceNumber();

		int id;

		if(invoiceNumber == null){

			invoiceNumber ="MIT-"+1;

			id=1;

			invoiceHdrModel.setInvoiceNumber(invoiceNumber);
			invoiceHdrModel.setId(id);


		}
		else{
			int nextNumber =1;
			String[] invoiceNumbersplit= invoiceNumber.split("-");
			nextNumber += Integer.parseInt(invoiceNumbersplit[1]);
			invoiceHdrModel.setInvoiceNumber(invoiceNumbersplit[0]+"-"+nextNumber);
			invoiceHdrModel.setId(nextNumber);

		}



		List<InvoiceDetailsModel> invoiceDetailList = new ArrayList<InvoiceDetailsModel>();




		JSONArray jsonarray = new JSONArray();
		jsonarray =  jsonObject.getJSONArray("invoicelist");

		for(int i=0;i<jsonarray.length();i++){



			InvoiceDetailsModel invoicedetail = new InvoiceDetailsModel();

			JSONObject jsonobject = new JSONObject();

			jsonobject.put("value",jsonarray.get(i));

			String InvoiceFromDateString=(String) jsonobject.getJSONObject("value").get("fromdate");
			Date invoiceFromDate = sdf1.parse(InvoiceFromDateString);

			String stringTotalAmt = String.valueOf(jsonobject.getJSONObject("value").get("amount"));


			double totalamt = Double.parseDouble((String)stringTotalAmt);		


			invoicedetail.setInvoiceFromDate(invoiceFromDate);

			String InvoiceToDateString= (String) jsonobject.getJSONObject("value").get("todate");
			Date invoiceToDate = sdf2.parse(InvoiceToDateString);

			InvoiceHdrModel invoicehdrmodel = new InvoiceHdrModel();
			invoicehdrmodel.setInvoiceNumber(invoiceHdrModel.getInvoiceNumber());
			invoicedetail.setInvoice(invoicehdrmodel);

			invoicedetail.setInvoiceToDate(invoiceToDate);
			invoicedetail.setDescription((String) jsonobject.getJSONObject("value").get("description"));

			if(jsonobject.getJSONObject("value").has("teammember") && !jsonobject.getJSONObject("value").get("teammember").toString().equals("")){
				
				invoicedetail.setTeamMember((String) jsonobject.getJSONObject("value").getJSONObject("teammember").getString("name"));
			}		

			if(jsonobject.getJSONObject("value").has("billablehours") && !jsonobject.getJSONObject("value").get("billablehours").toString().equals("")){

				invoicedetail.setBillableHours(Integer.parseInt(jsonobject.getJSONObject("value").get("billablehours").toString()));


			}

			if(jsonobject.getJSONObject("value").has("rateperhour") && !jsonobject.getJSONObject("value").get("rateperhour").toString().equals("")){

				String stringrateperhour = jsonobject.getJSONObject("value").get("rateperhour").toString();


				double ratevalue = Double.parseDouble((String)stringrateperhour);		
				BigDecimal rateperhour = BigDecimal.valueOf(ratevalue);
				invoicedetail.setRatePerHour(rateperhour);

			}

			BigDecimal Amt = BigDecimal.valueOf(totalamt);;
			invoicedetail.setTotalAmount(Amt);

			invoiceDetailList.add(invoicedetail);


		}
		ProjectModel projectModel = new ProjectModel();
		CustomerDetailsModel customerModel = new CustomerDetailsModel();

		JSONObject project = new JSONObject();

		JSONObject customer = new JSONObject();

		project=(JSONObject) jsonObject.get("project");

		customer = (JSONObject) jsonObject.get("customer");

		projectModel.setProjectId(Integer.parseInt(project.get("projectId").toString()));

		projectModel.setProjectName(project.getString("projectName"));

		customerModel.setCustomerId(Integer.parseInt(customer.get("customerId").toString()));

		customerModel.setCustomerName(customer.getString("customerName"));
		
		
		if (customer.has("phone") && !customer.getString("phone").equals("") && !customer.get("phone").equals(null)) {
			/*BigInteger phone = new BigInteger(jsonObject.getString("phone"));*/
			customerModel.setPhone(Long.valueOf(customer.getString("phone")));
		}

		customerModel.setMobile(Long.valueOf(customer.getString("mobile")));

		customerModel.setAddress(customer.getString("address"));

		invoiceHdrModel.setInvoiceDate(invoiceDate);
		
		String invamount= jsonObject.get("invoiceamt").toString();

		double invamt = Double.parseDouble(invamount);		
		BigDecimal invoiceamount = BigDecimal.valueOf(invamt);

		invoiceHdrModel.setInvoiceAmount(invoiceamount);
		invoiceHdrModel.setProject(projectModel);
		invoiceHdrModel.setCustomer(customerModel);
		invoiceHdrModel.setCurrencyCode(jsonObject.getString("currency"));
		invoiceHdrModel.setInvoiceStatus("unpaid");
		invoiceHdrModel.setBalanceAmount(invoiceamount);
		invoiceHdrModel.setPaidAmount(new BigDecimal(0));


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

			CompanyInfoModel companyinfoModel = new CompanyInfoModel();

			companyinfoModel = InvoiceService.getCompanyInfo();

			InvoiceDetailsReport invoiceDetailsReport = new InvoiceDetailsReport();


			invoiceDetailsReport.setInvoiceHdr(invoiceHdrModel);
			invoiceDetailsReport.setCompanyInfo(companyinfoModel);


			JasperDesign jasperDesign = JRXmlLoader.load(request.getSession().getServletContext()
					.getRealPath("/")
					+ "reports/InvoiceReport.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// JREmptyDataSource jrEmptyDatasource = new JREmptyDataSource();
			Map<String, Object> parameters = new HashMap<String, Object>();
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			String pdfPath = path.replaceAll("WEB-INF/classes/", "");
			String imagePath = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("WEB-INF/classes/", "");

			parameters.put("invoiceDetailsList", invoiceDetailsReport);
			parameters.put("logoimage",imagePath+"/images");
			parameters.put("totalinvoice", invoiceDetailList.size());



			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(invoiceDetailList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);


			String pdfFilePath = pdfPath	
					+ "reports/InvoiceReport" + employeeId + ".pdf";

			new File(pdfFilePath).deleteOnExit();

			JasperExportManager.exportReportToPdfFile(jasperPrint,pdfFilePath);

			json.put("pdfFileName","InvoiceReport"+employeeId+".pdf");
			json.put("pdfPath",pdfFilePath);

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
	public List<ProjectCostHdrModel> getProjectTypeList(JSONObject jsonObject) throws JSONException{


		JSONObject project = new JSONObject();



		project=(JSONObject) jsonObject.get("project");



		int id = project.getInt("projectId");

		List<ProjectCostHdrModel> projectCostHdrList = new ArrayList<ProjectCostHdrModel>();

		projectCostHdrList = InvoiceService.getProjectCostHdrList(id);


		return projectCostHdrList;	



	}


	@Path("/getInvoiceList")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceHdrModel> getInvoiceList(JSONObject jsonObject) throws JSONException{


		List<InvoiceHdrModel> invoiceList = new ArrayList<InvoiceHdrModel>();


		invoiceList = InvoiceService.getInvoiceList(jsonObject.getInt("projectId"));


		return invoiceList;

	}


	@Path("/getInvoiceDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceDetailsModel> getInvoiceDetails(int invoiceno) throws JSONException{


		List<InvoiceDetailsModel> invoiceDetailsList = new ArrayList<InvoiceDetailsModel>();


		invoiceDetailsList = InvoiceService.getInvoiceDetails(invoiceno);


		return invoiceDetailsList;

	}




	@Path("/getMemberRate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProjectCostDetailsModel getMemberRate(JSONObject jsonObject) throws JSONException{


		ProjectCostHdrModel projectCostHdrModel = new ProjectCostHdrModel();

		int memberId = jsonObject.getInt("memberId");

		int projectId = jsonObject.getInt("projectId");
	
		projectCostHdrModel = InvoiceService.getCostHdrId(projectId);
		
		int projectCostId = projectCostHdrModel.getId();
		
		ProjectCostDetailsModel projectCostDetailsModel = new ProjectCostDetailsModel();
		
		projectCostDetailsModel =InvoiceService.getMemberRate(projectCostId,memberId);

		return projectCostDetailsModel;


	}





	/*
	@Path("/getTeamMembers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectCostDetailsModel> getTeamMembers(JSONObject jsonObject) throws JSONException, ParseException{

		JSONObject project = new JSONObject();



		List<ProjectCostDetailsModel> memberList = new ArrayList<ProjectCostDetailsModel>();

	 *customerModel.setCustomerId(Integer.parseInt(jsonObject.get("customerid").toString()));


		project=(JSONObject) jsonObject.get("project");

		memberList = InvoiceService.getTeamList(project.getInt("projectId"));

		return memberList;	



	}
	 */

	@Path("/deleteInvoice")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject deleteInvoice(JSONObject jsonObject) throws JSONException, ParseException{






		return jsonObject;



	}



}
