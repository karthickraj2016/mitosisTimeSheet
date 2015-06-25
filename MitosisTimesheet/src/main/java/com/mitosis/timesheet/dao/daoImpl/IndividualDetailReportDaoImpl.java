package com.mitosis.timesheet.dao.daoImpl;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.mitosis.timesheet.dao.IndividualReportDao;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class IndividualDetailReportDaoImpl extends BaseService implements IndividualReportDao {

	@Override
	public List<TimeSheetModel> getIndividualReport(Date fromdate, Date todate,int employeeId) {
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

	@Override
	public double getTotalHours(Date fromdate, Date todate, int employeeId) {
		// TODO Auto-generated method stub
		
		double totalhours=0.0;
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Number> cq = qb.createQuery(Number.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("employeeId"), employeeId);
			Predicate condition2 = qb.greaterThan(fromDatePath, fromdate);
			Predicate condition3 = qb.lessThan(fromDatePath, todate);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(qb.sum(root.<Integer>get("hours")));
			totalhours=entityManager.createQuery(cq).getSingleResult().doubleValue();
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return totalhours;
	}

	
	
}
