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
		return insert;
	}

	@Override
	public boolean validate(LobModel lobModel) {
		
		
		boolean validate = lobMasterDao.validate(lobModel);
		return validate;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		
		boolean delete =lobMasterDao.delete(id);
		return delete;
	}

}
