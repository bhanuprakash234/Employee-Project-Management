package com.springboot.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sprint {
@Id	
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String title;
private String duration;


private String status;
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
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}





}
