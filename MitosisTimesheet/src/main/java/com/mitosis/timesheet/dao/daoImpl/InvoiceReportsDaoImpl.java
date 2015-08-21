package com.mitosis.timesheet.dao.daoImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.mitosis.timesheet.dao.InvoiceReportsDao;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.util.BaseService;

public class InvoiceReportsDaoImpl  extends BaseService implements InvoiceReportsDao {

	@Override
	public List<ProjectModel> pendingInvoiceProjectList(Date firstday, Date lastday) {
		
		List<ProjectModel> projectList = new ArrayList<ProjectModel>();
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			
			
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			/*Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.select(root);
			
			Subquery<Integer> subquery = cq.subquery(Integer.class);
			Root<InvoiceHdrModel> subRoot = subquery.from(InvoiceHdrModel.class);
			Path<Date> invoiceDate = subRoot.get("invoiceDate");
			Path<Integer> invoiceDate1 = subRoot.get("projectId");
			Predicate condition = qb.between(invoiceDate,firstday,lastday);
			Predicate conditions = qb.and(condition);
			subquery.where(conditions);
			subquery.select(invoiceDate1);
			
			
			
			
			 
			cq.where(qb.not(qb.exists(subquery)));*/ 
		/*	Root<ProjectModel> root = cq.from(ProjectModel.class);
			Root<InvoiceHdrModel> invoiceRoot = cq.from(InvoiceHdrModel.class);
			Path<Integer> invoiceDate1 = invoiceRoot.get("projectId");
			cq.select(root);
			subquery.where();*/
			
			
			
					
			projectList = entityManager.createQuery(cq).getResultList();
			
			
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		
		
		// TODO Auto-generated method stub
		return projectList;
	}

	@Override
	public List<InvoiceHdrModel> pendingBalanceList() {
		
		
		List<InvoiceHdrModel> pendingBalanceList = new ArrayList<InvoiceHdrModel>();
		
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			Path<BigDecimal> balanceAmountpath =root.get("balanceAmount");
			BigDecimal zero  = new BigDecimal(0);
			Predicate condition = qb.greaterThan(balanceAmountpath,zero);
			Predicate conditions = qb.and(condition);
			cq.where(conditions);
			cq.select(root);
			
			pendingBalanceList = entityManager.createQuery(cq).getResultList();
			
		// TODO Auto-generated method stub
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		
		return pendingBalanceList;
	}

}
