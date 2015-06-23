package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.TeamAssignmentDAO;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.RoleDetailsModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class TeamAssignmentDAOImpl extends BaseService implements TeamAssignmentDAO {

	public List<TeamAssignmentModel> showTeamList(){
		List<TeamAssignmentModel> teamlist = null;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TeamAssignmentModel> cq = qb.createQuery(TeamAssignmentModel.class);
			Root<TeamAssignmentModel> root = cq.from(TeamAssignmentModel.class);
			cq.select(root);
			cq.orderBy(qb.desc(root.get("id")));
			teamlist = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return teamlist;
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
	public List<UserDetailsModel> getMemberList() {
		List<UserDetailsModel> memberlist = new ArrayList<UserDetailsModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDetailsModel> cq = qb.createQuery(UserDetailsModel.class);
			Root<UserDetailsModel> root = cq.from(UserDetailsModel.class);
			cq.where(qb.equal(root.get("active"),true));
			cq.select(root);
			cq.orderBy(qb.asc(root.get("name")));
			memberlist = entityManager.createQuery(cq).getResultList();
			System.out.println(memberlist);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return memberlist;
	}

	@Override
	public List<RoleDetailsModel> getRoleList() {
		List<RoleDetailsModel> rolelist = new ArrayList<RoleDetailsModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<RoleDetailsModel> cq = qb.createQuery(RoleDetailsModel.class);
			Root<RoleDetailsModel> root = cq.from(RoleDetailsModel.class);
			cq.select(root);
			cq.orderBy(qb.desc(root.get("level")));
			rolelist = entityManager.createQuery(cq).getResultList();
			System.out.println(rolelist);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return rolelist;
	}

	@Override
	public boolean insertTeamDetails(TeamAssignmentModel teamAssignModel){

		boolean flag = false ;

		try{
			begin();
			merge(teamAssignModel);
			commit();
			flag=true;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return flag;
	}


	@Override
	public boolean deleteAssignment(int id) {
		boolean flag=false;
		TeamAssignmentModel teamAssignmentModel=null;
		try {
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TeamAssignmentModel> cq = qb.createQuery(TeamAssignmentModel.class);
			Root<TeamAssignmentModel> root = cq.from(TeamAssignmentModel.class);
			cq.where(qb.equal(root.get("id"), id));
			cq.select(root);
			teamAssignmentModel=entityManager.createQuery(cq).getSingleResult();
			remove(teamAssignmentModel);
			commit();
			flag=true;
		}finally{
			close();
		}
		return flag;
	}

}
