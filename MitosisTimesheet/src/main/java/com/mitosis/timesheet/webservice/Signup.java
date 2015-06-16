package com.mitosis.timesheet.webservice;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mitosis.timesheet.dao.SignupDao;
import com.mitosis.timesheet.dao.daoImpl.SignupDaoImpl;
import com.mitosis.timesheet.model.UserDetailsModel;



@Path("/account")

public class Signup {

	SignupDao signUp = new SignupDaoImpl();
	UserDetailsModel userModel = new UserDetailsModel();
	String url = getValueByKey("url");
	String projectName = getValueByKey("projectName");
	@Context
	private HttpServletRequest request;

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject signup(JSONObject jsonObject) throws Exception {

		return new JSONObject().put("signup", signUpValidation(jsonObject));

	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject login(JSONObject jsonObject) throws JSONException {

		JSONObject jsonObj = new JSONObject();

		UserDetailsModel userDetailsModel = new UserDetailsModel();

		boolean user = false;

		userDetailsModel = signUp.login(jsonObject.getString("username"), jsonObject.getString("password"));

		if (userDetailsModel != null) {

			user = signUp.loginValidation(jsonObject.getString("username"), jsonObject.getString("password"));

			if (user != true) {
				jsonObj.put("message", "notactivated");
			} else {

				HttpSession session = request.getSession(true);
				session.removeAttribute("userId");
				Object userId = session.getAttribute("userId");

				if (userId != null) {
					System.out.println(userId.toString());
				} else {
					session.setAttribute("userId", userDetailsModel.getId());
				}

				jsonObj.put("message", "success");
			}
		} else {

			jsonObj.put("message", "signupunsuccessful");

		}

		return jsonObj;
	}

	@GET
	@Path("/logout")
	public String logout() {

		HttpSession session = request.getSession(true);
		session.removeAttribute("userId");

		return "success";
	}

	private String signUpValidation(JSONObject jsonObject) throws Exception {

		UserDetailsModel userList = new UserDetailsModel();

		/*
		 * DESEncryption encription= new DESEncryption(); String
		 * encryptedText=encription.encrypt(jsonObject.getString("password"));
		 */

		userModel.setName(jsonObject.getString("name"));
		userModel.setUserName(jsonObject.getString("username"));
		userModel.seteMail(jsonObject.getString("email"));
		userModel.setPassword(jsonObject.getString("password"));
		// userModel.setActive(jsonObject.getBoolean("active"));
		// userModel.setRole(jsonObject.getString("role"));

		String empName = jsonObject.getString("username");
		String empMail = jsonObject.getString("email");

		userList = signUp.createUser(userModel);
		int id = signUp.getUserId(empName, empMail);

		final String username = "testingemail2016@gmail.com";
		final String password = "Kart@2016";
		final String content = "<p>Thank you for registering. You have an account with username<br/><b>"
				+ userList.getUserName()
				+ "</b>. In order to complete your registration, Click the link below</p><br/><a href='" + url + "/"
				+ projectName + "/rest/account/activeUser?id=" + id + "'>click here for activation</a>";
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(jsonObject.getString("email")));
			message.setSubject("MITOSIS-TimeSheet Registration");
			message.setContent(content, "text/html");

			Transport.send(message);
			System.out.println("DONE");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
    } catch (Exception e) {
      e.printStackTrace();
		}

		return "success";

	}

	@GET
	@Path("/activeUser")
	public Response activeUser(@QueryParam("id") int id) throws Exception, URISyntaxException {

		UserDetailsModel userActive = new UserDetailsModel();

		userActive = signUp.userActivate(id);
		System.out.println(userActive);

		if (userActive != null) {

			java.net.URI location = new java.net.URI(url + "/" + projectName + "/#/login");
			return Response.temporaryRedirect(location).build();
		} else {
			return null;
		}
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

	@POST
	@Path("/nameValidation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean nameValidation(JSONObject jsonObject) throws Exception {

		boolean usernamevalidation;
		String empName = jsonObject.getString("username");
		usernamevalidation = signUp.checkUsername(empName);
		if (usernamevalidation != true) {
			return true;
		} else {
			return false;
		}
	}

	@POST
	@Path("/mailValidation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean mailValidation(JSONObject jsonObject) throws Exception {

		boolean mailvalidation;
		String empMail = jsonObject.getString("email");
		mailvalidation = signUp.checkEmail(empMail);
		if (mailvalidation != true) {
			return true;
		} else {
			return false;
		}
	}

	@Path("/getName")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getName() throws JSONException {
		HttpSession session = request.getSession(true);

		if (session.getAttribute("userId") == null) {
			return null;
		}
		Object userId = session.getAttribute("userId");
		/* Object userId=137; */

		UserDetailsModel name = null;
		String empname = null;

		name = signUp.getname(userId);
		empname = name.getName();
		return empname;
	}

}


