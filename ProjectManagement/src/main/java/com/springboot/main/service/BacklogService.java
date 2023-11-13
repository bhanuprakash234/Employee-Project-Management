package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.model.Task;
import com.springboot.main.repository.BacklogRepository;
import com.springboot.main.repository.TaskRepository;

@Service
public class BacklogService {
	
	@Autowired
	private BacklogRepository  backlogRepository;

	@Autowired
	private TaskRepository  taskRepository;
	public Backlog insert(Backlog backlog) {
	
		return backlogRepository.save(backlog);
	}

	public Backlog getById(int bid) throws InvalidIdException {
		Optional<Backlog>optional = backlogRepository.findById(bid);
		if(!optional.isPresent())
			throw new InvalidIdException("bid is inavlid");
		return optional.get();
	}



	public List<Backlog> getAllBacklog(Pageable pageable) {
		
		return backlogRepository.findAll(pageable).getContent();
	}

	public List<Backlog> getBacklogByProjectId(int pid) {
		
		return backlogRepository.getBacklogByProjectId(pid);
	}
	
	public Backlog getBacklogById(int id) throws InvalidIdException{
		Optional<Backlog> optional = backlogRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("Backlog Id Is Invalid");
		Backlog backlog = optional.get();
		return backlog;
	}
	
	public void deleteBacklog(int id) {
		backlogRepository.deleteById(id);
	}


}
