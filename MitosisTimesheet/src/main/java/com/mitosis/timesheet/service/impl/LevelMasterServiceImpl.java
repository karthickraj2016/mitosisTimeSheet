package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.LevelMasterDao;
import com.mitosis.timesheet.dao.daoImpl.LevelMasterDaoImpl;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.service.LevelMasterService;

public class LevelMasterServiceImpl implements LevelMasterService {

	LevelMasterDao levelDao=new LevelMasterDaoImpl();
	
	@Override
	public boolean addLevelDetails(LevelMasterModel levelModel) {
		
        boolean insert=false;
		
		insert=levelDao.addLevelDetails(levelModel);
		
		return insert;
	}

	@Override
	public boolean deleteLevelDetailEntry(int id) {
		
		boolean delete=false;
			
		delete=levelDao.deleteLevelDetailEntry(id);
			
		return delete;
	}

	@Override
	public List<LevelMasterModel> showLevelDetails() {

		List<LevelMasterModel> levelModel=new ArrayList<LevelMasterModel>();
		
		levelModel=levelDao.showLevelDetails();
		
		return levelModel;
	}

	@Override
	public int findCountOfEmpPerLevel(int level) {
		
		int numberOfEmployees;
		
		numberOfEmployees=levelDao.findCountOfEmpPerLevel(level);
		
		return numberOfEmployees;
	}

}
