package com.mitosis.timesheet.dao.daoImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.HeaderControlDao;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class HeaderControlDaoImpl extends BaseService implements HeaderControlDao{

	@Override
	public int adminFlag(int userId) {
		// TODO Auto-generated method stub
		
		int adminFlag =0;
		
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"),userId));
			cq.select(root);
			userDetailsModel=entityManager.createQuery(cq).getSingleResult();
			adminFlag = userDetailsModel.getAdminFlag();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return adminFlag;
	}

}
