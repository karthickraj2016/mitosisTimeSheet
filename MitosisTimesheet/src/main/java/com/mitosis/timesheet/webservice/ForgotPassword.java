package com.mitosis.timesheet.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.commonservice.JavaMD5Hash;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.ForgotPasswordService;
import com.mitosis.timesheet.service.impl.ForgotPasswordServiceImpl;



@Path("forgotpassword")

public class ForgotPassword {
	
	ForgotPasswordService forgotpassword = new ForgotPasswordServiceImpl();
	String url = getValueByKey("url");
	String projectName = getValueByKey("projectName");
	
	@POST
	@Path("/changepassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetailsModel forgotpassword(JSONObject jsonObject) throws JSONException {

		String mailId = jsonObject.getString("emailid");
		
		String isReset = jsonObject.getString("passwordflag");

		UserDetailsModel userDetailsModel = new UserDetailsModel();

		userDetailsModel= forgotpassword.getEmailId(mailId);
		
		if(userDetailsModel.geteMail()!= null){
		
		if(isReset.equals("Y")){
			
			userDetailsModel.setReset(true);
			
		}
			boolean isreset = forgotpassword.setpasswordflag(userDetailsModel);
		

			final String username = "testingemail2016@gmail.com";
			final String password = "Kart@2016";
			final String content = "In order to change your password visit the following url and set your new password<br/><a href='" + url + "/"
					+ projectName + "/#/resetpassword?id=" + userDetailsModel.getId() + "' onclick='resetpassword(); return false'>click here for activation</a>";
			System.out.println(content);
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			System.out.println(props);

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("testingemail2016@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailId));
				message.setSubject("MITOSIS-Change Password");
				message.setContent(content, "text/html");

				Transport.send(message);
				System.out.println("DONE");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			
		}
		return userDetailsModel;
	}
	
	@POST
	@Path("/resetpassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean resetpassword(JSONObject jsonObject) throws JSONException {
		
		int id= jsonObject.getInt("id");
		
		boolean passwordupdated = false;
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		
		
		String password = jsonObject.getString("password");
		
		String encryptedPassword =JavaMD5Hash.md5(password);
		
		userDetailsModel = forgotpassword.getIsreset(id);
	
		
		if(userDetailsModel.IsReset()){
			
			userDetailsModel.setReset(false);
			userDetailsModel.setPassword(encryptedPassword);
			
			
			passwordupdated = forgotpassword.updatepassword(userDetailsModel);
			
			return passwordupdated;
			
			
		}
		else{
		

		return passwordupdated;
		}
		
		}

	@POST
	@Path("/mailvalidation")
	/*@Consumes(MediaType.APPLICATION_JSON)*/
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetailsModel mailvalidation(JSONObject jsonObject) throws JSONException{
		
		String emailid = jsonObject.getString("emailid");
		
		UserDetailsModel userDetailsmodel = new UserDetailsModel();
		userDetailsmodel = forgotpassword.getEmailId(emailid);
	
		return userDetailsmodel;
			
	}
	
	public String getValueByKey(String key) {

		InputStream inputStream = null;
		Properties prop = new Properties();

		inputStream = getClass().getClassLoader().getResourceAsStream("urlConfig.properties");

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop.getProperty(key);

	}

}
