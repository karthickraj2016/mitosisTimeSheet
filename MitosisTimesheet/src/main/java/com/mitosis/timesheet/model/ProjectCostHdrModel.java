package com.mitosis.timesheet.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "project_cost_hdr")
public class ProjectCostHdrModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToMany(mappedBy="projectCostHdr")
	private List<ProjectCostDetailsModel> projectCostDetails;

	@ManyToOne(targetEntity = ProjectModel.class)
	@JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
	private ProjectModel project;

	@Column(name = "project_type")
	private String projectType;

	@Column(name = "project_cost")
	private BigDecimal projectCost;

	@Column(name = "currency_code")
	private String currencyCode;

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

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public BigDecimal getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(BigDecimal projectCost) {
		this.projectCost = projectCost;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<ProjectCostDetailsModel> getProjectCostDetails() {
		return projectCostDetails;
	}

	public void setProjectCostDetails(
			List<ProjectCostDetailsModel> projectCostDetails) {
		this.projectCostDetails = projectCostDetails;
	}


}
