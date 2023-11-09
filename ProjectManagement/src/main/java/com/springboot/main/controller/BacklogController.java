package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Project;
import com.springboot.main.service.BacklogService;
import com.springboot.main.service.ProjectService;

@RestController
@RequestMapping("/backlog")
public class BacklogController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BacklogService backlogService;

	@PostMapping("/add/{pid}")
	public ResponseEntity<?> CreateBacklog(@PathVariable("pid")int pid,
			                  @RequestBody Backlog backlog) {
	try {	
		Project project = projectService.getById(pid);
		
		backlog.setProject(project);
		
		backlog = backlogService.insert(backlog);
		return ResponseEntity.ok().body(backlog);
	}catch(InvalidIdException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
}