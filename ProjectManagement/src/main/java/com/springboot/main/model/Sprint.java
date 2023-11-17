package com.springboot.main.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
<<<<<<< HEAD

import com.springboot.main.enums.Status;
=======
>>>>>>> bde90b3b5921ecaea197ac42d3823e001bd5314a

@Entity
public class Sprint {
@Id	
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String title;
private String duration;

@Enumerated(EnumType.STRING)
private Status status;

@ManyToOne
private Project project;

<<<<<<< HEAD
=======
private String status;

@ManyToOne
private Project project;

>>>>>>> bde90b3b5921ecaea197ac42d3823e001bd5314a
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}

public Status getStatus() {
	return status;
}
public void setStatus(Status status) {
	this.status = status;
}
public Project getProject() {
	return project;
}
public void setProject(Project project) {
	this.project = project;
}
<<<<<<< HEAD




=======
>>>>>>> bde90b3b5921ecaea197ac42d3823e001bd5314a

}
