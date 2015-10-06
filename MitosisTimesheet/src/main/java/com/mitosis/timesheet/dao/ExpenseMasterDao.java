package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.ExpenseMasterModel;

public interface ExpenseMasterDao {
	
	
	public List<ExpenseMasterModel> showList();

	public boolean insert(ExpenseMasterModel expenseMasterModel);

	public boolean delete(int id);

}
