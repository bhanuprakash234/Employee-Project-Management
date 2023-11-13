package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
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
	

	@GetMapping("/getAll")
	public List<Project> getAllProject(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return projectService.getAllProject(pageable);
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getProjectById(@PathVariable("id") int id) {
		try {
			Project project = projectService.getProjectById(id);
			return ResponseEntity.ok().body(project);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProject(@PathVariable("id") int id) {
		try {
			Project project = projectService.getProjectById(id);
			projectService.deleteProject(project.getId());
			return ResponseEntity.ok().body("Project deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
