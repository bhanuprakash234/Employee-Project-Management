package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.main.enums.Status;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Manager;
import com.springboot.main.model.Project;

import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.ProjectService;



@RestController
@CrossOrigin(origins = {"http://localhost:3000"})

public class ProjectController {
	

	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private  EmployeeService employeeService;
	
	@Autowired
	private Logger logger;
	
	@PostMapping("/project/add/{mid}")
	public  ResponseEntity<?> insertProject(@PathVariable("mid")int mid,
			@RequestBody Project project){
		try {
		
		Manager manager = managerService.getById(mid);
		project.setManager(manager);
	
		
		
		project.setStartDate(LocalDate.now());
	
		project.setStatus(Status.TO_DO);
		project = projectService.insert(project);
		
		logger.info(project.getTitle()+"named project added successfully");
	return ResponseEntity.ok().body(project);

} catch (InvalidIdException e) {
	logger.error("issue in adding project");
	return ResponseEntity.badRequest().body(e.getMessage());
}
}
	

	@GetMapping("/project/getAll")
	public List<Project> getAllProject(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return projectService.getAllProject(pageable);
	}
	@GetMapping("/project/getAll/manager/{mid}")
	public ResponseEntity<?> getAllProjectByManagerId( @PathVariable("mid")int mid,
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		try {
			Manager manager= managerService.getById(mid);
		
		List<Project> list = projectService.getByManagerId(mid);
		logger.info("got projects for manager:"+manager.getName());
		return ResponseEntity.ok().body(list);
		
	}catch (InvalidIdException e) {
		logger.error("issue in getting projects by manager with id :"+mid);
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	}
	@GetMapping("/project/getAll/employee/{eid}")
	public ResponseEntity<?> getAllProjectByEmployeeId( @PathVariable("eid")int eid,
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		try {
			Employee employee = employeeService.getById(eid);
		
		List<Project> list = projectService.getByEmployeeId(eid);
		logger.info("got projects for employee:"+employee.getName());
		return ResponseEntity.ok().body(list);
		
	}catch (InvalidIdException e) {
		logger.error("issue in getting projects by employee with id:"+eid);
		return ResponseEntity.badRequest().body(e.getMessage());
	}
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
			if(newProject.getTitle() != null)
				project.setTitle(newProject.getTitle());
			if(newProject.getEndDate() != null)
				project.setEndDate(newProject.getEndDate());
			if(newProject.getStatus() != null)
				project.setStatus(Status.TO_DO);
			project = projectService.insertEmployee(project);
			logger.info("updated "+project.getTitle());
			return ResponseEntity.ok().body(project);
		} catch (InvalidIdException e) {
			logger.error("Issue in updating project");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/search/project/{qStr}")
	public List<Project> searchByProjectName(@PathVariable("qStr") String qStr) {
		List<Project> list= projectService.searchByProjectName(qStr);
		logger.info("got projects"+list);
		return list; 
	}
	
	
	}

