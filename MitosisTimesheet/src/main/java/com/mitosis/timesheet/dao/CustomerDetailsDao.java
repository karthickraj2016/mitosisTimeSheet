package com.mitosis.timesheet.dao;

import java.util.List;

import com.mitosis.timesheet.model.CustomerDetailsModel;

public interface CustomerDetailsDao {

	public boolean addCustomerDetails(CustomerDetailsModel customerModel);

	public boolean updateCustomerDetails(CustomerDetailsModel customerModel);

	public boolean removeCustomerById(int customerId);

	public List<CustomerDetailsModel> showCustomerlist();

	public boolean nameValidation(String name);
	
	public boolean mailValidation(String mail);

}
