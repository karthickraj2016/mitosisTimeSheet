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
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.security.access.method.P;

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
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			cq.select(root);
			Subquery<InvoiceHdrModel> subquery = cq.subquery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> subRoot = subquery.from(InvoiceHdrModel.class);
			Path<Date> datepath=subRoot.get("invoiceDate");
			Predicate condition1 = qb.equal(subRoot.get("project"),root);
			Predicate condition2 = qb.between(datepath, firstday, lastday);
			Predicate condition3 = qb.and(condition1,condition2);
			subquery.where(condition3);
			subquery.select(subRoot);
			Predicate condition4 = qb.equal(root.get("billable"),"Yes");
			Predicate condition5 = qb.not(qb.exists(subquery));
			Predicate condition6 = qb.and(condition4,condition5);
			cq.where(condition6);		
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
