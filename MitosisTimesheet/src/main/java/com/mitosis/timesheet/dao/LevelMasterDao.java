package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.LevelMasterModel;

public interface LevelMasterDao {

	public boolean addLevelDetails(LevelMasterModel levelModel);

	public boolean deleteLevelDetailEntry(int id);

	public List<LevelMasterModel> showLevelDetails();

	public int findCountOfEmpPerLevel(int level);

}
