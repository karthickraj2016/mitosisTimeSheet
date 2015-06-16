package com.mitosis.timesheet.service.impl;

import com.mitosis.timesheet.dao.ForgotPasswordDao;
import com.mitosis.timesheet.dao.ProjectDAO;
import com.mitosis.timesheet.dao.daoImpl.ForgotPasswordDaoImpl;
import com.mitosis.timesheet.dao.daoImpl.ProjectDAOImpl;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.service.ForgotPasswordService;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	
	ForgotPasswordDao forgotpasswordDao = new ForgotPasswordDaoImpl();
	
	@Override
	public UserDetailsModel getEmailId(String emailId) {
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		userDetailsModel = forgotpasswordDao.getEmailid(emailId);
		
		return userDetailsModel;
	}

	@Override
	public boolean updatepassword(UserDetailsModel userDetailsModel) {
		boolean flag ;
		
		flag = forgotpasswordDao.update(userDetailsModel);
		return flag;
	}

	@Override
	public boolean setpasswordflag(UserDetailsModel userDetailsModel) {
		// TODO Auto-generated method stub
		
		
		boolean flag;

		flag = forgotpasswordDao.updatepasswordflag(userDetailsModel);
		return flag;
	}

	@Override
	public UserDetailsModel getIsreset(int id) {
		// TODO Auto-generated method stub
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		userDetailsModel = forgotpasswordDao.getIsreset(id);
		
		return userDetailsModel;
	}


}
