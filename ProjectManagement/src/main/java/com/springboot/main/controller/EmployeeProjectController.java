package com.springboot.main.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.EmployeeProject;
import com.springboot.main.model.Manager;
import com.springboot.main.model.Project;
import com.springboot.main.service.EmployeeProjectService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.ProjectService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})

public class EmployeeProjectController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private EmployeeProjectService employeeProjectService;

	@PostMapping("/employeeproject/add/{eid}/{pid}")
	public ResponseEntity<?> assignProject(@PathVariable("eid") int eid,@PathVariable("pid")int pid
			                                ){
			                                            
		
		try {
			
			//step-1:
			  EmployeeProject employeeProject = new EmployeeProject();
			  Employee employee= employeeService.getById(eid);
			  
			  //step-2:
			  Project project= projectService.getById(pid);
			  
			  //step-3:
			  employeeProject.setEmployee(employee);
			  
			  employeeProject.setProject(project);
			  
			  
			  
			  //step:4
			  employeeProject=employeeProjectService.insert(employeeProject);
			  return ResponseEntity.ok().body(employeeProject);
					  
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/employeeproject/getAll")
	public List<EmployeeProject> getAllEmployeeProject(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return employeeProjectService.getAllEmployeeProject(pageable);
	}
	@GetMapping("/employeeproject/one/{id}")
	public ResponseEntity<?> getEmployeeProjectById(@PathVariable("id") int id) {
		try {
			EmployeeProject employeeproject = employeeProjectService.getEmployeeProjectById(id);
			return ResponseEntity.ok().body(employeeproject);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/employeeproject/manager/{mid}")
	public ResponseEntity<?> getEmployeeProjectByManagerId(@PathVariable("mid") int mid) {
		try {
			Manager manager = managerService.getById(mid);
			List<EmployeeProject> list = employeeProjectService.getEmployeeProjectByManagerId(mid);
			return ResponseEntity.ok().body(list);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/employeeproject/delete/{id}")
	public ResponseEntity<?> deleteEmployeeProject(@PathVariable("id") int id) {
		try {
			EmployeeProject employeeproject = employeeProjectService.getEmployeeProjectById(id);
			employeeProjectService.deleteEmployeeProject(employeeproject.getId());
			return ResponseEntity.ok().body("EmployeeProject Record deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
}
