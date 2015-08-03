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
@Table(name = "employee_master")
public class EmployeeMasterModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@ManyToOne(targetEntity = UserDetailsModel.class)
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
	private UserDetailsModel employee;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="joining_date")
	private Date joiningDate;
	
	@Column(name="exp_start_date")
	private Date expStartDate;
	
	@Column(name="years_of_exp")
	private int yearsOfExperience;
	
	@Column(name="months_of_exp")
	private int monthsOfExperience;
	
	@Column(name="level")
	private int level;

	@Column(name="as_on_date")
	private Date asOnDate;
	  
	@Column(name="billable")
	private String billable;
	
	@Transient
	private String joiningEntryDate;
	
	@Transient
	private String expStartEntryDate;
	
	@Transient
	private String asOnEntryDate;
	
	public String getJoiningEntryDate() {
		Date d= getJoiningDate();
		joiningEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return joiningEntryDate;
	}
	public void setJoiningEntryDate(String joiningEntryDate) {
		this.joiningEntryDate = joiningEntryDate;
	}
		
	public String getExpStartEntryDate() {
		Date d= getExpStartDate();
		expStartEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return expStartEntryDate;
	}
	public void setExpStartEntryDate(String expStartEntryDate) {
		this.expStartEntryDate = expStartEntryDate;
	}
	
	public String getAsOnEntryDate() {
		Date d= getAsOnDate();
		asOnEntryDate = new SimpleDateFormat("dd-MM-yyyy").format(d);
		return asOnEntryDate;
	}
	public void setAsOnEntryDate(String asOnEntryDate) {
		this.asOnEntryDate = asOnEntryDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public UserDetailsModel getEmployee() {
		return employee;
	}
	public void setEmployee(UserDetailsModel employee) {
		this.employee = employee;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Date getExpStartDate() {
		return expStartDate;
	}

	public void setExpStartDate(Date expStartDate) {
		this.expStartDate = expStartDate;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public int getMonthsOfExperience() {
		return monthsOfExperience;
	}

	public void setMonthsOfExperience(int monthsOfExperience) {
		this.monthsOfExperience = monthsOfExperience;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getAsOnDate() {
		return asOnDate;
	}

	public void setAsOnDate(Date asOnDate) {
		this.asOnDate = asOnDate;
	}

	public String getBillable() {
		return billable;
	}

	public void setBillable(String billable) {
		this.billable = billable;
	}
	
}
