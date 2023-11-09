package com.springboot.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Backlog;
import com.springboot.main.repository.BacklogRepository;

@Service
public class BacklogService {
	
	@Autowired
	private BacklogRepository  backlogRepository;

	public Backlog insert(Backlog backlog) {
	
		return backlogRepository.save(backlog);
	}

	public Backlog getById(int bid) throws InvalidIdException {
		Optional<Backlog>optional = backlogRepository.findById(bid);
		if(!optional.isPresent())
			throw new InvalidIdException("bid is inavlid");
		return optional.get();
	}

}
