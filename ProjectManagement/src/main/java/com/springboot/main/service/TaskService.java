package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Task;
import com.springboot.main.repository.EmployeeRepository;
import com.springboot.main.repository.TaskRepository;
import com.springboot.main.repository.WorklogRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private WorklogRepository worklogRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public Task insert(Task task) {
		
		return taskRepository.save(task);
	}

	public Task getById(int tid) throws InvalidIdException {
		Optional<Task> optional = taskRepository.findById(tid);
		if(!optional.isPresent()) 
			throw new InvalidIdException("Tid is invalid");
		
		return optional.get();
	}

	public Object getTaskwithWorklogId(int wid) {
		return worklogRepository.findById(wid);
		
	}

	public List<Task> getAllTasksWithEmployeeId(int eid) {
		
		return taskRepository.findByEmployeeId(eid);
	}

	public List<Task> getAll() {
		
		return taskRepository.findAll();
	}



}
