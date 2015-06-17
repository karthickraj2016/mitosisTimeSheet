package com.mitosis.timesheet.dao;

import com.mitosis.timesheet.model.UserDetailsModel;

public interface AccountDetailsDao {

	
	public UserDetailsModel getAccountDetails(Object Id);
	
	public boolean updateNewPassword(UserDetailsModel userDetailsModel);
	
	public boolean updateDetails(UserDetailsModel userDetailsModel);
}
