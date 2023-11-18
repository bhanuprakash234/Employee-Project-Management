package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.enums.Role;
import com.springboot.main.enums.Status;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Project;
import com.springboot.main.model.Sprint;
import com.springboot.main.service.ProjectService;
import com.springboot.main.service.SprintService;



@RestController

public class ProjectController {
	

	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/project/add")
	public  ResponseEntity<?> insertProject(
			@RequestBody Project project){
	
		
		
		project.setStartDate(LocalDate.now());
	
		project.setStatus(Status.TO_DO);
		project = projectService.insert(project);
	return ResponseEntity.ok().body(project);

}
	

	@GetMapping("/project/getAll")
	public List<Project> getAllProject(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return projectService.getAllProject(pageable);
	}
	
	@GetMapping("/project/one/{pid}")
	public ResponseEntity<?> getById(@PathVariable("pid") int pid) {
		try {
			Project project = projectService.getById(pid);
			return ResponseEntity.ok().body(project);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@DeleteMapping("/project/delete/{pid}")
	public ResponseEntity<?> deleteProject(@PathVariable("pid") int pid) {
		try {
			Project project = projectService.getById(pid);
			projectService.deleteProject(pid);
			return ResponseEntity.ok().body("Project deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/project/update/{pid}")
	public ResponseEntity<?> updateProject(@PathVariable("pid") int pid, 
			@RequestBody Project newProject) {
		try {
			Project project = projectService.getById(pid);
			if(newProject.getLongDesc() != null)
				project.setLongDesc(newProject.getLongDesc());
			if(newProject.getEndDate() != null)
				project.setEndDate(newProject.getEndDate());
			if(newProject.getStatus() != null)
				project.setStatus(Status.TO_DO);
			project = projectService.insertEmployee(project);
			return ResponseEntity.ok().body(project);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
