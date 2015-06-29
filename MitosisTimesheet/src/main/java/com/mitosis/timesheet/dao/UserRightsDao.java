package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.UserDetailsModel;

public interface UserRightsDao {

	public List<UserDetailsModel> showUserList();
	
	public boolean updateRights(UserDetailsModel userModel);
}
