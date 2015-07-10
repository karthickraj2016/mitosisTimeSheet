package com.mitosis.timesheet.service.impl;

import java.beans.beancontext.BeanContextServicesListener;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import com.mitosis.timesheet.dao.AccountDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.AccountDetailsDaoImpl;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.pojo.UserDetailsVo;
import com.mitosis.timesheet.service.AccountDetailsService;


public class AccountDetailsServiceImpl implements AccountDetailsService{
	


	
	AccountDetailsDao accountDetailsDao = new AccountDetailsDaoImpl();

	@Override
	public UserDetailsVo getAccountDetails(Object userId) {
		
		Object Id = userId;		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		userDetailsModel = accountDetailsDao.getAccountDetails(Id);
		
		UserDetailsVo userDetailsVo = new UserDetailsVo();
		
		BeanUtils.copyProperties(userDetailsModel, userDetailsVo);
		

		return userDetailsVo;
	}


	@Override
	public boolean updateNewPassword(UserDetailsVo userDetailsVo) {
		boolean updatepassword;
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();

		
		BeanUtils.copyProperties(userDetailsVo, userDetailsModel);
		
		System.out.println(userDetailsModel);
		updatepassword = accountDetailsDao.updateNewPassword(userDetailsModel);
		
		
		return updatepassword;
	}


	@Override
	public boolean updateDetails(UserDetailsVo userDetailsVo) {
		boolean updatepassword;
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		BeanUtils.copyProperties(userDetailsVo, userDetailsModel);
		
		updatepassword = accountDetailsDao.updateDetails(userDetailsModel);
		
		
		return updatepassword;
	}


	@Override
	public boolean checkMailId(String mailId) {
		// TODO Auto-generated method stub
		
		boolean checkmailid = accountDetailsDao.checkMailId(mailId);
		return checkmailid;
	}

	

	


}
