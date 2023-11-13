package com.springboot.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Task;
import com.springboot.main.model.WorkLog;
import com.springboot.main.repository.TaskRepository;
import com.springboot.main.service.BacklogService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.TaskService;
import com.springboot.main.service.WorklogService;

@RestController

public class TaskController {
	
	@Autowired
	private BacklogService backlogService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private WorklogService worklogService;

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
	@GetMapping("/task/{bid}")
	public ResponseEntity<?> getTaskwithWorkLogId(@PathVariable("bid")int bid) {
		
		try {
			Backlog backlog = backlogService.getById(bid);
		
		List<Task> list =taskService.getTaskwithBacklogId(bid);
		return ResponseEntity.ok().body(list);
		
		}catch(InvalidIdException e) {
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	
	}

	@GetMapping("/task/employee/{eid}")
	public ResponseEntity<?> getAllTasksWithEmployeeId(@PathVariable("eid")int eid){
		try {
			Employee employee = employeeService.getById(eid);
		List<Task> list =  taskService.getAllTasksWithEmployeeId(eid);
		return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

	@GetMapping("/task/getAll")
	public List<Task> getAllTask(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return taskService.getAllTask(pageable);
	}
	
	@GetMapping("/task/one/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable("id") int id) {
		try {
			Task task = taskService.getTaskById(id);
			return ResponseEntity.ok().body(task);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/task/delete/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable("id") int id) {
		try {
			Task task = taskService.getTaskById(id);
			taskService.deleteTask(task.getId());
			return ResponseEntity.ok().body("Task deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateTaskr(@PathVariable("id") int id, 
			@RequestBody Task newTask) {
		try {
			Task task = taskService.getTaskById(id);
			if(newTask.getDetails() != null)
				task.setDetails(newTask.getDetails());
			
			task =taskService.insertTask(task);
			return ResponseEntity.ok().body(task);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}