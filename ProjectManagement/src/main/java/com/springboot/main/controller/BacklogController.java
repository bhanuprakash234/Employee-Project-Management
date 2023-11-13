package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Project;
import com.springboot.main.model.Task;
import com.springboot.main.service.BacklogService;
import com.springboot.main.service.ProjectService;
import com.springboot.main.service.TaskService;

@RestController
@RequestMapping("/backlog")
public class BacklogController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BacklogService backlogService;
	
	@Autowired
	private TaskService taskService;

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
	@GetMapping("/{pid}")
	public ResponseEntity<?> getBacklogByProjectId(@PathVariable("pid")int pid) {
		
		try {
			Project project = projectService.getById(pid);
		
		List<Backlog> list = backlogService.getBacklogByProjectId(pid);
		
		return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			return ResponseEntity.ok().body(e.getMessage());
		}
	}
	
	@GetMapping("/getAll")
	public List<Backlog> getAllEmployee(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return backlogService.getAllBacklog(pageable);
	}
	
}