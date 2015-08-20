 package com.mitosis.timesheet.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "project_team")

public class TeamAssignmentModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne(targetEntity = ProjectModel.class)
	@JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
	private ProjectModel project;
	
	@ManyToOne(targetEntity = UserDetailsModel.class)
	@JoinColumn(name = "member_id", nullable = false, referencedColumnName = "id")
	private UserDetailsModel member;
	
	@ManyToOne(targetEntity = RoleDetailsModel.class)
	@JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
	private RoleDetailsModel role;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Transient
	private String releaseEntryDate;

	public String getReleaseEntryDate() {
     
		if(getReleaseDate()==null){
			
			return "NotMentioned";
		}
		Date d= getReleaseDate();
		releaseEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return releaseEntryDate;
	}
	public void setReleaseEntryDate(String releaseEntryDate) {
		this.releaseEntryDate = releaseEntryDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}
	
	public UserDetailsModel getMember() {
		return member;
	}

	public void setMember(UserDetailsModel member) {
		this.member = member;
	}
	
	public RoleDetailsModel getRole() {
		return role;
	}

	public void setRole(RoleDetailsModel role) {
		this.role = role;
	}

	public Date getReleaseDate() {

		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}