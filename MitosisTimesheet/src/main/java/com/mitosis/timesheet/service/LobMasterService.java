package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.LobModel;

public interface LobMasterService {
	
	
	public List<LobModel> getLobList();

	public boolean insert(LobModel lobModel);

	public boolean validate(LobModel lobModel);

	public boolean delete(int id);

}
