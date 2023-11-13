package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
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
	
	public WorkLog getWorkLogById(int id) throws InvalidIdException{
		Optional<WorkLog> optional = worklogRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("WokLog Id Invalid");
		WorkLog worklog = optional.get();
		return worklog;
	}
	public void deleteWorkLog(int id) {
		worklogRepository.deleteById(id);
	}





	public WorkLog insertWorkLog(WorkLog worklog) {
		// TODO Auto-generated method stub
		return worklogRepository.save(worklog);
	}

}
