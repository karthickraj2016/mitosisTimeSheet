package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.UserRightsDao;
import com.mitosis.timesheet.dao.daoImpl.UserRightsDaoImpl;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.UserRightsService;

public class UserRightsServiceImpl implements UserRightsService  {
	
	UserRightsDao userDAO = new UserRightsDaoImpl();
	
	@Override
	public List<UserDetailsModel>  showUserList() {
		
		List<UserDetailsModel> userList = new ArrayList<UserDetailsModel>();

		userList = userDAO.showUserList();
		
		return userList;
	}
	
	@Override
	public boolean  updateRights(UserDetailsModel userModel) {
	
		boolean flag=false;
		
		flag=userDAO.updateRights(userModel);
	
		return flag;
	}

}
