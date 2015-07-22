package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.InvoiceDetailsDao;
import com.mitosis.timesheet.model.InvoiceDetailsModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.LeaveDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class InvoiceDetailsDaoImpl extends BaseService implements InvoiceDetailsDao {

	@Override
	public boolean validateEntry(InvoiceHdrModel invoiceHdrModel) {
		// TODO Auto-generated method stub
		
		boolean validation = false;
		
		List<InvoiceHdrModel> invoicehdrModel = new ArrayList<InvoiceHdrModel>();
		

		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			Path<Date> invoiceDate = root.get("invoiceDate");
			Predicate condition = qb.equal(root.get("Project").get("projectId"),invoiceHdrModel.getProject().getProjectId());
			Predicate condition1 = qb.equal(invoiceDate, invoiceHdrModel.getInvoiceDate());
			Predicate conditions = qb.and(condition,condition1);
			cq.where(conditions);
			cq.select(root);		
			invoicehdrModel = entityManager.createQuery(cq).getResultList();
			if(invoicehdrModel.size()==0){
				
				validation = true;
				
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return validation;
	}

	@Override
	public boolean create(InvoiceHdrModel invoiceHdrModel) {
		
		
		boolean insert = false;
		try {
			begin();
			persist(invoiceHdrModel);
			commit();
			insert = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return insert;
	}

	@Override
	public boolean create(InvoiceDetailsModel invoiceDetailsModel) {
		boolean insert = false;
		try {
			begin();
			persist(invoiceDetailsModel);
			commit();
			insert = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
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
	
			cq.select(root);		
			invoiceHdrModel = entityManager.createQuery(cq).getResultList();
			if(invoiceHdrModel.size()>0){
			InvoiceNumber =invoiceHdrModel.get(invoiceHdrModel.size()-1).getInvoiceNumber();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return InvoiceNumber;
	}
	
	

}
