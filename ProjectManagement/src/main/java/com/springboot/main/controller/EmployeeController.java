package com.springboot.main.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.springboot.main.enums.Role;
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
@CrossOrigin(origins = {"http://localhost:3000"})

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
	
	@Autowired
	private Logger logger;
	
	@PostMapping("/employee/add/{mid}")//:Adding Employee
	public ResponseEntity<?> insertEmployee(@PathVariable("mid")int mid,
			@RequestBody Employee employee) {
		try {
		//save user info in db
		User user=employee.getUser();
		//// i am encrypting the password
		String passwordPlain = user.getPassword();
		
		String encodedPassword = passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		
		user.setRole(Role.EMPLOYEE);
		employee.setRole(Role.EMPLOYEE);
		employee.setEmail(user.getEmail());
		Manager manager = managerService.getById(mid);
		employee.setManager(manager);
		
		user = userService.insert(user);
		// attach the saved user(in step 1)
		employee.setUser(user);
		employee= employeeService.insert(employee);
		logger.info("added employee with username :"+ employee.getName());
		return ResponseEntity.ok().body(employee);
		
		}catch(InvalidIdException e) {
			logger.error("added employee with username :"+ employee.getName());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	


	@GetMapping("/employee/manager/{mid}")
	public ResponseEntity<?> getEmployeesByManager(
		    @PathVariable("mid") int mid,
		    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
		    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

		    try {
		        Manager manager = managerService.getById(mid);

		        Pageable pageable = PageRequest.of(page, size);
		        Page<Employee> employeePage = employeeService.getEmployeesByManager(mid, pageable);

		        logger.info("Got employees by manager: " + manager.getName());
		        
		        // You may want to return additional pagination information like total pages, total elements, etc.
		        return ResponseEntity.ok()
		            .body(employeePage);
		    } catch (InvalidIdException e) {
		        logger.error("Error getting employees by manager");
		        return ResponseEntity.badRequest().body(e.getMessage());
		    }
		}

	@GetMapping("/employee/getAll")
	public List<Employee> getAllEmployee(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return employeeService.getAllEmployee(pageable);
	}
	
	@GetMapping("/employee/one/{eid}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("eid") int eid) {
		try {
			Employee employee = employeeService.getById(eid);
			return ResponseEntity.ok().body(employee);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/employee/list/manager/{mid}")
	public ResponseEntity<?> getEmployeeListByManagerId(@PathVariable("mid") int mid) {
		try {
			Manager manager = managerService.getById(mid);
			List<Employee> list = employeeService.getEmployeeListByManagerId(mid);
			return ResponseEntity.ok().body(list);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/employee/delete/{eid}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("eid") int eid) {
		try {
			Employee employee = employeeService.getById(eid);
			employeeService.deleteEmployee(eid);
			logger.info("employee deleted with name:"+employee.getName());
			return ResponseEntity.ok().body("Employee Record deleted");
		} catch (InvalidIdException e) {
			logger.error("Issue in deleting in employee");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/employee/update/{eid}")
	public ResponseEntity<?> updateEmployee(@PathVariable("eid") int eid, 
			@RequestBody Employee newEmployee) {
		try {
			Employee employee = employeeService.getById(eid);
			if(newEmployee.getName() != null)
				employee.setName(newEmployee.getName());
			if(newEmployee.getEmail() != null)
				employee.setEmail(newEmployee.getEmail());
			if(newEmployee.getRole() != null)
				employee.setRole(Role.EMPLOYEE);
			employee = employeeService.insertEmployee(employee);
			return ResponseEntity.ok().body(employee);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/search/employee/name")
	public ResponseEntity<?> SearchByEmployeeName(@RequestParam String name) {
		List<Employee> list = employeeService.SearchByEmployeeName(name);
		return ResponseEntity.ok().body(list);
	}

}
