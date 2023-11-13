package com.springboot.main.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Sprint;
import com.springboot.main.service.SprintService;

@RestController
@RequestMapping("/sprint")
public class SprintController {
	
	@Autowired
	private SprintService sprintService;
	
	@PostMapping("/add")
	public Sprint CreateSprint(@RequestBody Sprint sprint) {
		sprint.setStatus("TO DO");
		return sprintService.insert(sprint);
	}
	

	@GetMapping("/getAll")
	public List<Sprint> getAllSprint(
			                             @RequestParam(value="page",required=false,defaultValue="0")Integer page,
			                             @RequestParam(value="size",required=false,defaultValue="111111111")Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return sprintService.getAllSprint(pageable);
	}
	
	@GetMapping("/one/{sid}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("sid") int sid) {
		try {
			Sprint sprint = sprintService.getById(sid);
			return ResponseEntity.ok().body(sprint);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{sid}")
	public ResponseEntity<?> deleteSprint(@PathVariable("sid") int sid) {
		try {
			Sprint sprint = sprintService.getById(sid);
			sprintService.deleteSprint(sid);
			return ResponseEntity.ok().body("Sprint deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSprint(@PathVariable("id") int id, 
			@RequestBody Sprint newSprint) {
		try {
			Sprint sprint = sprintService.getSprintById(id);
			if(newSprint.getDuration() != null)
				sprint.setDuration(newSprint.getDuration());
			if(newSprint.getStatus() != null)
				sprint.setStatus(newSprint.getStatus());
			
			
			sprint = sprintService.insertEmployee(sprint);
			return ResponseEntity.ok().body(sprint);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

}
