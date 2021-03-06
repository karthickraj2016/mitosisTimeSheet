package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.LobMasterDao;
import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.util.BaseService;

public class LobMasterDaoImpl extends BaseService implements LobMasterDao{

	@Override
	public List<LobModel> getLobList() {
	
		List<LobModel> lobModel = new ArrayList<LobModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LobModel> cq = qb.createQuery(LobModel.class);
			Root<LobModel> root = cq.from(LobModel.class);
			cq.select(root);		
			lobModel = entityManager.createQuery(cq).getResultList();
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return lobModel;
	}

	@Override
	public boolean insert(LobModel lobModel) {
		boolean flag = false ;
		
		try{
			begin();
			merge(lobModel);
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
	public boolean validate(LobModel LobModel) {
		
		boolean validate = false;
	
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LobModel> cq = qb.createQuery(LobModel.class);
			Root<LobModel> root = cq.from(LobModel.class);
			cq.select(root);	
			Predicate conditions = qb.equal(root.get("lobName"),LobModel.getLobName());
			if(LobModel.getId() != null){	
				Predicate condition1 = qb.equal(root.get("lobName"),LobModel.getLobName());
				Predicate condition2 = qb.notEqual(root.get("id"),LobModel.getId());
				conditions = qb.and(condition1,condition2);
			}
			cq.where(conditions);
			entityManager.createQuery(cq).getSingleResult();
			validate = true;
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return validate;
	}

	@Override
	public boolean delete(int id) {
		LobModel lobModel = null;
	
		boolean deleted = false;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LobModel> cq = qb.createQuery(LobModel.class);
			Root<LobModel> root = cq.from(LobModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			lobModel = entityManager.createQuery(cq).getSingleResult();
			remove(lobModel);
			commit();
			deleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return deleted;

	}

}
