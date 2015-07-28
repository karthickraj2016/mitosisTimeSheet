package com.mitosis.timesheet.webservice;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.mitosis.timesheet.service.CompanyService;
import com.mitosis.timesheet.service.impl.CompanyServiceImpl;



@Path("company")
public class Company {
	CompanyInfoModel company=new CompanyInfoModel();
	CompanyService companyservice=new CompanyServiceImpl();
	@Path("/addcompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addCompany(JSONObject jsonObject) throws JSONException, ParseException{
		boolean flag=false;
		if(jsonObject.has("id")){
			company.setId(jsonObject.getInt("id"));
		}
		company.setCompanyName(jsonObject.getString("companyName"));
		company.setCompanyAddress(jsonObject.getString("companyAddress"));
		company.setPhoneNumber(jsonObject.getString("phoneNumber"));
		company.setMobileNumber(jsonObject.getString("mobileNumber"));
		company.setFaxNo(jsonObject.getString("faxNo"));
		company.setLogo(jsonObject.getString("logo"));
		company.setAccountNumber(jsonObject.getInt("accountNumber"));
		company.setBankName(jsonObject.getString("bankName"));
		company.setBankAddress(jsonObject.getString("bankAddress"));
		company.setIfscCode(jsonObject.getString("ifscCode"));
		company.setMicrCode(jsonObject.getString("micrCode"));
		company.setSwiftCode(jsonObject.getString("swiftCode"));
		System.out.println("ph "+company.getPhoneNumber());
		System.out.println("fx "+company.getFaxNo());
		System.out.println("mob "+company.getMobileNumber());
		System.out.println("ph "+company.getAccountNumber());
		flag=companyservice.save(company);
		return flag;
}
	
	@Path("/showCompanylist")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CompanyInfoModel> showlist() throws JSONException, ParseException {

		List<CompanyInfoModel> companylist = new ArrayList<CompanyInfoModel>();

		companylist = companyservice.showlist();
		
		return companylist;

	}
	@Path("/showCompany")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object show() throws JSONException, ParseException {
		
		CompanyInfoModel c=companyservice.show();
		if(c==null)
		{
			JSONObject res=new JSONObject();
			res.put("result", "No record Found");
			return res;
		}
		
		//For testing starts
//		byte[] data = c.getLogo();
//		try{
//		FileOutputStream fout=new FileOutputStream("/home/muthu/Downloads/photos/logows.jpg");
//		fout.write(data);
//		fout.flush();
//		}
//		catch(Exception e){
//			System.out.println(e.toString());
//		}
		//Ends
		return c;

	}
	@Path("/removeCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object remove(JSONObject jsonObject) throws JSONException, ParseException {
		int companyId=jsonObject.getInt("id");
		boolean c=companyservice.remove(companyId);
		
		return c;

	}
}