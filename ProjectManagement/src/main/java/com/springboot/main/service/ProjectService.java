package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Project;
import com.springboot.main.repository.ProjectRepository;

@Service
public class ProjectService {

	
	@Autowired
	private ProjectRepository projectRepository;
	public Project insert(Project project) {
		
		return projectRepository.save(project);
	}
	public Project getById(int pid) throws InvalidIdException {
		Optional<Project>optional = projectRepository.findById(pid);
		if(!optional.isPresent())
			throw new InvalidIdException("Pid Invalid");
		return optional.get();
	}
	public List<Project> getAllProject(Pageable pageable) {
		
		return projectRepository.findAll(pageable).getContent();
	}
	
	public Project getProjectById(int id) throws InvalidIdException{
		Optional<Project> optional = projectRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("Project Id Is Invalid");
		Project project = optional.get();
		return project;
	}
	public void deleteProject(int id) {
		projectRepository.deleteById(id);
	}

}
