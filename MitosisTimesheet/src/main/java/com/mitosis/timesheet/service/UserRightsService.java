package com.mitosis.timesheet.service;

import java.util.List;


import com.mitosis.timesheet.model.UserDetailsModel;

public interface UserRightsService {
	
	public List<UserDetailsModel> showUserList();
	
	public boolean updateRights(UserDetailsModel userModel);

}
