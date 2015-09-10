package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.LeaveReportDao;
import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class LeaveReportDaoImpl  extends BaseService implements LeaveReportDao{

	@Override
	public List<LeaveDetailsModel> leaveDetailList(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		
		List<LeaveDetailsModel> leaveDetailList = new ArrayList<LeaveDetailsModel>();
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LeaveDetailsModel> cq = qb.createQuery(LeaveDetailsModel.class);
			Root<LeaveDetailsModel> root = cq.from(LeaveDetailsModel.class);
			Path<Date> fromdate = root.get("fromDate");
			Path<Date> todate = root.get("toDate");
			Predicate condition1 = qb.greaterThanOrEqualTo(fromdate,fromDate);
			Predicate condition2 = qb.lessThanOrEqualTo(todate,toDate);
			Predicate conditions = qb.and(condition1,condition2);
			cq.where(conditions);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("fromDate")),qb.asc(root.get("employee").get("name")));
			leaveDetailList = entityManager.createQuery(cq).getResultList();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return leaveDetailList;
		
		
		
	}

}
