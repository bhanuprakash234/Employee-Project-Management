package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Manager;
import com.springboot.main.model.User;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.UserService;

@RestController

public class ManagerController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/manager/add")//:Adding Manager
	public Manager insertManager(@RequestBody Manager manager) {
		//save user info in db
		User user=manager.getUser();
		//// i am encrypting the password
		String passwordPlain = user.getPassword();
		
		String encodedPassword = passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		
		user.setRole(Role.MANAGER);
		manager.setRole(Role.MANAGER);
		manager.setEmail(user.getEmail());
		user = userService.insert(user);
		// attach the saved user(in step 1)
		manager.setUser(user);
		
		
		return managerService.insert(manager);

}

	@GetMapping("/manager/getAll")
	public List<Manager> getAllManager(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return managerService.getAllManager(pageable);
	}
	
	@GetMapping("/manager/one/{mid}")
	public ResponseEntity<?> getManagerById(@PathVariable("mid") int mid) {
		try {
			Manager manager = managerService.getById(mid);
			return ResponseEntity.ok().body(manager);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/manager/delete/{mid}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("mid") int mid)  {
		try {
		Manager manager = managerService.getById(mid);
		managerService.deleteManager(mid);
		return ResponseEntity.ok().body("Manager Record Is deleted");
	} catch (InvalidIdException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	}

	@PutMapping("/manager/update/{mid}")
	public ResponseEntity<?> updateManager(@PathVariable("mid") int mid, 
			@RequestBody Manager newManager) {
		try {
			Manager manager = managerService.getById(mid);
			if(newManager.getName() != null)
				manager.setName(newManager.getName());
			if(newManager.getEmail() != null)
				manager.setEmail(newManager.getEmail());
			if(newManager.getRole() != null)
				manager.setRole(Role.MANAGER);
			
			manager = managerService.insertManager(manager);
			return ResponseEntity.ok().body(manager);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	

}

}