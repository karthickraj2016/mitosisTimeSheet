package com.mitosis.timesheet.dao.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mitosis.timesheet.dao.TeamReportDao;
import com.mitosis.timesheet.model.ProjectModel;
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.model.UserDetailsModel;
import com.mitosis.timesheet.util.BaseService;

public class TeamReportDaoImpl extends BaseService implements TeamReportDao {

	@Override
	public List<ProjectModel> getProjectList() {
		// TODO Auto-generated method stub
		
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
	public List<TeamAssignmentModel> getTeamList(int projectId,int role) {
		List<TeamAssignmentModel> members = null;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TeamAssignmentModel> cq = qb.createQuery(TeamAssignmentModel.class);
			Root<TeamAssignmentModel> root = cq.from(TeamAssignmentModel.class);
			Path<Integer> rolePath =  root.get("role");
			cq.where(qb.equal(root.get("project"),projectId),qb.greaterThanOrEqualTo(rolePath, role));
			cq.select(root);
			members = entityManager.createQuery(cq).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return members;
	}

	@Override
	public int getrole(int userId) {
		
		int role=0;
		
	    Long longrole = null ;
		
		TeamAssignmentModel teamAssignmentModel = new TeamAssignmentModel();
		List<TeamAssignmentModel> teamlist = new ArrayList<TeamAssignmentModel>();
		
		try{
			begin();
		      entityManager.getEntityManagerFactory().getCache().evictAll();
		      CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		      CriteriaQuery<Number> query = qb.createQuery(Number.class);
		      Root<TeamAssignmentModel> root = query.from(TeamAssignmentModel.class);
		      Predicate condition = qb.equal(root.get("member"), userId);
		      Predicate conditions = qb.and(condition);
		      query.where(conditions);
		      query.select(qb.sum(root.<Integer>get("role")));
		      longrole = (Long) entityManager.createQuery(query).getSingleResult();
		       role = (int) (long)longrole;
		     
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return role;
	}

	@Override
	public List<TimeSheetModel> getTeamReportList(Date fromDate, Date toDate,
			int employeeId) {
		
		System.out.println(employeeId);
		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("employeeId"), employeeId);
			Predicate condition2 = qb.greaterThan(fromDatePath, fromDate);
			Predicate condition3 = qb.lessThan(fromDatePath, toDate);
			Predicate conditions = qb.and(condition, condition2, condition3);
			cq.where(conditions);
			cq.select(root);
			timeSheetDetailReport = entityManager.createQuery(cq).getResultList();
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetDetailReport;
	}

	
}
