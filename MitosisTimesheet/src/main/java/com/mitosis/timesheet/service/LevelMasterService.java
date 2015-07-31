package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;

public interface LevelMasterService {

	public boolean addLevelDetails(LevelMasterModel levelModel);

	public boolean deleteLevelDetailEntry(int id);

	public List<LevelMasterModel> showLevelDetails();

	public int findCountOfEmpPerLevel(int level);

	public List<EmployeeMasterModel> getEmployeesByLevel(int level);

}
