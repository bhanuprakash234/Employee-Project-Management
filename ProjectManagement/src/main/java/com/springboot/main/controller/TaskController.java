package com.springboot.main.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.enums.Status;
import com.springboot.main.exception.IllegalArgumentException;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Project;
import com.springboot.main.model.Sprint;
import com.springboot.main.model.Task;
import com.springboot.main.model.WorkLog;
import com.springboot.main.repository.TaskRepository;
import com.springboot.main.service.BacklogService;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ProjectService;
import com.springboot.main.service.SprintService;
import com.springboot.main.service.TaskService;
import com.springboot.main.service.WorklogService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})

public class TaskController {
	
	@Autowired
	private BacklogService backlogService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private WorklogService worklogService;

	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("task/add/{sid}/{eid}")
	public ResponseEntity<?> CreateTask(
			          @PathVariable("eid")int eid,
			          @PathVariable("sid")int sid,
		          @RequestBody Task task) {
		
		try {
		
		Sprint sprint = sprintService.getById(sid);
		Employee employee = employeeService.getById(eid);
		
		
		task.setEmployee(employee);
		task.setSprint(sprint);
		task.setStatus(Status.TO_DO);
		
		task = taskService.insert(task);
		  return ResponseEntity.ok().body(task);
	}catch(InvalidIdException e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	}
}
	@GetMapping("/task/{sid}")
	public ResponseEntity<?> getTasksBySprintId(@PathVariable("sid") int sid) {
		try {
			Sprint sprint = sprintService.getById(sid);
			List<Task> list = taskService.getTasksBySprintId(sid);
			return ResponseEntity.ok().body(list);
	}catch(InvalidIdException e) {
		 return ResponseEntity.badRequest().body(e.getMessage());
	}
	}

	@GetMapping("/task/employee/{eid}")//:To get a task by employeeId
	public ResponseEntity<?> getAllTasksWithEmployeeId(@PathVariable("eid")int eid){
		try {
			Employee employee = employeeService.getById(eid);
		List<Task> list =  taskService.getAllTasksWithEmployeeId(eid);
		return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/task/project/{pid}")//:To get a tasks by projectId
	public ResponseEntity<?> getAllTasksByProjectId(@PathVariable("pid")int pid){
		try {
			Project project = projectService.getById(pid);
		List<Task> list =  taskService.getAllTasksByProjectId(pid);
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
	
	@GetMapping("/task/one/{tid}")
	public ResponseEntity<?> getById(@PathVariable("tid") int tid) {
		try {
			Task task = taskService.getById(tid);
			return ResponseEntity.ok().body(task);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/search/taskName/{title}")
	public ResponseEntity<?> getByTaskTitle(@PathVariable String title) {
		
			List<Task> list = taskService.getByTaskTitle(title);
			return ResponseEntity.ok().body(list);
			
			
		
	}
	
	@GetMapping("/search/task/{qStr}")
	public List<Task> searchByTaskName(@PathVariable("qStr") String qStr) {
		List<Task> list= taskService.searchByTaskName(qStr);
		return list; 
	}
	
	@DeleteMapping("/task/delete/{tid}")
	public ResponseEntity<?> deleteTask(@PathVariable("tid") int tid) {
		try {
			Task task = taskService.getById(tid);
			taskService.deleteTask(tid);
			return ResponseEntity.ok().body("Task deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	@PutMapping("/update/{tid}")
	public ResponseEntity<?> updateTaskr(@PathVariable("tid") int tid, 
			@RequestBody Task newTask) {
		try {
			Task task = taskService.getById(tid);
			if(newTask.getDetails() != null)
				task.setDetails(newTask.getDetails());
			if(newTask.getStatus() != null)
				task.setStatus(newTask.getStatus());
			
			
			task =taskService.insertTask(task);
			return ResponseEntity.ok().body(task);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}