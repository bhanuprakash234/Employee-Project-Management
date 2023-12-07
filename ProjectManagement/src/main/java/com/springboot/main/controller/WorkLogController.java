package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Task;
import com.springboot.main.model.WorkLog;
import com.springboot.main.service.TaskService;
import com.springboot.main.service.WorklogService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})

public class WorkLogController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private WorklogService worklogService;
	
	@PostMapping("/worklog/add/{tid}")//:To add a work log/comment
	public ResponseEntity<?> InsertWorklog(@PathVariable("tid")int tid,
			                  @RequestBody WorkLog worklog) {
		try {
			Task task = taskService.getById(tid);
			worklog.setTask(task);
			worklog.setLogDate(LocalDate.now());
		worklog = worklogService.insert(worklog);
		return ResponseEntity.ok().body(worklog);
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/worklog/task/{tid}")//:To get a work log/comment based on a task
	public ResponseEntity<?> getWorkLogsAndEmployeeWithTaskByTaskId(@PathVariable("tid")int tid){
		
		try {
			Task task = taskService.getById(tid);
		List<WorkLog> list =  worklogService.getWorkLogsAndEmployeeWithTaskByTaskId(tid);
		return ResponseEntity.ok().body(list);
		
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/worklog/getAll")
	public List<WorkLog> getAllWorklog(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return worklogService.getAllWorklog(pageable);
	}
	
	@GetMapping("/worklog/one/{id}")
	public ResponseEntity<?> getWorkLogById(@PathVariable("id") int id) {
		try {
			WorkLog worklog = worklogService.getWorkLogById(id);
			return ResponseEntity.ok().body(worklog);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/worklog/delete/{id}")
	public ResponseEntity<?> deleteWorkLog(@PathVariable("id") int id) {
		try {
			WorkLog worklog = worklogService.getWorkLogById(id);
			worklogService.deleteWorkLog(worklog.getId());
			return ResponseEntity.ok().body("Worklog Record deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PutMapping("/worklog/update/{id}")
	public ResponseEntity<?> updateWorkLog(@PathVariable("id") int id, 
			@RequestBody WorkLog newWorkLog) {
		try {
			WorkLog worklog = worklogService.getWorkLogById(id);
			if(newWorkLog.getLog() != null)
				worklog.setLog(newWorkLog.getLog());
			
			
			worklog = worklogService.insertWorkLog(worklog);
			return ResponseEntity.ok().body(worklog);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
