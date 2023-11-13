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

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Manager;
import com.springboot.main.model.User;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.UserService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/add")
	public Manager insertManager(@RequestBody Manager manager) {
		//save user info in db
		User user=manager.getUser();
		//// i am encrypting the password
		String passwordPlain = user.getPassword();
		
		String encodedPassword = passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		
		user.setRole("MANAGER");
		manager.setRole("MANAGER");
		manager.setEmail(user.getEmail());
		user = userService.insert(user);
		// attach the saved user(in step 1)
		manager.setUser(user);
		
		
		return managerService.insert(manager);

}

	@GetMapping("/getAll")
	public List<Manager> getAllManager(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return managerService.getAllManager(pageable);
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getManagerById(@PathVariable("id") int id) {
		try {
			Manager manager = managerService.getManagerById(id);
			return ResponseEntity.ok().body(manager);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) throws InvalidIdException {
		Manager manager = managerService.getById(id);
		managerService.deleteManager(manager.getId());
		return ResponseEntity.ok().body("Manager Record Is deleted");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateManager(@PathVariable("id") int id, 
			@RequestBody Manager newManager) {
		try {
			Manager manager = managerService.getManagerById(id);
			if(newManager.getName() != null)
				manager.setName(newManager.getName());
			if(newManager.getEmail() != null)
				manager.setEmail(newManager.getEmail());
			
			
			manager = managerService.insertManager(manager);
			return ResponseEntity.ok().body(manager);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}