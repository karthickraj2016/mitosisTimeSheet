package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.ExpenseMasterDao;
import com.mitosis.timesheet.dao.daoImpl.ExpenseMasterDaoImpl;
import com.mitosis.timesheet.model.ExpenseMasterModel;
import com.mitosis.timesheet.service.ExpenseMasterService;

public class ExpenseMasterServiceImpl implements ExpenseMasterService{
	
	ExpenseMasterDao expenseMasterDao = new ExpenseMasterDaoImpl();

	@Override
	public List<ExpenseMasterModel> showList() {
		List<ExpenseMasterModel> showList = new ArrayList<ExpenseMasterModel>();
		
		showList = expenseMasterDao.showList();
		return showList;
	}

	@Override
	public boolean insert(ExpenseMasterModel expenseMasterModel) {
		// TODO Auto-generated method stub
		boolean flag =  expenseMasterDao.insert(expenseMasterModel);
		return flag;
	}

	@Override
	public boolean delete(int id) {
		boolean flag =  expenseMasterDao.delete(id);
		return flag;
	}

}
