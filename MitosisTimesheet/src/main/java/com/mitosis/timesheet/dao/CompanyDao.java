package com.mitosis.timesheet.dao;

import java.util.List;
import com.mitosis.timesheet.model.CompanyInfoModel;

public interface CompanyDao {
	public boolean save(CompanyInfoModel company);
	public boolean remove(int companyId);
	public List<CompanyInfoModel> showlist();
	public CompanyInfoModel show();
}
