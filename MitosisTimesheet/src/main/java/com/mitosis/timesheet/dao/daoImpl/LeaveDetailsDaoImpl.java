package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
			cq.orderBy(qb.desc(root.get("id")));
			leaveModel = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return leaveModel;
	}
	
}
