package com.mitosis.timesheet.dao.daoImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.util.BaseService;

public class InvoiceDetailsDaoImpl extends BaseService implements InvoiceDetailsDao {

	@Override
	public boolean validateEntry(InvoiceHdrModel invoiceHdrModel) {
		// TODO Auto-generated method stub
		
		boolean validation = true;
		
		List<InvoiceHdrModel> invoicehdrModel = new ArrayList<InvoiceHdrModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			Path<Date> invoiceDate = root.get("invoiceDate");
			Predicate condition = qb.equal(root.get("project").get("projectId"),invoiceHdrModel.getProject().getProjectId());
			Predicate condition1 = qb.equal(invoiceDate, invoiceHdrModel.getInvoiceDate());
			Predicate conditions = qb.and(condition,condition1);
			cq.where(conditions);
			cq.select(root);		
			invoicehdrModel = entityManager.createQuery(cq).getResultList();
			if(invoicehdrModel.size()==0){
				
				validation = false;
				
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return validation;
	}

	@Override
	public InvoiceHdrModel create(InvoiceHdrModel invoiceHdrModel) {
		
		
		InvoiceHdrModel invoicehdrModel = new InvoiceHdrModel();
		try {
			begin();
			merge(invoiceHdrModel);
			commit();
			invoicehdrModel = invoiceHdrModel;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return invoicehdrModel;
	}

	@Override
	public boolean create(List<InvoiceDetailsModel> invoiceDetailsModel) throws IllegalAccessException, InvocationTargetException {
		boolean insert = false;
		for(int i=0;i<invoiceDetailsModel.size();i++){
	
		try {
			begin();
			merge(invoiceDetailsModel.get(i));
			commit();
			insert = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		}
		return insert;
	}

	@Override
	public String getInvoiceNumber() {
		
		String InvoiceNumber = null;
		
		
		List<InvoiceHdrModel> invoiceHdrModel = new ArrayList<InvoiceHdrModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			
			cq.orderBy(qb.desc(root.get("id")));
	
			cq.select(root);		
			invoiceHdrModel = entityManager.createQuery(cq).getResultList();
			if(invoiceHdrModel.size()>0){
			InvoiceNumber =invoiceHdrModel.get(0).getInvoiceNumber();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return InvoiceNumber;
	}

	@Override
	public int getId(String invoiceNumber) {
		
	InvoiceDetailsModel invoiceDetailsModel = new InvoiceDetailsModel();
	int id =0;
	
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceDetailsModel> cq = qb.createQuery(InvoiceDetailsModel.class);
			Root<InvoiceDetailsModel> root = cq.from(InvoiceDetailsModel.class);
			Predicate condition = qb.equal(root.get("invoice").get("invoiceNumber"),invoiceNumber);
			cq.where(condition);
			cq.select(root);		
			invoiceDetailsModel = entityManager.createQuery(cq).getSingleResult();
			id = invoiceDetailsModel.getId();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return id;
	}

	@Override
	public List<ProjectModel> getProjectList() {
		List<ProjectModel> projectList = new ArrayList<ProjectModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.select(root);	
			cq.orderBy(qb.asc(root.get("projectName")));
			projectList = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return projectList;
	}

	@Override
	public List<CustomerDetailsModel> getCustomerList() {
		List<CustomerDetailsModel> customerList = new ArrayList<CustomerDetailsModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerDetailsModel> cq = qb.createQuery(CustomerDetailsModel.class);
			Root<CustomerDetailsModel> root = cq.from(CustomerDetailsModel.class);
			cq.select(root);	
			cq.orderBy(qb.asc(root.get("customerName")));
			customerList = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return customerList;
	}

	@Override
	public List<ProjectCostHdrModel> getProjectCosthdrList(int id) {
		List<ProjectCostHdrModel> projectCostHdrList = new ArrayList<ProjectCostHdrModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectCostHdrModel> cq = qb.createQuery(ProjectCostHdrModel.class);
			Root<ProjectCostHdrModel> root = cq.from(ProjectCostHdrModel.class);
			Predicate condition = qb.equal(root.get("project").get("projectId"),id);
			cq.where(condition);
			cq.select(root);		
			projectCostHdrList = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return projectCostHdrList;
	}

	@Override
	public List<ProjectCostDetailsModel> getTeamList(int projectId) {
		List<ProjectCostDetailsModel> teamMembersList = new ArrayList<ProjectCostDetailsModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectCostDetailsModel> cq = qb.createQuery(ProjectCostDetailsModel.class);
			Root<ProjectCostDetailsModel> root = cq.from(ProjectCostDetailsModel.class);
			Predicate condition = qb.equal(root.get("projectCostHdr").get("project").get("projectId"),projectId);
			cq.where(condition);
			cq.select(root);		
			teamMembersList = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return teamMembersList;
	}

	@Override
	public CompanyInfoModel getCompanyInfo() {
		
		CompanyInfoModel companyInfoModel = new CompanyInfoModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CompanyInfoModel> cq = qb.createQuery(CompanyInfoModel.class);
			Root<CompanyInfoModel> root = cq.from(CompanyInfoModel.class);
	
			cq.select(root);		
			companyInfoModel = entityManager.createQuery(cq).getSingleResult();
			
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return companyInfoModel;
	}

	@Override
	public List<InvoiceHdrModel> getInvoiceList(int projectId) {
		List<InvoiceHdrModel> invoicelist = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			cq.where(qb.equal(root.get("project").get("projectId"), projectId));
			cq.orderBy(qb.asc(root.get("id")));
			cq.select(root);
			invoicelist = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return invoicelist;
	}
	@Override
	public InvoiceHdrModel getInvoiceHdr(String invoiceNumber){
		InvoiceHdrModel invoicehdr = new InvoiceHdrModel();
			try{
				begin();
				entityManager.getEntityManagerFactory().getCache().evictAll();
				CriteriaBuilder qb = entityManager.getCriteriaBuilder();
				CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
				Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
				Predicate condition = qb.equal(root.get("invoiceNumber"),invoiceNumber);
				cq.where(condition);
				cq.select(root);		
				invoicehdr = entityManager.createQuery(cq).getSingleResult();
				}catch(Exception e){
				e.printStackTrace();
			}finally{
				close();
			}
			return invoicehdr;
	}

	@Override
	public List<InvoiceDetailsModel> getInvoiceDetails(int invoiceno) {
		List<InvoiceDetailsModel> invoiceDetails = new ArrayList<InvoiceDetailsModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceDetailsModel> cq = qb.createQuery(InvoiceDetailsModel.class);
			Root<InvoiceDetailsModel> root = cq.from(InvoiceDetailsModel.class);
			Predicate condition = qb.equal(root.get("invoiceNumber"),invoiceno);
			cq.where(condition);
			cq.select(root);		
			invoiceDetails = entityManager.createQuery(cq).getResultList();
			}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return invoiceDetails;
	}

	@Override
	public ProjectCostHdrModel getCostHdrId(int projectId) {
		
		ProjectCostHdrModel projectCostHdrModel = new ProjectCostHdrModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectCostHdrModel> cq = qb.createQuery(ProjectCostHdrModel.class);
			Root<ProjectCostHdrModel> root = cq.from(ProjectCostHdrModel.class);
			Predicate condition = qb.equal(root.get("project").get("projectId"),projectId);
			Predicate conditions = qb.and(condition);
			cq.where(conditions);
			cq.select(root);		
			projectCostHdrModel = entityManager.createQuery(cq).getSingleResult();
			}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return projectCostHdrModel;
	}

	@Override
	public ProjectCostDetailsModel getMemberRate(int projectCostId,int memberId) {
		ProjectCostDetailsModel projectCostHdrModel = new ProjectCostDetailsModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectCostDetailsModel> cq = qb.createQuery(ProjectCostDetailsModel.class);
			Root<ProjectCostDetailsModel> root = cq.from(ProjectCostDetailsModel.class);
			Predicate condition = qb.equal(root.get("projectCostHdr"),projectCostId);
			Predicate condition1 = qb.equal(root.get("employee").get("id"),memberId);
			Predicate conditions = qb.and(condition,condition1);
			cq.where(conditions);
			cq.select(root);		
			projectCostHdrModel = entityManager.createQuery(cq).getSingleResult();
			}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return projectCostHdrModel;
	}
	

}
