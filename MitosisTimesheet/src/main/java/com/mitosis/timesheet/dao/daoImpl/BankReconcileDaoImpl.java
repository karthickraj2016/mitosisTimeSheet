package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.BankReconcileDao;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.util.BaseService;

@SuppressWarnings("rawtypes")
public class BankReconcileDaoImpl extends BaseService implements BankReconcileDao {

	@Override
	public List<CustomerPaymentModel> getReceiptDetails() {
		List<CustomerPaymentModel> paymentModel=new ArrayList<CustomerPaymentModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq.from(CustomerPaymentModel.class);
			Predicate condition1 =root.get("paidAmountInr").isNull();
	/*		Predicate condition2 =  qb.equal(root.get("paidAmountInr"),null);
			Predicate condition3 =  qb.equal(root.get("commisionAmount"),null);
			Predicate condition4 = qb.equal(root.get("receivedAmount"), null);*/
			/*Predicate condition5 =qb.equal(root.get("bankReceivedDate"), null);*/
			Predicate conditions = qb.and(condition1);
			cq.where(conditions);
			cq.select(root);		
			paymentModel = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return paymentModel;
	}

	@Override
	public List<CustomerPaymentModel> getPaymentDetails(String invoiceNumber) {
		List<CustomerPaymentModel> paymentModel=new ArrayList<CustomerPaymentModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq.from(CustomerPaymentModel.class);
			cq.where(qb.equal(root.get("invoiceHdr").get("invoiceNumber"),invoiceNumber));
			cq.select(root);		
			paymentModel = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return paymentModel;
	}

	@Override
	public InvoiceHdrModel getInvoiceHdrDetails(String invoiceNum) {

		InvoiceHdrModel invoiceModel=null;

		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			cq.where(qb.equal(root.get("invoiceNumber"),invoiceNum));
			cq.select(root);		
			invoiceModel = entityManager.createQuery(cq).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return invoiceModel;
	}


	@Override
	public boolean insert(CustomerPaymentModel customerPaymentModel) {

		boolean flag=false;
		try {
			begin();
			merge(customerPaymentModel);
			commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

}
