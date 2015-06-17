package com.mitosis.timesheet.pojo;

public class UserDetailsVo {

	private int id;
	private String name;
	private String userName;
	private String password;
	private String eMail;
	private boolean active;
	private String role;
	private boolean isReset;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public boolean isReset() {
		return isReset;
	}

	public void setReset(boolean isReset) {
		this.isReset = isReset;
	}

	

}
