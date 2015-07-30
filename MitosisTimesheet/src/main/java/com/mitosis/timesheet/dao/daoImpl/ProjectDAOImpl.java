package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.ProjectDAO;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.*;

public class ProjectDAOImpl extends BaseService implements ProjectDAO {
	boolean flag = false;

	public boolean save(ProjectModel project) {
		try {
			begin();
			persist(project);
			commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	public boolean update(ProjectModel project) {
		try {
			begin();
			merge(project);
			commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	public boolean removeProjectById(int projectId) {

		ProjectModel projectModel = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.where(qb.equal(root.get("projectId"), projectId));
			cq.select(root);
			projectModel = entityManager.createQuery(cq).getSingleResult();
			remove(projectModel);
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
	public List<ProjectModel> showlist() {
		List<ProjectModel> projectlist = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.select(root);
			cq.orderBy(qb.desc(root.get("projectId")));
			projectlist = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return projectlist;
	}

	@Override
	public boolean checkProjectName(String projectName) {

		ProjectModel project = new ProjectModel();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.where(qb.equal(root.get("projectName"), projectName));
			cq.select(root);
			project = entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return flag;
		} finally {
			close();
		}
		return true;
	}

	@Override
	public String getProjectName(int projectId) {
		String name = "";
		ProjectModel project = new ProjectModel();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.where(qb.equal(root.get("projectId"), projectId));
			cq.select(root);
			project = entityManager.createQuery(cq).getSingleResult();
			name = project.getProjectName();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return name;
	}

	@Override
	public List<CustomerDetailsModel> getCustomerlist() {
		List<CustomerDetailsModel> customerlist = new ArrayList<CustomerDetailsModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerDetailsModel> cq = qb.createQuery(CustomerDetailsModel.class);
			Root<CustomerDetailsModel> root = cq.from(CustomerDetailsModel.class);
			cq.where(qb.equal(root.get("status"), "Active"));
			cq.select(root);
			cq.orderBy(qb.asc(root.get("customerName")));
			customerlist = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return customerlist;
	}
	@Override
	public List<ProjectModel> getProjectList(int cusid) {
		List<ProjectModel> projectlist = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.where(qb.equal(root.get("customer").get("customerId"), cusid));
			cq.select(root);
			cq.orderBy(qb.desc(root.get("projectId")));
			projectlist = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return projectlist;
	}

}
