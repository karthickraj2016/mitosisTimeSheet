package com.mitosis.timesheet.dao.daoImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.AccountDetailsDao;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class AccountDetailsDaoImpl extends BaseService implements AccountDetailsDao{

	@Override
	public UserDetailsModel getAccountDetails(Object userId) {
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();

		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"),userId));
			cq.select(root);
			userDetailsModel= entityManager.createQuery(cq).getSingleResult();
		

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return userDetailsModel;
	}


	@Override
	public boolean updateNewPassword(UserDetailsModel userDetailsModel) {
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"), userDetailsModel.getId()));
			cq.select(root);
	/*		userDetailsModel = entityManager.createQuery(cq).getSingleResult();*/	
			merge(userDetailsModel);
			commit();
			return true;

		}
		catch(Exception e){
			return false;

		}finally{
			close();
		}
	}


	@Override
	public boolean updateDetails(UserDetailsModel userDetailsModel) {
			UserDetailsModel userDetailsmodel = new UserDetailsModel();
		

		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"), userDetailsModel.getId()));
			cq.select(root);
	/*		userDetailsModel = entityManager.createQuery(cq).getSingleResult();*/	
			merge(userDetailsModel);
			commit();
			return true;

		}
		catch(Exception e){
			return false;

		}finally{
			close();
		}
	}


	@Override
	public boolean checkMailId(String MailId) {
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		boolean checkMailId = true;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("eMail"), MailId));
			cq.select(root);
			userDetailsModel = entityManager.createQuery(cq).getSingleResult();	
			checkMailId = false;
		}
		catch(Exception e){
		e.printStackTrace();

		}finally{
			close();
		}
		return checkMailId;
	}

}
