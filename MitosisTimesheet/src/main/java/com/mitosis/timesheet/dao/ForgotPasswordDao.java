package com.mitosis.timesheet.dao;

import com.mitosis.timesheet.model.UserDetailsModel;



public interface ForgotPasswordDao {
	
	public UserDetailsModel getEmailid(String emailId);

	public boolean update(UserDetailsModel userDetailsModel);

	public boolean updatepasswordflag(UserDetailsModel userDetailsModel);

	public UserDetailsModel getIsreset(int id);

}
