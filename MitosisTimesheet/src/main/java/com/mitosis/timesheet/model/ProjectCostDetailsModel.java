package com.mitosis.timesheet.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement
@Entity
@Table(name = "project_cost_details")
public class ProjectCostDetailsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="project_cost_hdr_id")
	private Integer projectCostHdr;

	@ManyToOne(targetEntity = UserDetailsModel.class)
	@JoinColumn(name = "employee_id", nullable = false, referencedColumnName = "id")
	private UserDetailsModel employee;


	@Column(name="rate")
	private BigDecimal rate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Integer getProjectCostHdr() {
		return projectCostHdr;
	}

	public void setProjectCostHdr(Integer projectCostHdr) {
		this.projectCostHdr = projectCostHdr;
	}

	public UserDetailsModel getEmployee() {
		return employee;
	}

	public void setEmployee(UserDetailsModel employee) {
		this.employee = employee;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}


