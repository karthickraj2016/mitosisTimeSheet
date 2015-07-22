package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;

public interface EmployeeMasterService {

	public boolean addEmployeeDetails(EmployeeMasterModel masterModel);

	public boolean deleteEmployeeDetailEntry(int id);

	public List<EmployeeMasterModel> showEmployeeDetailsEntryList();

	public boolean updateEmployeeDetails(EmployeeMasterModel masterModel);

	public LevelMasterModel findEmployeeLevel(int yearsOfExp);

}
