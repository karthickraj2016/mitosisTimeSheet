package com.mitosis.timesheet.dao.daoImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.CompanyDao;
import com.mitosis.timesheet.model.CompanyInfoModel;
import com.mitosis.timesheet.util.BaseService;

public class CompanyDaoImpl extends BaseService implements CompanyDao {

	@Override
	public List<CompanyInfoModel> showlist() {
		List<CompanyInfoModel> companylist = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CompanyInfoModel> cq = qb.createQuery(CompanyInfoModel.class);
			Root<CompanyInfoModel> root = cq.from(CompanyInfoModel.class);
			cq.select(root);
			//cq.orderBy(qb.desc(root.get("id")));
			companylist = entityManager.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return companylist;
	}
	@Override
	public CompanyInfoModel show() {
		CompanyInfoModel company = new CompanyInfoModel();
		
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CompanyInfoModel> cq = qb.createQuery(CompanyInfoModel.class);
			Root<CompanyInfoModel> root = cq.from(CompanyInfoModel.class);
			//cq.where(qb.equal(root.get("Id"), 1));
			cq.select(root);
			List l = entityManager.createQuery(cq).getResultList();
			company=(CompanyInfoModel)l.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		return company;
	}
	@Override
	public boolean save(CompanyInfoModel company) {
		boolean flag=false;
		try {
			begin();
			merge(company);
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
	public boolean remove(int id) {
		boolean flag=false;
		CompanyInfoModel company = null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<CompanyInfoModel> cq = qb.createQuery(CompanyInfoModel.class);
			Root<CompanyInfoModel> root = cq.from(CompanyInfoModel.class);
			cq.where(qb.equal(root.get("Id"), id));
			cq.select(root);
			company = entityManager.createQuery(cq).getSingleResult();
			remove(company);
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
