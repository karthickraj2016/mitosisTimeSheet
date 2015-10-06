package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.ExpenseMasterModel;


public interface ExpenseMasterService {
	
	
	public List<ExpenseMasterModel> showList();

	public boolean insert(ExpenseMasterModel expenseMasterModel);

	public boolean delete(int id);
	

}
