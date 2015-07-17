package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mitosis.timesheet.dao.CustomerDetailsDao;
import com.mitosis.timesheet.dao.daoImpl.CustomerDetailsDaoImpl;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.service.CustomerDetailsService;

public class CustomerDetailsServiceImpl implements CustomerDetailsService {

	@Override
	public boolean addCustomerDetails(CustomerDetailsModel customerModel) {
		
		boolean flag=false;
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		flag=customerDao.addCustomerDetails(customerModel);
				
		return flag;
	}

	@Override
	public boolean updateCustomerDetails(CustomerDetailsModel customerModel) {
        
		boolean flag=false;
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		flag=customerDao.updateCustomerDetails(customerModel);
				
		return flag;
	}

	@Override
	public boolean removeCustomerById(int customerId) {
		
        boolean flag=false;
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		flag=customerDao.removeCustomerById(customerId);
				
		return flag;
	}

	@Override
	public List<CustomerDetailsModel> showCustomerlist() {
		
         List<CustomerDetailsModel> customerList=new ArrayList<CustomerDetailsModel>();
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		customerList=customerDao.showCustomerlist();
				
		return customerList;
	}

	@Override
	public boolean nameValidation(String name) {
		
		boolean validation=false;
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		validation=customerDao.nameValidation(name);
	
		return validation;
	}
	
	@Override
	public boolean mailValidation(String mail) {
		
		boolean validation=false;
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		validation=customerDao.mailValidation(mail);
	
		return validation;
	}

	@Override
	public boolean getProjectStatus(int customerId) {
		
		
		
		CustomerDetailsDao customerDao=new CustomerDetailsDaoImpl();
		
		boolean projectstatus=customerDao.getProjectStatus(customerId);
	
		return projectstatus;
	}

}
