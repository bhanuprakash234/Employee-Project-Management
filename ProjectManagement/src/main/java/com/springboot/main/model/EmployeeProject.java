package com.springboot.main.model;

import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "employee_project")
public class EmployeeProject {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Employee employee;
	
	@ManyToOne
	private Project project;
	
	
	
	
	
    
	

	

	

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	

	

}
