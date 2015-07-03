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
import com.mitosis.timesheet.model.TeamAssignmentModel;
import com.mitosis.timesheet.model.TimeSheetModel;
import com.mitosis.timesheet.pojo.SummaryReport;
import com.mitosis.timesheet.util.BaseService;

public class TeamReportDaoImpl extends BaseService implements TeamReportDao {

	@Override
	public List<TeamAssignmentModel> getProjectList(int employeeId) {
		// TODO Auto-generated method stub
		
		List<TeamAssignmentModel> projectlist = new ArrayList<TeamAssignmentModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TeamAssignmentModel> cq = qb.createQuery(TeamAssignmentModel.class);
			Root<TeamAssignmentModel> root = cq.from(TeamAssignmentModel.class);
			cq.select(root);
			cq.where(qb.equal(root.get("member"),employeeId));
			cq.orderBy(qb.asc(root.get("project").get("projectName")));
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
	public int getrole(int userId,int projectId) {
		
		int role=0;
		List<TeamAssignmentModel> rolelist = new ArrayList<TeamAssignmentModel>();		
		try{
			begin();
		      entityManager.getEntityManagerFactory().getCache().evictAll();
		      CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		      CriteriaQuery<TeamAssignmentModel> query = qb.createQuery(TeamAssignmentModel.class);
		      Root<TeamAssignmentModel> root = query.from(TeamAssignmentModel.class);
		      Predicate condition = qb.equal(root.get("member"), userId);
		      Predicate condition1 = qb.equal(root.get("project").get("projectId"), projectId);
		      Predicate conditions = qb.and(condition,condition1);
		      query.where(conditions);
		      query.select(root);
		      rolelist =  entityManager.createQuery(query).getResultList();
		      role = rolelist.get(0).getRole().getId();
		      
		      
		      /*role =   entityManager.createQuery(query).getSingleResult();*/
		      /*longrole = (Long) entityManager.createQuery(query).getSingleResult();
		       role = (int) (long)longrole;*/
		     
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return role;
	}

	@Override
	public List<TimeSheetModel> getTeamSummaryTimeSheetList(Date fromDate, Date toDate,
			int employeeId, int projectId) {
		
		System.out.println(employeeId);
		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("userDetails"), employeeId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate condition4 = qb.equal(root.get("project"),projectId);
			Predicate conditions = qb.and(condition, condition2, condition3,condition4);
			cq.where(conditions);
			cq.select(root);
			cq.groupBy(root.get("date"));
			timeSheetDetailReport = entityManager.createQuery(cq).getResultList();
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetDetailReport;
	}

	
	@Override
	public double getTotalHours(Date fromDate, Date toDate, int memberId,
			int projectId) {
		
		double totalhours=0.0;
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Number> cq = qb.createQuery(Number.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("userDetails"), memberId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate condition4 = qb.equal(root.get("project"),projectId);
			Predicate conditions = qb.and(condition, condition2, condition3,condition4);
			cq.where(conditions);
			cq.select(qb.sum(root.<Integer>get("hours")));
			totalhours=entityManager.createQuery(cq).getSingleResult().doubleValue();
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return totalhours;
	}

	@Override
	public List<SummaryReport> getSumHours(Date fromDate, Date toDate,
			int memberId, int projectId) {
		TimeSheetModel timesheetModel = new TimeSheetModel();
		List<SummaryReport> timeSheetList = new ArrayList<SummaryReport>();
		List<Double> hours = new ArrayList<Double>();
		
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Double> cq = qb.createQuery(Double.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			/*Path<Integer> hourspath =root.get("hours");*/
			Predicate condition = qb.equal(root.get("userDetails").get("id"), memberId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate condition4 = qb.equal(root.get("project"), projectId);
			Predicate conditions = qb.and(condition, condition2, condition3, condition4);
			cq.where(conditions);
			cq.select(qb.sum(root.<Double>get("hours")));
			cq.groupBy(root.get("date"));
	
			hours=entityManager.createQuery(cq).getResultList();
			for(int i =0;i<hours.size();i++){
			
				SummaryReport summaryReport = new SummaryReport();
				
				summaryReport.setHourslist(hours.get(i));

				timeSheetList.add(summaryReport);
								
			}

			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetList;
	}

	@Override
	public int checkUserRights(int employeeId,int projectId){
		
		String levelstring;
		int level=0;
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TeamAssignmentModel> cq = qb.createQuery(TeamAssignmentModel.class);
			Root<TeamAssignmentModel> root = cq.from(TeamAssignmentModel.class);
 			cq.where(qb.equal(root.get("member").get("id"), employeeId),qb.equal(root.get("project").get("projectId"), projectId));
 			cq.select(root);
 			levelstring=entityManager.createQuery(cq).getSingleResult().getRole().getLevel();
 			level=Integer.parseInt(levelstring);
 		}catch(Exception e){
			e.printStackTrace();
		}finally{
 			close();
		}
 		return level;
	}

	@Override
	public List<TimeSheetModel> getTeamDetailTimeSheetList(Date date, Date toDate,
			int memberId, int projectId) {

		
		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.equal(root.get("userDetails"), memberId);
			Predicate condition2 = qb.greaterThanOrEqualTo(fromDatePath, date);
			Predicate condition3 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate condition4 = qb.equal(root.get("project"),projectId);
			Predicate conditions = qb.and(condition, condition2, condition3,condition4);
			cq.where(conditions);
			cq.select(root);
			cq.orderBy(qb.asc(root.get("date")),qb.asc(root.get("userDetails").get("name")));
			timeSheetDetailReport = entityManager.createQuery(cq).getResultList();
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetDetailReport;
	}
	
	@Override
	public List<TimeSheetModel> getAllProjectsDetails(Date fromDate, Date toDate){
		

		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
		begin();
		entityManager.getEntityManagerFactory().getCache().evictAll();
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
		Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
		Path<Date> fromDatePath =  root.get("date");
		Predicate condition = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
		Predicate condition1 = qb.lessThanOrEqualTo(fromDatePath, toDate);
		Predicate conditions = qb.and(condition, condition1);
		cq.where(conditions);
		cq.select(root);
		cq.orderBy(qb.asc(root.get("date")),qb.asc(root.get("project").get("projectName")));
		timeSheetDetailReport = entityManager.createQuery(cq).getResultList();
		commit(); 
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		close();
	}
	return timeSheetDetailReport;
 }
	
	@Override
	public List<TimeSheetModel> getAllProjectsSummary(Date fromDate, Date toDate){
		

		List<TimeSheetModel> timeSheetDetailReport = new ArrayList<TimeSheetModel>();
		try{
		begin();
		entityManager.getEntityManagerFactory().getCache().evictAll();
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeSheetModel> cq = qb.createQuery(TimeSheetModel.class);
		Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
		Path<Date> fromDatePath =  root.get("date");
		Predicate condition = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
		Predicate condition1 = qb.lessThanOrEqualTo(fromDatePath, toDate);
		Predicate conditions = qb.and(condition, condition1);
		cq.where(conditions);
		cq.select(root);
		cq.groupBy(root.get("date"));
		timeSheetDetailReport = entityManager.createQuery(cq).getResultList();
		commit(); 
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		close();
	}
	return timeSheetDetailReport;
 }
	@Override
	public List<SummaryReport> getAllUserSumHours(Date fromDate, Date toDate){
		TimeSheetModel timesheetModel = new TimeSheetModel();
		List<SummaryReport> timeSheetList = new ArrayList<SummaryReport>();
		List<Double> hours = new ArrayList<Double>();
		
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Double> cq = qb.createQuery(Double.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			/*Path<Integer> hourspath =root.get("hours");*/
			Predicate condition = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
			Predicate condition1 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate conditions = qb.and(condition, condition1);
			cq.where(conditions);
			cq.select(qb.sum(root.<Double>get("hours")));
			cq.groupBy(root.get("date"),root.get("employee_id"));
			hours=entityManager.createQuery(cq).getResultList();
			for(int i =0;i<hours.size();i++){
			
				SummaryReport summaryReport = new SummaryReport();
				
				summaryReport.setHourslist(hours.get(i));

				timeSheetList.add(summaryReport);
								
			}

			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return timeSheetList;
	}
	@Override
	public double getAllUsersTotalHours(Date fromDate, Date toDate) {
		
		double totalhours=0.0;
		
		try{
			begin();
			entityManager.getEntityManagerFactory().getCache().evictAll();
			CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		    CriteriaQuery<Number> cq = qb.createQuery(Number.class);
			Root<TimeSheetModel> root = cq.from(TimeSheetModel.class);
			Path<Date> fromDatePath =  root.get("date");
			Predicate condition = qb.greaterThanOrEqualTo(fromDatePath, fromDate);
			Predicate condition1 = qb.lessThanOrEqualTo(fromDatePath, toDate);
			Predicate conditions = qb.and(condition, condition1);
			cq.where(conditions);
			cq.select(qb.sum(root.<Integer>get("hours")));
			totalhours=entityManager.createQuery(cq).getSingleResult().doubleValue();
			System.out.println(totalhours);
			commit(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return totalhours;
	}

	
}
