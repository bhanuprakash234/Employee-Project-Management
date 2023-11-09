package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Project;
import com.springboot.main.model.Sprint;
import com.springboot.main.service.ProjectService;
import com.springboot.main.service.SprintService;



@RestController
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/add/{sid}")
	public ResponseEntity<?> insertProject(@PathVariable("sid")int sid,
			@RequestBody Project project){
	try{
		
		Sprint sprint = sprintService.getById(sid);
		
		project.setSprint(sprint);
		project.setStatus("TO DO");
		project = projectService.insert(project);
		return ResponseEntity.ok().body(project);
	} catch(InvalidIdException e) {
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
}
