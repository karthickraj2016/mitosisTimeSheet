package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.LobModel;

public interface LobMasterDao {
	
	
	public List<LobModel> getLobList ();

	public boolean insert(LobModel lobModel);

	public boolean validate(LobModel lobModel);

	public boolean delete(int id);

}
