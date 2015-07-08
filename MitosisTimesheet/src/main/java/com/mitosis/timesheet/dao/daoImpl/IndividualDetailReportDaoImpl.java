package com.mitosis.timesheet.dao.daoImpl;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.mitosis.timesheet.dao.IndividualReportDao;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;
import com.mitosis.timesheet.util.BaseService;

public class IndividualDetailReportDaoImpl extends BaseService implements IndividualReportDao {

	@Override
	public List<TimeSheetModel> getIndividualDetailReportList(Date fromdate, Date todate,int employeeId) {
		// TODO Auto-generated method stub
		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("userDetails"), employeeId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromdate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, todate);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("date")));
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
			Predicate condition = qb.equal(root.get("userDetails"), employeeId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromdate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, todate);
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

	@Override
	public List<SummaryReport> getIndividualSummaryReportList(Date fromdate,
			Date todate, int employeeId) {
		
		List<TimeSheetModel> timeSheetModel = new ArrayList<TimeSheetModel>();
		List<SummaryReport> timeSheetList = new ArrayList<SummaryReport>();
		int i= 0;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("userDetails").get("id"), employeeId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromdate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, todate);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(root);	
			cq.groupBy(root.get("date"),root.get("project").get("projectName"));
			timeSheetModel= entityManager.createQuery(cq).getResultList();
			for(TimeSheetModel timeSheetModel1 : timeSheetModel){
				
				SummaryReport summaryReport = new SummaryReport();
				
				summaryReport.setTimeSheet(timeSheetModel1);

				timeSheetList.add(summaryReport);
				i++;				
			}
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetList;
	}

	@Override
	public List<SummaryReport> getIndividualSummaryReportHours(Date fromdate,
			Date toDate, int employeeId) {
		/*ArrayList<TimeSheetModel> hours = new ArrayList<TimeSheetModel>();*/
		
		TimeSheetModel timesheetModel = new TimeSheetModel();
		List<SummaryReport> timeSheetList = new ArrayList<SummaryReport>();
		List<Double> hours = new ArrayList<Double>();
		
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Double> cq = qb.createQuery(Double.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			/*Path<Integer> hourspath =root.get("hours");*/
			Predicate condition = qb.equal(root.get("userDetails").get("id"), employeeId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromdate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(qb.sum(root.<Double>get("hours")));	
			cq.groupBy(root.get("date"),root.get("project").get("projectName"));
			hours=entityManager.createQuery(cq).getResultList();
			for(int i =0;i<hours.size();i++){
			
				SummaryReport summaryReport = new SummaryReport();
				
				summaryReport.setHourslist(hours.get(i));

				timeSheetList.add(summaryReport);
								
			}

			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetList;
	}



	
	
	
}
