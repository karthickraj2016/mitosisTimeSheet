package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;

public interface EmployeeMasterDao {

	public boolean addEmployeeDetails(EmployeeMasterModel masterModel);

	public boolean deleteEmployeeDetailEntry(int id);

	public List<EmployeeMasterModel> showEmployeeDetailsEntryList();

	public boolean updateEmployeeDetails(EmployeeMasterModel masterModel);

	public LevelMasterModel findEmployeeLevel(int yearsOfExp);

}