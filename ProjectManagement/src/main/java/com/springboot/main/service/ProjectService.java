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
	
	
	public void deleteProject(int pid) {
		projectRepository.deleteById(pid);
	}
	public Project insertEmployee(Project project) {
		return projectRepository.save(project);
		
	}
	
	
	public List<Project> getByManagerId(int mid) {
		// TODO Auto-generated method stub
		return projectRepository.getByManagerId(mid);
	}
	public List<Project> getByEmployeeId(int eid) {
		// TODO Auto-generated method stub
		return projectRepository.getByEmployeeId(eid);
	}
	public List<Project> searchByProjectName(String qStr) {
		
		return projectRepository.searchByProjectName(qStr);
	}

}
