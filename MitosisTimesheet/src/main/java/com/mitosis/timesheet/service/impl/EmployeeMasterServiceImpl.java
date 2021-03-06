package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.EmployeeMasterDao;
import com.mitosis.timesheet.dao.daoImpl.EmployeeMasterDaoImpl;
import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.service.EmployeeMasterService;
import com.mitosis.timesheet.webservice.EmployeeMaster;

public class EmployeeMasterServiceImpl implements EmployeeMasterService {

	EmployeeMasterDao masterDao=new EmployeeMasterDaoImpl();
	
	@Override
	public List<LobModel> getLobList() {
		
		List<LobModel> lobList=new ArrayList<LobModel>();
		
		lobList=masterDao.getLobList();
		
		return lobList;
	}
	
	@Override
	public boolean addEmployeeDetails(EmployeeMasterModel masterModel) {
		
		boolean insert=false;
		
		insert=masterDao.addEmployeeDetails(masterModel);
		
		return insert;
	}

	@Override
	public boolean deleteEmployeeDetailEntry(int id) {
        
		boolean delete=false;
		
		delete=masterDao.deleteEmployeeDetailEntry(id);
		
		return delete;
	}

	@Override
	public List<EmployeeMasterModel> showEmployeeDetailsEntryList() {
		
		List<EmployeeMasterModel> masterModel=new ArrayList<EmployeeMasterModel>();
		
		masterModel=masterDao.showEmployeeDetailsEntryList();
		
		return masterModel;
	}

	@Override
	public boolean updateEmployeeDetails(EmployeeMasterModel masterModel) {
		
		boolean insert=false;
		
		insert=masterDao.updateEmployeeDetails(masterModel);
		
		return insert;
	}

	@Override
	public LevelMasterModel findEmployeeLevel(int yearsOfExp) {
	
		LevelMasterModel levelModel=new LevelMasterModel();
		
		levelModel=masterDao.findEmployeeLevel(yearsOfExp);
		
		return levelModel;
	}

	@Override
	public boolean employeeIdValidation(String employeeId, boolean empId) {
		
		empId=masterDao.employeeIdValidation(employeeId,empId);
		
		return empId;
		
	}

	@Override
	public boolean employeeValidation(int userId, boolean empId) {
       
		empId=masterDao.employeeValidation(userId,empId);
		
		return empId;
	}


}
