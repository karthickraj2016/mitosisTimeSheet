package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.model.LobModel;

public interface EmployeeMasterDao {
	
	public List<LobModel> getLobList();

	public boolean addEmployeeDetails(EmployeeMasterModel masterModel);

	public boolean deleteEmployeeDetailEntry(int id);

	public List<EmployeeMasterModel> showEmployeeDetailsEntryList();

	public boolean updateEmployeeDetails(EmployeeMasterModel masterModel);

	public LevelMasterModel findEmployeeLevel(int yearsOfExp);

	public boolean employeeIdValidation(String employeeId, boolean empId);

	public boolean employeeValidation(int userId, boolean empId);
	
}
