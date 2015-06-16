package com.mitosis.timesheet.dao;

import com.mitosis.timesheet.model.UserDetailsModel;


public interface SignupDao {
  
  public UserDetailsModel login(String userName, String password);
  
  public boolean loginValidation(String userName, String password);
	
  public UserDetailsModel createUser(UserDetailsModel userModel) throws Exception;
  
  public int getUserId(String empMail,String empName) throws Exception;
  
  public UserDetailsModel userActivate(int id) throws Exception;
  
  public boolean checkEmail(String empMail) throws Exception;
  
  public boolean checkUsername(String empName) throws Exception;
  
  public UserDetailsModel getname(Object userId);
  
  public UserDetailsModel forgotpassword(String mailId);

  public boolean updatepassword(int id, String password);

}
