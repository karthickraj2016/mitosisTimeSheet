package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.eclipse.persistence.internal.expressions.ParameterExpression;
import org.hibernate.annotations.Where;

import com.mitosis.timesheet.dao.LeaveDetailsDao;
import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class LeaveDetailsDaoImpl  extends BaseService implements LeaveDetailsDao {
	 
	boolean flag=false;
	
	public boolean insertLeaveEntry(LeaveDetailsModel leaveModel) {
		try {
			begin();
			persist(leaveModel);
			commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	public boolean updateLeaveEntry(LeaveDetailsModel leaveModel) {
		try {
			begin();
			merge(leaveModel);
			commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	@Override
	public boolean deleteLeaveEntry(int id) {
		
		LeaveDetailsModel leaveModel = new LeaveDetailsModel();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LeaveDetailsModel> cq = qb.createQuery(LeaveDetailsModel.class);
			Root<LeaveDetailsModel> root = cq.from(LeaveDetailsModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			leaveModel = entityManager.createQuery(cq).getSingleResult();
			remove(leaveModel);
			commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	@Override
	public List<LeaveDetailsModel> showLeaveEntryDetails() {
		
		List<LeaveDetailsModel> leaveModel=new ArrayList<LeaveDetailsModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LeaveDetailsModel> cq = qb.createQuery(LeaveDetailsModel.class);
			Root<LeaveDetailsModel> root = cq.from(LeaveDetailsModel.class);
			cq.select(root);
			cq.orderBy(qb.desc(root.get("fromDate")),qb.asc(root.get("employee").get("name")));
			leaveModel = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return leaveModel;
	}

	@Override
	public boolean validateEntry(LeaveDetailsModel leaveModel,boolean validation) {
	
		List<LeaveDetailsModel>  leavedetailslist = new ArrayList<LeaveDetailsModel>();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LeaveDetailsModel> cq = qb.createQuery(LeaveDetailsModel.class);
			Root<LeaveDetailsModel> root = cq.from(LeaveDetailsModel.class);
			Path<Date> fromDate = root.get("fromDate");
			Path<Date> toDate = root.get("toDate");
			Predicate subcondition1 =qb.between(qb.literal(leaveModel.getFromDate()),fromDate,toDate);
			Predicate subcondition2 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcorrelation1 =qb.and(subcondition1,subcondition2);
			Predicate subcondition3 =qb.between(qb.literal(leaveModel.getToDate()),fromDate,toDate);
			Predicate subcondition4 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcorrelation2 =qb.and(subcondition3,subcondition4);
			Predicate subcondition5 =qb.between(fromDate,leaveModel.getFromDate(),leaveModel.getToDate());
			Predicate subcondition6 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcorrelation3 =qb.and(subcondition5,subcondition6);
			Predicate subcondition7 =qb.between(toDate,leaveModel.getFromDate(),leaveModel.getToDate());
			Predicate subcondition8 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcorrelation4 =qb.and(subcondition7,subcondition8);
			Predicate TotalConditions = qb.or(subcorrelation1,subcorrelation2,subcorrelation3,subcorrelation4);
			cq.where(TotalConditions);
			cq.select(root);
				
			leavedetailslist= entityManager.createQuery(cq).getResultList();
			commit(); 
			if(leavedetailslist.size()>0){
		    	validation = true;
		    }
		}catch(Exception e){
		}finally{
			close();
		}
		return validation;
	}

	@Override
	public boolean validateEntryForUpdate(LeaveDetailsModel leaveModel,
			boolean validation) {
		List<LeaveDetailsModel>  leavedetailslist = new ArrayList<LeaveDetailsModel>();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LeaveDetailsModel> cq = qb.createQuery(LeaveDetailsModel.class);
			Root<LeaveDetailsModel> root = cq.from(LeaveDetailsModel.class);
			Path<Date> fromDate = root.get("fromDate");
			Path<Date> toDate = root.get("toDate");
			Predicate subcondition1 =qb.between(qb.literal(leaveModel.getFromDate()),fromDate,toDate);
			Predicate subcondition2 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcondition3 =root.get("id").in(leaveModel.getId());
			Predicate subcondition4 = qb.not(subcondition3);
			Predicate subcorrelation1 =qb.and(subcondition1,subcondition2,subcondition4);
			Predicate subcondition5 =qb.between(qb.literal(leaveModel.getToDate()),fromDate,toDate);
			Predicate subcondition6 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcondition7 =root.get("id").in(leaveModel.getId());
			Predicate subcondition8 = qb.not(subcondition7);
			Predicate subcorrelation2 =qb.and(subcondition5,subcondition6,subcondition8);
			Predicate subcondition9 =qb.between(fromDate,leaveModel.getFromDate(),leaveModel.getToDate());
			Predicate subcondition10 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcondition11 =root.get("id").in(leaveModel.getId());
			Predicate subcondition12 =qb.not(subcondition11);
			Predicate subcorrelation3 =qb.and(subcondition9,subcondition10,subcondition12);
			Predicate subcondition13 =qb.between(toDate,leaveModel.getFromDate(),leaveModel.getToDate());
			Predicate subcondition14 =qb.equal(root.get("employee").get("id"),leaveModel.getEmployee().getId());
			Predicate subcondition15 = root.get("id").in(leaveModel.getId());
			Predicate subcondition16 = qb.not(subcondition15);
			Predicate subcorrelation4 =qb.and(subcondition13,subcondition14,subcondition16);
			Predicate TotalConditions = qb.or(subcorrelation1,subcorrelation2,subcorrelation3,subcorrelation4);
			cq.where(TotalConditions);
			cq.select(root);
				
			leavedetailslist= entityManager.createQuery(cq).getResultList();
			commit(); 
			if(leavedetailslist.size()>0){
		    	validation = true;
		    }
		}catch(Exception e){
		}finally{
			close();
		}
		return validation;
	}
	
}
