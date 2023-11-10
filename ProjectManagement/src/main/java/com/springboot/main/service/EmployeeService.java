package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.repository.EmployeeRepository;
import com.springboot.main.repository.ManagerRepository;
import com.springboot.main.repository.TaskRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ManagerRepository managerRepository;

	public Employee insert(Employee employee) {
		
		return employeeRepository.save(employee);
	}

	public Employee getById(int eid) throws InvalidIdException {
		Optional<Employee> optional = employeeRepository.findById(eid);
		if(!optional.isPresent())
			throw new InvalidIdException("Eid invalid");
		return optional.get();
	}


	public List<Employee> getEmployeesByManager(int mid) {
		
		return employeeRepository.findByManagerId(mid);
	}

	public List<Employee> getAllEmployee() {
		
		return employeeRepository.findAll();
	}



}
