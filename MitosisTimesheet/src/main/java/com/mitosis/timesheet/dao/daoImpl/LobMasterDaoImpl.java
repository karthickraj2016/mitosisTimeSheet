package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.LobMasterDao;
import com.mitosis.timesheet.model.LobModel;
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
	public boolean validate(String lobName) {
		
		boolean validate = false;
		
		LobModel lobModel = new LobModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LobModel> cq = qb.createQuery(LobModel.class);
			Root<LobModel> root = cq.from(LobModel.class);
			cq.select(root);		
			cq.where(qb.equal(root.get("lobName"),lobName));
			lobModel =  entityManager.createQuery(cq).getSingleResult();
			validate = true;
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return validate;
	}

}
