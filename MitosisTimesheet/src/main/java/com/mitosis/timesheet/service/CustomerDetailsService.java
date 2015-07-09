package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CustomerDetailsModel;

public interface CustomerDetailsService {

	public boolean addCustomerDetails(CustomerDetailsModel customerModel);

	public boolean updateCustomerDetails(CustomerDetailsModel customerModel);

	public boolean removeCustomerById(int customerId);

	public List<CustomerDetailsModel> showCustomerlist();

	public boolean mailValidation(String mail);

}
