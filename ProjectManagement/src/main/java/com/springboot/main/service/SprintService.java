package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Sprint;
import com.springboot.main.repository.SprintRepository;

@Service
public class SprintService {
	
	@Autowired
	private SprintRepository sprintRepository;

	public Sprint insert(Sprint sprint) {
		
		return sprintRepository.save(sprint);
	}

	public Sprint getById(int sid) throws InvalidIdException {
		Optional<Sprint> optional = sprintRepository.findById(sid);
		if(!optional.isPresent())
			throw new InvalidIdException("Sid is invalid");
		return optional.get();
	}

	public List<Sprint> getAllSprint(Pageable pageable) {
		
		return sprintRepository.findAll(pageable).getContent();
	}
	public Sprint getSprintById(int id) throws InvalidIdException{
		Optional<Sprint> optional = sprintRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("Sprint Id Invalid");
		Sprint sprint = optional.get();
		return sprint;

}
	public void deleteSprint(int id) {
		sprintRepository.deleteById(id);
	}

	public Sprint insertEmployee(Sprint sprint) {
		// TODO Auto-generated method stub
		return sprintRepository.save(sprint);
	}

}
