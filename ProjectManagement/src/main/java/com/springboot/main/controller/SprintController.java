package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.model.Sprint;
import com.springboot.main.service.SprintService;

@RestController
@RequestMapping("/sprint")
public class SprintController {
	
	@Autowired
	private SprintService sprintService;
	
	@PostMapping("/add")
	public Sprint CreateSprint(@RequestBody Sprint sprint) {
		sprint.setStatus("TO DO");
		return sprintService.insert(sprint);
	}
	

}
