package com.mitosis.timesheet.service;

import java.util.List;

import com.mitosis.timesheet.model.CompanyInfoModel;

public interface CompanyService {
	public boolean save(CompanyInfoModel company);
	//public boolean update(CompanyInfoModel company);
	public boolean remove(int Companyid);
	public List<CompanyInfoModel> showlist();
	public CompanyInfoModel show();

}
