package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Task;
import com.springboot.main.model.User;
import com.springboot.main.service.UserService;

@RestController

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/getAll")
	public List<User> getAllUser(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return userService.getAllUser(pageable);
	}
	
	@GetMapping("/user/one/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
		try {
			User user = userService.getUserById(id);
			return ResponseEntity.ok().body(user);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
		try {
			User user = userService.getUserById(id);
			userService.deleteUser(user.getId());
			return ResponseEntity.ok().body("User Record deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PutMapping("/user/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") int id, 
			@RequestBody User newUser) {
		try {
			User user = userService.getUserById(id);
			if(newUser.getPassword() != null)
				user.setPassword(newUser.getPassword());
			if(newUser.getEmail() != null)
				user.setEmail(newUser.getEmail());
			
			
			user =userService.insertUser(user);
			return ResponseEntity.ok().body(user);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
