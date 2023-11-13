package com.springboot.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.EmployeeProject;
import com.springboot.main.model.Manager;
import com.springboot.main.model.Project;
import com.springboot.main.model.Task;
import com.springboot.main.model.User;
import com.springboot.main.service.EmployeeProjectService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.ProjectService;
import com.springboot.main.service.TaskService;
import com.springboot.main.service.UserService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TaskService taskService;
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
			
			
			
		
		//save user info in db
		User user=employee.getUser();
		//// i am encrypting the password
		String passwordPlain = user.getPassword();
		
		String encodedPassword = passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		
		user.setRole("EMPLOYEE");
		employee.setRole("EMPLOYEE");
		employee.setEmail(user.getEmail());
		Manager manager = managerService.getById(mid);
		employee.setManager(manager);
		
		user = userService.insert(user);
		// attach the saved user(in step 1)
		employee.setUser(user);
		
		
		
		employee= employeeService.insert(employee);
		
		
		 return ResponseEntity.ok().body(employee);
		
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	


	@GetMapping("/manager/{mid}")
	public ResponseEntity<?> getEmployeesByManager(@PathVariable("mid")int mid) {
		try {
			
		Manager manager = managerService.getById(mid);
		List<Employee> list =  employeeService.getEmployeesByManager(mid);
		return ResponseEntity.ok().body(list);
	}catch(InvalidIdException e) {
		return ResponseEntity.ok().body(e.getMessage());
	}
	
	
	}

	@GetMapping("/getAll")
	public List<Employee> getAllEmployee(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return employeeService.getAllEmployee(pageable);
	}

}
