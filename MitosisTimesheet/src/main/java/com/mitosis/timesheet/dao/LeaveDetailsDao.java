package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.LeaveDetailsModel;

public interface LeaveDetailsDao {

 public	boolean insertLeaveEntry(LeaveDetailsModel leaveModel);

 public boolean updateLeaveEntry(LeaveDetailsModel leaveModel);

 public boolean deleteLeaveEntry(int id);

public List<LeaveDetailsModel> showLeaveEntryDetails();

public boolean validateEntry(LeaveDetailsModel leaveModel,boolean validation);

public boolean validateEntryForUpdate(LeaveDetailsModel leaveModel,
		boolean validation);

}
