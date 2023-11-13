package com.springboot.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.model.WorkLog;
import com.springboot.main.repository.WorklogRepository;

@Service
public class WorklogService {
	
	@Autowired
	private WorklogRepository worklogRepository;
	
	

	public WorkLog insert(WorkLog worklog) {
		
		return worklogRepository.save(worklog);
	}





	public List<WorkLog> getWorkLogsAndEmployeeWithTaskByTaskId(int tid) {
		
		return worklogRepository.findByTaskId(tid);
	}





	public List<WorkLog> getAllWorklog(Pageable pageable) {
		
		return worklogRepository.findAll(pageable).getContent();
	}

}
