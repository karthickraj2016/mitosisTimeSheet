package com.mitosis.timesheet.webservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.util.JasperUtil;


@Path("financeReport")
public class financeReport extends JasperUtil {
	
	@Context private HttpServletRequest request;
	
	@Path("/bankReconcileReport")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public JSONObject leaveSummaryReport(JSONObject jsonObject) throws Exception {

		HttpSession session= request.getSession(true);

		if(session.getAttribute("userId")==null){
			return null;
		}
		Object userId = session.getAttribute("userId");

		int employeeId =(Integer) request.getSession().getAttribute("userId");
		
		String fromDate = jsonObject.getString("fromDate");
        String toDate =jsonObject.getString("toDate");

        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
		Date dateFrom = sdf.parse(fromDate);
		Date dateTo = sdf.parse(toDate);
      
        JSONObject jsonobject = new JSONObject();

		String reportFilePath = request.getSession().getServletContext().getRealPath("/")+ "reports/BankReconcileReport.jrxml";

		Map<String, Object> parameters = new HashMap<String, Object>();
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		String pdfPath = path.replaceAll("WEB-INF/classes/", "");
		
		parameters.put("fromDate", fromDate);
		parameters.put("toDate",toDate);
		parameters.put("dateFrom",dateFrom);
		parameters.put("dateTo",dateTo);
		
		String pdfFilePath = pdfPath+"reports/BankReconcileReport"+employeeId+".pdf";

		RenderJr(reportFilePath, parameters,pdfFilePath);

		JRBeanCollectionDataSource ds = null;

		jsonobject.put("report","report successful");
		jsonobject.put("pdfFileName", "BankReconcileReport"+employeeId+".pdf");

		return jsonobject;
	}

}
