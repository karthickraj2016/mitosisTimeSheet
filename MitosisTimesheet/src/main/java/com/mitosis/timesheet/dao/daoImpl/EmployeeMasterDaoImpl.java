package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.EmployeeMasterDao;
import com.mitosis.timesheet.model.EmployeeMasterModel;
import com.mitosis.timesheet.model.LevelMasterModel;
import com.mitosis.timesheet.model.LobModel;
import com.mitosis.timesheet.util.BaseService;

public class EmployeeMasterDaoImpl extends BaseService implements EmployeeMasterDao {

	@Override
	public List<LobModel> getLobList() {
		
		List<LobModel> lobList=new ArrayList<LobModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LobModel> cq = qb.createQuery(LobModel.class);
			Root<LobModel> root = cq.from(LobModel.class);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("id")));
			lobList = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}

		return lobList;
	}
		
	@Override
	public boolean addEmployeeDetails(EmployeeMasterModel masterModel) {
		
		boolean insert=false;
		
		try{
			begin();
			persist(masterModel);
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
	public boolean deleteEmployeeDetailEntry(int id) {
		
		EmployeeMasterModel masterModel=null;
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmployeeMasterModel> cq = qb.createQuery(EmployeeMasterModel.class);
			Root<EmployeeMasterModel> root = cq.from(EmployeeMasterModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			masterModel = entityManager.createQuery(cq).getSingleResult();
			remove(masterModel);
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
	public List<EmployeeMasterModel> showEmployeeDetailsEntryList() {

		List<EmployeeMasterModel> masterModel=new ArrayList<EmployeeMasterModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmployeeMasterModel> cq = qb.createQuery(EmployeeMasterModel.class);
			Root<EmployeeMasterModel> root = cq.from(EmployeeMasterModel.class);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("employeeId")));
			masterModel = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return masterModel;
	}

	@Override
	public boolean updateEmployeeDetails(EmployeeMasterModel masterModel) {
		
		boolean update=false;
		
		try{
			begin();
			merge(masterModel);
			commit();
			update=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return update;
	}

	@Override
	public LevelMasterModel findEmployeeLevel(int yearsOfExp) {
		
		LevelMasterModel levelModel=new LevelMasterModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<LevelMasterModel> cq = qb.createQuery(LevelMasterModel.class);
			Root<LevelMasterModel> root = cq.from(LevelMasterModel.class);
			Path<Integer> yearFrom = root.get("yearFrom");
			Path<Integer> yearTo = root.get("yearTo");
		    Predicate condition = qb.ge(qb.literal(yearsOfExp), yearFrom);
			Predicate condition1 = qb.lt(qb.literal(yearsOfExp), yearTo);
			Predicate conditions=qb.and(condition,condition1);
			cq.where(conditions);
			cq.select(root);
			levelModel = entityManager.createQuery(cq).getSingleResult();
		    commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return levelModel;
	}

	@Override
	public boolean employeeIdValidation(String employeeId, boolean empId) {
	

		EmployeeMasterModel masterModel=new EmployeeMasterModel();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmployeeMasterModel> cq = qb.createQuery(EmployeeMasterModel.class);
			Root<EmployeeMasterModel> root = cq.from(EmployeeMasterModel.class);
		    cq.where(qb.equal(root.get("employeeId"), employeeId));
		    cq.select(root);
		    masterModel=entityManager.createQuery(cq).getSingleResult();
		    if(masterModel!=null){
		    	empId=true;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return empId;
	}
	
	@Override
	public boolean employeeValidation(int userId, boolean empId) {
	

		EmployeeMasterModel masterModel=new EmployeeMasterModel();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<EmployeeMasterModel> cq = qb.createQuery(EmployeeMasterModel.class);
			Root<EmployeeMasterModel> root = cq.from(EmployeeMasterModel.class);
		    cq.where(qb.equal(root.get("employee").get("id"), userId));
		    cq.select(root);
		    masterModel=entityManager.createQuery(cq).getSingleResult();
		    if(masterModel!=null){
		    	empId=true;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return empId;
	}

}
