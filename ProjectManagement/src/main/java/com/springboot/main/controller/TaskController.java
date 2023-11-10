package com.springboot.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Task;
import com.springboot.main.repository.TaskRepository;
import com.springboot.main.service.BacklogService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.TaskService;

@RestController

public class TaskController {
	
	@Autowired
	private BacklogService backlogService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TaskService taskService;

	@PostMapping("task/add/{bid}/{eid}")
	public ResponseEntity<?> CreateTask(@PathVariable("bid")int bid,
			          @PathVariable("eid")int eid,
		          @RequestBody Task task) {
		
		try {
		Backlog backlog = backlogService.getById(bid);
		
		Employee employee = employeeService.getById(eid);
		
		task.setBacklog(backlog);
		task.setEmployee(employee);
		
		task = taskService.insert(task);
		  return ResponseEntity.ok().body(task);
	}catch(InvalidIdException e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	}
}
	@GetMapping("/task/{wid}")
	public Object getTaskwithWorkLogId(@PathVariable("wid")int wid) {
		
		return taskService.getTaskwithWorklogId(wid);
		
	
	
	}

	@GetMapping("/task/employee/{eid}")
	public List<Task> getAllTasksWithEmployeeId(@PathVariable("eid")int eid){
		return taskService.getAllTasksWithEmployeeId(eid);
	}
	
	@GetMapping("/task/getAll")
	public List<Task> getAll() {
		return taskService.getAll();
	}
}