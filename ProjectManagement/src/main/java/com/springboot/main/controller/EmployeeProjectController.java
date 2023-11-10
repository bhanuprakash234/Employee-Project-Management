package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.EmployeeProject;
import com.springboot.main.model.Project;
import com.springboot.main.service.EmployeeProjectService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ProjectService;

@RestController
@RequestMapping("/employeeproject")
public class EmployeeProjectController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmployeeProjectService employeeProjectService;

	@PostMapping("/add/{eid}/{pid}")
	public ResponseEntity<?> assignProject(@PathVariable("eid") int eid,@PathVariable("pid")int pid,
			                                @RequestBody EmployeeProject employeeProject){
			                                            
		
		try {
			
			//step-1:
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
	
	@GetMapping("/getAll")
	public List<EmployeeProject> getAllEmployeeProject() {
		return employeeProjectService.getAllEmployeeProject();
	}
}
