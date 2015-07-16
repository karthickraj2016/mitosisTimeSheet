package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.LeaveDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.LeaveDetailsDaoImpl;
import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.service.LeaveDetailsService;

public class LeaveDetailsServiceImpl implements LeaveDetailsService {


	LeaveDetailsDao leaveDao=new LeaveDetailsDaoImpl();
	
	@Override
	public  boolean insertLeaveEntry(LeaveDetailsModel leaveModel) {
		
		boolean flag= false;
		
		flag = leaveDao.insertLeaveEntry(leaveModel);
			
		return flag;
	}
	
	@Override
	public  boolean updateLeaveEntry(LeaveDetailsModel leaveModel) {
		
		boolean flag= false;
		
		flag = leaveDao.updateLeaveEntry(leaveModel);
			
		return flag;
	}
	
	@Override
	public  boolean deleteLeaveEntry(int id) {
		
		boolean flag= false;
		
		flag = leaveDao.deleteLeaveEntry(id);
			
		return flag;
	}
	
	@Override
	public  List<LeaveDetailsModel> showLeaveEntryList() {
		
		List<LeaveDetailsModel> leaveModel=new ArrayList<LeaveDetailsModel>();
		
		leaveModel = leaveDao.showLeaveEntryDetails();
			
		return leaveModel;
	}

	@Override
	public boolean validateEntry(LeaveDetailsModel leaveModel,boolean validation) {
		
		validation = leaveDao.validateEntry(leaveModel,validation);
			
		return validation;
	}

	@Override
	public boolean validateEntryForUpdate(LeaveDetailsModel leaveModel,
			boolean validation) {
		validation = leaveDao.validateEntryForUpdate(leaveModel,validation);
		
		return validation;
	}


}
