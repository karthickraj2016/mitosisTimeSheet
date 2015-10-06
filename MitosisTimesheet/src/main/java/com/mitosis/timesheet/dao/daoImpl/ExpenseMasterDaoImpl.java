package com.mitosis.timesheet.dao.daoImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.BankReconcileDao;
import com.mitosis.timesheet.dao.ExpenseMasterDao;
import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.model.CustomerDetailsModel;
import com.mitosis.timesheet.model.ExpenseMasterModel;
import com.mitosis.timesheet.util.BaseService;

public class ExpenseMasterDaoImpl extends BaseService implements ExpenseMasterDao {

	@Override
	public List<ExpenseMasterModel> showList() {
		List<ExpenseMasterModel> expensesList = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseMasterModel> cq = qb.createQuery(ExpenseMasterModel.class);
			Root<ExpenseMasterModel> root = cq.from(ExpenseMasterModel.class);
			cq.select(root);
			expensesList = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return expensesList;
	}

	@Override
	public boolean insert(ExpenseMasterModel expenseMasterModel) {
		boolean flag=false;
		try {
			begin();
			merge(expenseMasterModel);
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
	public boolean delete(int id) {
		boolean flag=false;
		ExpenseMasterModel employeeMaster = new ExpenseMasterModel();
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseMasterModel> cq = qb.createQuery(ExpenseMasterModel.class);
			Root<ExpenseMasterModel> root = cq.from(ExpenseMasterModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			employeeMaster = entityManager.createQuery(cq).getSingleResult();
			remove(employeeMaster);
			commit();
			flag = true;
		} catch (Exception e) {
			return flag;
		} finally {
			close();
		}
		return flag;
	}

}
