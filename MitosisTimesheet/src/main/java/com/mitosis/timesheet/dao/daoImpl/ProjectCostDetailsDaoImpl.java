package com.mitosis.timesheet.dao.daoImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.ProjectCostDetailsDao;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.util.BaseService;

public class ProjectCostDetailsDaoImpl extends BaseService implements ProjectCostDetailsDao {

	@Override
	public int addDetailsInHdr(ProjectCostHdrModel hdrModel) {

		int hdrId=0;
		try{
			begin();
			persist(hdrModel);
			commit();
			hdrId=hdrModel.getId();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return hdrId;
	}

	@Override
	public boolean addDetailsInCostDetails(ProjectCostDetailsModel detailsModel) {

		boolean insert=false;
		
		try{
			begin();
			persist(detailsModel);
			commit();
			insert=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return insert;
	}

	@Override
	public boolean addFixedProjectCostDetails(ProjectCostHdrModel hdrModel) {
     
		boolean insert=false;
		
		try{
			begin();
			persist(hdrModel);
			commit();
			insert=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return insert;
	}

	@Override
	public boolean projectValidation(int projectId) {
		
		boolean project=false;
		
		ProjectCostHdrModel costModel=null;
		
	try{
		begin();
		entityManager.getEntityManagerFactory().getCache().evictAll();
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectCostHdrModel> cq = qb.createQuery(ProjectCostHdrModel.class);
		Root<ProjectCostHdrModel> root = cq.from(ProjectCostHdrModel.class);
		cq.where(qb.equal(root.get("project"), projectId));
		cq.select(root);
		costModel = entityManager.createQuery(cq).getSingleResult();
		if(costModel!=null){
			project=true;
		}
	}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return project;
	}

}
