package com.mitosis.timesheet.service;

import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.pojo.UserDetailsVo;

public interface AccountDetailsService {
	
	
	public UserDetailsModel getAccountDetails(Object userId);

	public boolean updateNewPassword(UserDetailsModel userDetailsModel);
	
	public boolean updateDetails(UserDetailsModel userDetailsModel);
	
	public boolean checkMailId(String mailId);

}
