package com.mitosis.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;


import com.mitosis.timesheet.dao.CompanyDao;
import com.mitosis.timesheet.dao.daoImpl.CompanyDaoImpl;
import com.mitosis.timesheet.model.CompanyInfoModel;

import com.mitosis.timesheet.service.CompanyService;

public class CompanyServiceImpl implements CompanyService{
	CompanyDao companyDao=new CompanyDaoImpl();
	
	
	@Override
	public List<CompanyInfoModel>  showlist() {
		
		List<CompanyInfoModel> companyList = new ArrayList<CompanyInfoModel>();
		companyList = companyDao.showlist();
		return companyList;
	}
	@Override
	public CompanyInfoModel show() {
		
		return companyDao.show();
		 
	}
	@Override
	public boolean save(CompanyInfoModel company)
	{
		return companyDao.save(company);
	}
	@Override
	public boolean remove(int id)
	{
		return companyDao.remove(id);
	}

}
