package com.mitosis.timesheet.dao.daoImpl;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.TimeSheetDAO;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.util.BaseService;

public class TimeSheetDAOImpl extends BaseService implements TimeSheetDAO{

	@Override
	public boolean Create(TimeSheetModel timeSheetModel) {
		boolean flag = false ;
		TimeSheetModel timesheetModel = new TimeSheetModel();
		try{
			begin();
      merge(timeSheetModel);
			commit();
			flag=true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return flag;
		
	}

	@Override
	public List<TimeSheetModel> showlist(Object userId) {
		List<TimeSheetModel> timesheetlist = null;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			cq.where(qb.equal(root.get("employeeId"),userId));
			cq.select(root);
			cq.orderBy(qb.desc(root.get("date")), qb.desc(root.get("id")));
			timesheetlist = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timesheetlist;
	}

  @Override
  public boolean deleteTimesheet(int timesheetId) {
    TimeSheetModel timesheetModel = null;
    try {
      begin();
      entityManager.getEntityManagerFactory().getCache().evictAll();
      CriteriaBuilder qb = entityManager.getCriteriaBuilder();
      CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
      Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
      cq.where(qb.equal(root.get("id"), timesheetId));
      cq.select(root);
      timesheetModel = entityManager.createQuery(cq).getSingleResult();
      remove(timesheetModel);
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
public double hourslist(int userId, Date date) {
	
	double hourstaken = 0;
	try {
	      begin();
	      entityManager.getEntityManagerFactory().getCache().evictAll();
	      CriteriaBuilder qb = entityManager.getCriteriaBuilder();
	      CriteriaQuery<Number> query = qb.createQuery(Number.class);
	      Root<TimeSheetModel> root = query.from(TimeSheetModel.class);
	      Predicate condition = qb.equal(root.get("employeeId"), userId);
	      Predicate condition2 = qb.equal(root.get("date"), date);
	      Predicate conditions = qb.and(condition, condition2);
	      query.where(conditions);
	      query.select(qb.sum(root.<Integer>get("hours")));
	      hourstaken=entityManager.createQuery(query).getSingleResult().doubleValue();
	} catch (Exception e) {
	      e.printStackTrace();
	    }finally {
	        close();
	    }
		return hourstaken;
}

@Override
public double getprevioushours(int id) {
	double previoushours = 0;
	try {
	      begin();
	      entityManager.getEntityManagerFactory().getCache().evictAll();
	      CriteriaBuilder qb = entityManager.getCriteriaBuilder();
	      CriteriaQuery<Number> query = qb.createQuery(Number.class);
	      Root<TimeSheetModel> root = query.from(TimeSheetModel.class);
	      Predicate condition = qb.equal(root.get("id"), id);
	      query.where(condition);
	      query.select(qb.sum(root.<Integer>get("hours")));
	     previoushours=entityManager.createQuery(query).getSingleResult().doubleValue();
	} catch (Exception e) {
	      e.printStackTrace();
	    }finally {
	        close();
	    }
	return previoushours;
}

@Override
public List<ProjectModel> getprojectList() {
	List<ProjectModel> projectlist = new ArrayList<ProjectModel>();
	try{
		begin();
		entityManager.getEntityManagerFactory().getCache().evictAll();
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
		Root<ProjectModel> root = cq.from(ProjectModel.class);
		cq.select(root);
		cq.orderBy(qb.asc(root.get("projectName")));
		projectlist = entityManager.createQuery(cq).getResultList();
		System.out.println(projectlist);
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		close();
	}
	return projectlist;
}
     
  }


