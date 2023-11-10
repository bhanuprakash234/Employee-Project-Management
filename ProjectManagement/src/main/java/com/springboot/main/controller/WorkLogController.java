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
import com.springboot.main.model.Task;
import com.springboot.main.model.WorkLog;
import com.springboot.main.service.TaskService;
import com.springboot.main.service.WorklogService;

@RestController
@RequestMapping("/worklog")
public class WorkLogController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private WorklogService worklogService;
	
	@PostMapping("/add/{tid}")
	public ResponseEntity<?> InsertWorklog(@PathVariable("tid")int tid,
			                  @RequestBody WorkLog worklog) {
		try {
			Task task = taskService.getById(tid);
			worklog.setTask(task);
		worklog = worklogService.insert(worklog);
		return ResponseEntity.ok().body(worklog);
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/task/{tid}")
	public List<WorkLog> getWorkLogsAndEmployeeWithTaskByTaskId(@PathVariable("tid")int tid){
		return worklogService.getWorkLogsAndEmployeeWithTaskByTaskId(tid);
	}
	

}
