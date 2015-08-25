package com.mitosis.timesheet.util;

import java.text.ParseException;
import java.util.Calendar;

import com.mitosis.timesheet.webservice.EmployeeMaster;

public class MyTask {
	
	
	 public void printCurrentTime() throws ParseException {  
		  // printing current system time  
		 EmployeeMaster hrMail = new EmployeeMaster();
		 
		 hrMail.ExpMailToHr();
		 
		 
		 }  
	

}
