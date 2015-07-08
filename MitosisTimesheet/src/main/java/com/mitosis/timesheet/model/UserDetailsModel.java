package com.mitosis.timesheet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@Table(name = "user_details")

public class UserDetailsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="username")
	private String userName ;

	@Column(name="password")
	private String password;


	@Column(name="email")
	private String eMail;

	@Column(name="active")
	private boolean active;

	@Column(name="isReset")
	private boolean isReset;
	
	@Column(name="manage_project")
	private boolean manageProject;

	@Column(name="manage_team")
	private boolean manageTeam;
	
	@Column(name="manage_customer")
	private boolean manageCustomer;
	
	
	@Column(name="admin_flag")
	private int adminFlag;


	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean IsReset() {
		return isReset;
	}

	public void setReset(boolean isReset) {
		this.isReset = isReset;
	}
	
	public boolean isManageProject() {
		return manageProject;
	}

	public void setManageProject(boolean manageProject) {
		this.manageProject = manageProject;
	}

	public boolean isManageTeam() {
		return manageTeam;
	}

	public void setManageTeam(boolean manageTeam) {
		this.manageTeam = manageTeam;
	}

	public boolean isManageCustomer() {
		return manageCustomer;
	}

	public void setManageCustomer(boolean manageCustomer) {
		this.manageCustomer = manageCustomer;
	}
	public int getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(int adminFlag) {
		this.adminFlag = adminFlag;
	}


}
