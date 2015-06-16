package com.mitosis.timesheet.dao.daoImpl;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.SignupDao;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;



public class SignupDaoImpl  extends BaseService implements SignupDao {

	boolean flag = true;

	UserDetailsModel userModel=new UserDetailsModel();

	public boolean loginValidation(String userName, String password){

		UserDetailsModel userDetailsModels = null;

		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);

			Predicate condition = qb.equal(root.get("userName"), userName);
			Predicate condition2 = qb.equal(root.get("password"), password);
			Predicate condition3 = qb.equal(root.get("active"), true);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(root);
			userDetailsModels = entityManager.createQuery(cq).getSingleResult();
			commit(); 
		} catch (Exception e) {
			return false;
		} finally {
			close();
		}

		return flag;
	}

	public UserDetailsModel createUser(UserDetailsModel userModel) throws Exception{

		try{
			begin();
			persist(userModel);
			commit();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return userModel;
	}

	@Override
	public boolean checkEmail(String empMail) throws Exception {

		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("eMail"), empMail));
			cq.select(root);
			userModel = entityManager.createQuery(cq).getSingleResult();

		}catch(Exception e){
			return false;
		}finally{
			close();
		}
		return flag;

	}

	@Override
	public boolean checkUsername(String empName) throws Exception{

		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("userName"), empName));
			cq.select(root);
			userModel = entityManager.createQuery(cq).getSingleResult();

		}catch(Exception e){
			return false;
		}finally{
			close();
		}
		return flag;
	}


	@Override
	public int getUserId(String empName, String empMail) throws Exception {

		int empId=0;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			Predicate condition = qb.equal(root.get("eMail"), empMail);
			Predicate condition2 = qb.equal(root.get("userName"), empName);
			Predicate condition3 = qb.or(condition, condition2);
			cq.where(condition3);
			cq.select(root);
			userModel = entityManager.createQuery(cq).getSingleResult();
			empId=userModel.getId();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return empId;
	}

	@Override
	public UserDetailsModel userActivate(int id) throws Exception{

		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			userModel = entityManager.createQuery(cq).getSingleResult();
			userModel.setActive(true);
			merge(userModel);
			commit();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return userModel;
	}

	@Override
	public UserDetailsModel login(String userName, String password) {

		UserDetailsModel userDetailsModel = null;

		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("userName"), userName), qb.equal(root.get("password"), password));
			cq.select(root);
			userDetailsModel = entityManager.createQuery(cq).getSingleResult();
			commit(); 
			if(userDetailsModel.getUserName().equals(userName) && userDetailsModel.getPassword().equals(password)){
				
				return userDetailsModel;
			}
			else{
				
				userDetailsModel = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return userDetailsModel;
	}
	public UserDetailsModel getname(Object userId){

		UserDetailsModel name=null;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"),userId));
			cq.select(root);
			name = entityManager.createQuery(cq).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return name; 
	}

	@Override
	public UserDetailsModel forgotpassword(String mailId) {
		// TODO Auto-generated method stub
		UserDetailsModel userDetailsModel = new UserDetailsModel();
		boolean mailvalidation = false;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("eMail"),mailId));
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
	public boolean updatepassword(int id, String password) {

		UserDetailsModel userDetailsModel=new UserDetailsModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			userDetailsModel = entityManager.createQuery(cq).getSingleResult();
			userDetailsModel.setPassword(password);
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


}
