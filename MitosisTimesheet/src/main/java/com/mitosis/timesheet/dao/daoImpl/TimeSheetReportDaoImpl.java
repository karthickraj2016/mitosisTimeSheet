package com.mitosis.timesheet.dao.daoImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.TimeSheetReportDao;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class TimeSheetReportDaoImpl extends BaseService implements TimeSheetReportDao {

	@Override
	public List<TimeSheetModel> getTimeSheetDetailReport(Date fromdate, Date todate,int employeeId) {
		// TODO Auto-generated method stub
		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("employeeId"), employeeId);
			Predicate condition2 = qb.greaterThan(fromDatePath, fromdate);
			Predicate condition3 = qb.lessThan(fromDatePath, todate);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(root);
			timeSheetDetailReport = entityManager.createQuery(cq).getResultList();
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetDetailReport;
	}
	
	
}
