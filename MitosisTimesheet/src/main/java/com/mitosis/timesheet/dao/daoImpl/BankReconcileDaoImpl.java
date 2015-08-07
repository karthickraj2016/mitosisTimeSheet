package com.mitosis.timesheet.dao.daoImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.BankReconcileDao;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.util.BaseService;

@SuppressWarnings("rawtypes")
public class BankReconcileDaoImpl extends BaseService implements BankReconcileDao {

	@Override
	public CustomerPaymentModel getReceiptDetails(String ino) {
		CustomerPaymentModel paymentModel=new CustomerPaymentModel();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq.from(CustomerPaymentModel.class);
			cq.where(qb.equal(root.get("invoiceHdr"),ino));
	    	cq.select(root);		
			paymentModel = entityManager.createQuery(cq).getSingleResult();
			}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return paymentModel;
	}
	

}
