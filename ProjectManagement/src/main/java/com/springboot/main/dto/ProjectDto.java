package com.springboot.main.dto;

import com.springboot.main.enums.Status;

public class ProjectDto {
	
	private int id;
	
	private Status status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	

}
