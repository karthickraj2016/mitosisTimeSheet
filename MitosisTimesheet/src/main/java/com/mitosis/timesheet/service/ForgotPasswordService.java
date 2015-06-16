package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.UserDetailsModel;


public interface ForgotPasswordService {
	  
	public UserDetailsModel getEmailId(String mailId);

	public boolean updatepassword(UserDetailsModel userDetailsModel);
	
	public boolean setpasswordflag(UserDetailsModel userDetailsModel);

	public UserDetailsModel getIsreset(int id);


	
}