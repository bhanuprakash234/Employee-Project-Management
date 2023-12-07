package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.EmployeeProject;
import com.springboot.main.repository.EmployeeProjectRepository;

@Service
public class EmployeeProjectService {
	
	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;

	public EmployeeProject insert(EmployeeProject employeeProject) {
		
		return employeeProjectRepository.save(employeeProject);
	}

	public List<EmployeeProject> getAllEmployeeProject(Pageable pageable) {
		
		return employeeProjectRepository.findAll(pageable).getContent();
	}
	
	public EmployeeProject getEmployeeProjectById(int id) throws InvalidIdException{
		Optional<EmployeeProject> optional = employeeProjectRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("Employee Project Id Invalid");
		EmployeeProject employeeproject = optional.get();
		return employeeproject;
	}
	public void deleteEmployeeProject(int id) {
		employeeProjectRepository.deleteById(id);
	}

	public EmployeeProject insertEmployee(EmployeeProject employeeproject) {
		// TODO Auto-generated method stub
		return employeeProjectRepository.save(employeeproject);
	}

	public List<EmployeeProject> getEmployeeProjectByManagerId(int mid) {
		
		return employeeProjectRepository.getEmployeeProjectByManagerId(mid);
	}


}
