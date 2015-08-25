package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.webservice.EmployeeMaster;

public interface EmployeeMasterService {
	
	public List<LobModel> getLobList();

	public boolean addEmployeeDetails(EmployeeMasterModel masterModel);

	public boolean deleteEmployeeDetailEntry(int id);

	public List<EmployeeMasterModel> showEmployeeDetailsEntryList();

	public boolean updateEmployeeDetails(EmployeeMasterModel masterModel);

	public LevelMasterModel findEmployeeLevel(int yearsOfExp);

	public boolean employeeIdValidation(String employeeId, boolean empId);

	public boolean employeeValidation(int userId, boolean empId);


}
