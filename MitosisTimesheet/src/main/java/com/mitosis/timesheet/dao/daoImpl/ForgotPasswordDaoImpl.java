package com.mitosis.timesheet.dao.daoImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.ForgotPasswordDao;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;


public class ForgotPasswordDaoImpl extends BaseService implements ForgotPasswordDao {

	@Override
	public UserDetailsModel getEmailid(String emailId) {
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("eMail"),emailId));
			cq.select(root);
			System.out.println(entityManager.createQuery(cq));
			userDetailsModel= entityManager.createQuery(cq).getSingleResult();
		

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return userDetailsModel;
	}

	@Override
	public boolean update(UserDetailsModel userDetailsModel) {

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
	public boolean updatepasswordflag(UserDetailsModel userdetailsModel) {
		
		
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("eMail"),userdetailsModel.geteMail()));
			cq.select(root);
			userDetailsModel = entityManager.createQuery(cq).getSingleResult();
			userDetailsModel.setReset(userdetailsModel.IsReset());
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
	public UserDetailsModel getIsreset(int id) {
		
		UserDetailsModel userdetailsModel = new UserDetailsModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"),id));
			cq.select(root);
	
			userdetailsModel= entityManager.createQuery(cq).getSingleResult();
	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return userdetailsModel;
	}


	

}
