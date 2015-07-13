package com.mitosis.timesheet.service.impl;


import com.mitosis.timesheet.dao.HeaderControlDao;
import com.mitosis.timesheet.dao.daoImpl.HeaderControlDaoImpl;

import com.mitosis.timesheet.service.HeaderControlService;

public class HeaderControlServiceImpl implements HeaderControlService{
	
	HeaderControlDao headerControlDAO = new HeaderControlDaoImpl();
	

	@Override
	public int adminFlag(int userId) {
		
		int adminFlag = headerControlDAO.adminFlag(userId);	
		return adminFlag;
	}

}
