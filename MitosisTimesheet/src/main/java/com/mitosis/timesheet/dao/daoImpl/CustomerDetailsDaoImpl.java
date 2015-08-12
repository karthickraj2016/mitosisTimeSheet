package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.CustomerDetailsDao;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.util.BaseService;

public class CustomerDetailsDaoImpl extends BaseService implements CustomerDetailsDao {

	boolean flag=false;
	
	@Override
	public boolean addCustomerDetails(CustomerDetailsModel customerModel) {

		try {
			begin();
			persist(customerModel);
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
	public boolean updateCustomerDetails(CustomerDetailsModel customerModel) {
	 
		try {
			begin();
			merge(customerModel);
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
	public boolean removeCustomerById(int customerId) {
		
	 CustomerDetailsModel customerModel=new CustomerDetailsModel();
	
	 try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerDetailsModel> cq = qb.createQuery(CustomerDetailsModel.class);
			Root<CustomerDetailsModel> root = cq.from(CustomerDetailsModel.class);
			cq.where(qb.equal(root.get("customerId"), customerId));
			cq.select(root);
			customerModel = entityManager.createQuery(cq).getSingleResult();
			remove(customerModel);
			commit();
			flag = true;
		} catch (Exception e) {
			return flag;
		} finally {
			close();
		}
		return flag;
		
	}

	@Override
	public List<CustomerDetailsModel> showCustomerlist() {
		
		List<CustomerDetailsModel> customerlist = new ArrayList<CustomerDetailsModel>();
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerDetailsModel> cq = qb.createQuery(CustomerDetailsModel.class);
			Root<CustomerDetailsModel> root = cq.from(CustomerDetailsModel.class);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("customerName")));
			customerlist = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return customerlist;
	}

	@Override
	public boolean nameValidation(String name) {
		
		CustomerDetailsModel customerModel=new CustomerDetailsModel();
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerDetailsModel> cq = qb.createQuery(CustomerDetailsModel.class);
			Root<CustomerDetailsModel> root = cq.from(CustomerDetailsModel.class);
			cq.where(qb.equal(root.get("customerName"), name));
			cq.select(root);
			customerModel = entityManager.createQuery(cq).getSingleResult();
			commit();
			flag = true;
		} catch (Exception e) {
			return flag;
		} finally {
			close();
		}
		return flag;
    }
	
	@Override
	public boolean mailValidation(String mail) {
		
		CustomerDetailsModel customerModel=new CustomerDetailsModel();
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerDetailsModel> cq = qb.createQuery(CustomerDetailsModel.class);
			Root<CustomerDetailsModel> root = cq.from(CustomerDetailsModel.class);
			cq.where(qb.equal(root.get("email"), mail));
			cq.select(root);
			customerModel = entityManager.createQuery(cq).getSingleResult();
			commit();
			flag = true;
		} catch (Exception e) {
			return flag;
		} finally {
			close();
		}
		return flag;
    }

	@Override
	public boolean getProjectStatus(int customerId) {

		List<ProjectModel> projectModel=new ArrayList<ProjectModel>();
		boolean projectStatus=false;
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.where(qb.equal(root.get("customer").get("customerId"), customerId),qb.equal(root.get("status"),"Open"));
			cq.select(root);
			projectModel = entityManager.createQuery(cq).getResultList();
			commit();
			if(projectModel.size()>0){
			projectStatus = true;
			}
		} catch (Exception e) {
			return flag;
		} finally {
			close();
		}
		return projectStatus;
	}
}