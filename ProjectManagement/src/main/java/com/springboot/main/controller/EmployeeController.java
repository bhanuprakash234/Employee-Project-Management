package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.EmployeeProject;
import com.springboot.main.model.Manager;
import com.springboot.main.model.Project;
import com.springboot.main.model.User;
import com.springboot.main.service.EmployeeProjectService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.ProjectService;
import com.springboot.main.service.UserService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmployeeProjectService employeeProjectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/add/{mid}")
	public ResponseEntity<?> insertEmployee(@PathVariable("mid")int mid,
			@RequestBody Employee employee) {
		
		
		
		try {
			Manager manager = managerService.getById(mid);
			
			
		
		//save user info in db
		User user=employee.getUser();
		//// i am encrypting the password
		String passwordPlain = user.getPassword();
		
		String encodedPassword = passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		
		user.setRole("EMPLOYEE");
		employee.setRole("EMPLOYEE");
		employee.setEmail(user.getEmail());
		employee.setManager(manager);
		
		user = userService.insert(user);
		// attach the saved user(in step 1)
		employee.setUser(user);
		
		
		
		employee= employeeService.insert(employee);
		
		
		 return ResponseEntity.ok().body(manager);
		
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/project/add/{eid}/{pid}")
	public ResponseEntity<?> assignProject(@PathVariable("eid") int eid,@PathVariable("pid")int pid,@RequestBody EmployeeProject employeeProject){
		
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
	

}
