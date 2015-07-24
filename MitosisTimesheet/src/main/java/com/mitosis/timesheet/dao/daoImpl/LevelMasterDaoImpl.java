package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.LevelMasterDao;
import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.util.BaseService;

public class LevelMasterDaoImpl  extends BaseService implements LevelMasterDao {

	@Override
	public boolean addLevelDetails(LevelMasterModel levelModel) {
       
		boolean insert=false;
		
		try{
			begin();
			merge(levelModel);
			commit();
			insert=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return insert;
	}

	@Override
	public boolean deleteLevelDetailEntry(int id) {
     
		LevelMasterModel levelModel=null;
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LevelMasterModel> cq = qb.createQuery(LevelMasterModel.class);
			Root<LevelMasterModel> root = cq.from(LevelMasterModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			levelModel = entityManager.createQuery(cq).getSingleResult();
			remove(levelModel);
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
	public List<LevelMasterModel> showLevelDetails() {

		List<LevelMasterModel> levelModel=new ArrayList<LevelMasterModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LevelMasterModel> cq = qb.createQuery(LevelMasterModel.class);
			Root<LevelMasterModel> root = cq.from(LevelMasterModel.class);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("id")));
			levelModel = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return levelModel;
	}

	@Override
	public int findCountOfEmpPerLevel(int level) {
		
		int numberOfEmployees = 0;
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmployeeMasterModel> cq = qb.createQuery(EmployeeMasterModel.class);
			Root<EmployeeMasterModel> root = cq.from(EmployeeMasterModel.class);
			Predicate condition = qb.equal(root.get("level"), level);
			Predicate condition1 = qb.equal(root.get("billable"),"Yes");
			Predicate conditions = qb.and(condition, condition1);
			cq.where(conditions);
			cq.select(root);
			numberOfEmployees=entityManager.createQuery(cq).getResultList().size();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return numberOfEmployees;
	}

}


