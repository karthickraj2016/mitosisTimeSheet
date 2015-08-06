package com.mitosis.timesheet.dao.daoImpl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.CustomerPaymentDao;
import com.mitosis.timesheet.model.CustomerPaymentModel;
import com.mitosis.timesheet.model.InvoiceHdrModel;
import com.mitosis.timesheet.util.BaseService;

public class CustomerPaymentImpl extends BaseService implements
		CustomerPaymentDao {

	@Override
	public boolean save(CustomerPaymentModel c) {
		boolean flag = false;
		try {
			begin();
			InvoiceHdrModel i = c.getInvoiceHdr();
			i = updateInvoice(i.getInvoiceNumber(), c.getPaidAmount(),
					c.getId());
			merge(c);
			merge(i);
			commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		} finally {
			close();
		}
		return flag;
	}

	@Override
	public boolean remove(int id) {
		boolean flag = false;
		CustomerPaymentModel payment = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb
					.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq
					.from(CustomerPaymentModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			payment = entityManager.createQuery(cq).getSingleResult();
			BigDecimal pamt = payment.getPaidAmount();
			InvoiceHdrModel i = payment.getInvoiceHdr();
			String invoicenumber = i.getInvoiceNumber();
			i = updateInvoice(invoicenumber, pamt, -1); // updation of Invoice
														// table
			remove(payment);
			merge(i);
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
	public List<CustomerPaymentModel> showlist() {

		List<CustomerPaymentModel> paymentlist = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb
					.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq
					.from(CustomerPaymentModel.class);
			cq.select(root);
			cq.orderBy(qb.desc(root.get("id")));
			paymentlist = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return paymentlist;

	}

	@Override
	public CustomerPaymentModel show(int id) {
		CustomerPaymentModel payment = new CustomerPaymentModel();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb
					.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq
					.from(CustomerPaymentModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			payment = entityManager.createQuery(cq).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		return payment;
	}
	@SuppressWarnings("finally")
	@Override
	public boolean checkReceiptNo(String rno) {
		boolean flag=true;
		CustomerPaymentModel payment = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPaymentModel> cq = qb
					.createQuery(CustomerPaymentModel.class);
			Root<CustomerPaymentModel> root = cq
					.from(CustomerPaymentModel.class);
			cq.where(qb.equal(root.get("receiptNumber"), rno));
			cq.select(root);
			payment = entityManager.createQuery(cq).getSingleResult();
			if(payment!=null)
				flag=false;
		} catch (javax.persistence.NoResultException e) {
			System.out.println("Valid ReceiptNo");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			close();
			return flag;
		}
	}
	private InvoiceHdrModel updateInvoice(String id, BigDecimal amt,
			Integer cpid) {
		InvoiceHdrModel invoice = new InvoiceHdrModel();
		try {
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InvoiceHdrModel> cq = qb
					.createQuery(InvoiceHdrModel.class);
			Root<InvoiceHdrModel> root = cq.from(InvoiceHdrModel.class);
			cq.where(qb.equal(root.get("invoiceNumber"), id));
			cq.select(root);
			invoice = entityManager.createQuery(cq).getSingleResult();
			System.out.println(invoice);
			BigDecimal oldBalance = invoice.getBalanceAmount();
			BigDecimal oldPaid = invoice.getPaidAmount();
			BigDecimal newBalance;
			BigDecimal newPaid;
			System.out.println("OLD==" + oldBalance);
			System.out.println("NOW==" + amt);
			if (cpid != null && cpid > 0) {
				// System.out.println("==>ACTION UPDATION<==");
				CustomerPaymentModel cpm = new CustomerPaymentModel();
				CriteriaBuilder qb1 = entityManager.getCriteriaBuilder();
				CriteriaQuery<CustomerPaymentModel> cq1 = qb1
						.createQuery(CustomerPaymentModel.class);
				Root<CustomerPaymentModel> root1 = cq1
						.from(CustomerPaymentModel.class);
				cq1.where(qb1.equal(root1.get("id"), cpid));
				cq1.select(root1);
				cpm = entityManager.createQuery(cq1).getSingleResult();
				BigDecimal previousamt = cpm.getPaidAmount();
				// System.out.println("UPDATION==>previousAmount:-"+previousamt);
				oldBalance = oldBalance.add(previousamt);
				oldPaid = oldPaid.subtract(previousamt);
				newBalance = oldBalance.subtract(amt);
				newPaid = oldPaid.add(amt);
			} else if (cpid != null && cpid < 0) {
				// System.out.println("==>ACTION Deletion<==");
				newBalance = oldBalance.add(amt);
				newPaid = oldPaid.subtract(amt);
			} else {
				// System.out.println("==>ACTION Insertion<==");
				newBalance = oldBalance.subtract(amt);
				newPaid = oldPaid.add(amt);
			}
			// System.out.println("NEW=="+newBalance);
			invoice.setBalanceAmount(newBalance);
			invoice.setPaidAmount(newPaid);
			if (invoice.getInvoiceAmount().equals(invoice.getPaidAmount())) {
				invoice.setInvoiceStatus("PAID");
			} else {
				invoice.setInvoiceStatus("PARTIALLY-PAID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return invoice;
	}

}
