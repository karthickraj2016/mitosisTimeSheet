package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.LeaveDetailsModel;

public interface LeaveDetailsService {

	public boolean insertLeaveEntry(LeaveDetailsModel leaveModel);

	public boolean updateLeaveEntry(LeaveDetailsModel leaveModel);

	public boolean deleteLeaveEntry(int id);

	public List<LeaveDetailsModel> showLeaveEntryList();

	public boolean validateEntry(LeaveDetailsModel leaveModel);

}
