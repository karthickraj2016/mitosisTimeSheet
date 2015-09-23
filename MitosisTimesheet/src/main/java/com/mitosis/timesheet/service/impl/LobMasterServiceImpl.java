package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.LobMasterDao;
import com.mitosis.timesheet.dao.daoImpl.LobMasterDaoImpl;
import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.service.LobMasterService;

public class LobMasterServiceImpl implements LobMasterService{
	
	LobMasterDao lobMasterDao = new LobMasterDaoImpl();

	@Override
	public List<LobModel> getLobList() {
		
		List<LobModel> lobList = new ArrayList<LobModel>();
		
		lobList = lobMasterDao.getLobList();
		
		return lobList;
	}

	@Override
	public boolean insert(LobModel lobModel) {
		
		boolean insert =lobMasterDao.insert(lobModel);
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validate(String lobName) {
		
		
		boolean validate = lobMasterDao.validate(lobName);
		return validate;
	}

}
