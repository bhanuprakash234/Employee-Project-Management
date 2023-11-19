package com.springboot.main.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import com.springboot.main.enums.Status;


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
private Backlog backlog;






public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Backlog getBacklog() {
	return backlog;
}
public void setBacklog(Backlog backlog) {
	this.backlog = backlog;
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


}
