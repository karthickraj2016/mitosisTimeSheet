package com.mitosis.timesheet.pojo;

import com.mitosis.timesheet.model.TimeSheetModel;

public class SummaryReport {
	
	public TimeSheetModel timeSheet;
	
	public double hourslist;
	
	public double sumHours;


	public double getSumHours() {
		return sumHours;
	}

	public void setSumHours(double sumHours) {
		this.sumHours = sumHours;
	}

	public TimeSheetModel getTimeSheet() {
		return timeSheet;
	}

	public void setTimeSheet(TimeSheetModel timeSheet) {
		this.timeSheet = timeSheet;
	}
	
	public double getHourslist() {
		return hourslist;
	}

	public void setHourslist(double hourslist) {
		this.hourslist = hourslist;
	}

}
