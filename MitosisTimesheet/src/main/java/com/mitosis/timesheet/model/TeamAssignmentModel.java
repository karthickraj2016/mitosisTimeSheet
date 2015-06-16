package com.mitosis.timesheet.model;

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
@Table(name = "team_details")

public class TeamAssignmentModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne(targetEntity = ProjectModel.class)
	@JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
	private ProjectModel projectId;
	
	@ManyToOne(targetEntity = UserDetailsModel.class)
	@JoinColumn(name = "member_id", nullable = false, referencedColumnName = "id")
	private ProjectModel memberId;
	
	@ManyToOne(targetEntity = ProjectModel.class)
	@JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
	private ProjectModel roleId;
}
