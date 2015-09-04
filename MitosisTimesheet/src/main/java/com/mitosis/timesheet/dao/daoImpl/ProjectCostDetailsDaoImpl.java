package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.ProjectCostDetailsDao;
import com.mitosis.timesheet.model.ProjectCostDetailsModel;
import com.mitosis.timesheet.model.ProjectCostHdrModel;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.util.BaseService;

public class ProjectCostDetailsDaoImpl extends BaseService implements ProjectCostDetailsDao {

	@Override
	public int addDetailsInHdr(ProjectCostHdrModel hdrModel) {

		int hdrId=0;
	
		try{
			begin();
			if(hdrModel.getId()==null){
				persist(hdrModel);
			}else{
				merge(hdrModel);
			}
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
			merge(detailsModel);
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
			merge(hdrModel);
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
	public ProjectCostHdrModel projectValidation(int projectId) {
		
		ProjectCostHdrModel costModel=new ProjectCostHdrModel();
		
	try{
		begin();
		entityManager.getEntityManagerFactory().getCache().evictAll();
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectCostHdrModel> cq = qb.createQuery(ProjectCostHdrModel.class);
		Root<ProjectCostHdrModel> root = cq.from(ProjectCostHdrModel.class);
		cq.where(qb.equal(root.get("project"), projectId));
		cq.select(root);
		costModel = entityManager.createQuery(cq).getSingleResult();
		commit();
		flush();
	}catch(Exception e){
			
			System.out.println(e);
		}finally{
			close();
		}
		return costModel;
	}

	@Override
	public List<ProjectModel> getProjectList() {
		List<ProjectModel> projectlist = new ArrayList<ProjectModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectModel> cq = qb.createQuery(ProjectModel.class);
			Root<ProjectModel> root = cq.from(ProjectModel.class);
			Predicate condition = qb.equal(root.get("status"),"Open");
			Predicate condition1 = qb.equal(root.get("billable"),"Yes");
			Predicate conditions=qb.and(condition,condition1);
			cq.where(conditions);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("projectName")));
			projectlist = entityManager.createQuery(cq).getResultList();
			System.out.println(projectlist);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return projectlist;
	}

	@Override
	public List<TeamAssignmentModel> getTeamMembers(int projectId) {
		
		List<TeamAssignmentModel> teamMembers = new ArrayList<TeamAssignmentModel>();
		
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TeamAssignmentModel> cq = qb.createQuery(TeamAssignmentModel.class);
			Root<TeamAssignmentModel> root = cq.from(TeamAssignmentModel.class);
			cq.where(qb.equal(root.get("project"), projectId));
			cq.select(root);
			teamMembers = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
				
			e.printStackTrace();
			}finally{
				close();
			}
			return teamMembers;
	}


}
