package com.mitosis.timesheet.service;

import com.mitosis.timesheet.pojo.UserDetailsVo;

public interface AccountDetailsService {
	
	
	public UserDetailsVo getAccountDetails(Object userId);

	public boolean updateNewPassword(UserDetailsVo userDetailsVo);
	
	public boolean updateDetails(UserDetailsVo userDetailsVo);

}
