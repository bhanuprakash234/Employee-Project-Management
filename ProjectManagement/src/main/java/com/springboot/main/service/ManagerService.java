package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Manager;
import com.springboot.main.repository.EmployeeRepository;
import com.springboot.main.repository.ManagerRepository;

@Service
public class ManagerService {
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public Manager insert(Manager manager) {
		
		return managerRepository.save(manager);
	}

	public Manager getById(int mid)throws InvalidIdException {
		Optional<Manager> optional = managerRepository.findById(mid);
		if(!optional.isPresent())
			throw new InvalidIdException("Mid is invalid");
		return optional.get();
	}

	public List<Manager> getAllManager(Pageable pageable) {
		
		return managerRepository.findAll(pageable).getContent();
	}
	public Manager getManagerById(int id) throws InvalidIdException{
		Optional<Manager> optional = managerRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("Manager Id Invalid");
		Manager manager = optional.get();
		return manager;
	}

	
	
	public void deleteManager(int id) {
		managerRepository.deleteById(id);
	}

	public Manager insertManager(Manager manager) {
		// TODO Auto-generated method stub
		return managerRepository.save(manager);
		
	}

	
}
