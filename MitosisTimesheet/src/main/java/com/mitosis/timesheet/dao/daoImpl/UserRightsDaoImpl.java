package com.mitosis.timesheet.dao.daoImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.UserRightsDao;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class UserRightsDaoImpl extends BaseService implements UserRightsDao {
	
	public List<UserDetailsModel> showUserList(){
		List<UserDetailsModel> userlist = null;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.select(root);
			cq.orderBy(qb.desc(root.get("id")));
			userlist = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return userlist;
	}
	
	public boolean updateRights(UserDetailsModel userModel){
				
		boolean flag = false ;

		try{
			begin();
			merge(userModel);
			commit();
			flag=true;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return flag;
	}

}
