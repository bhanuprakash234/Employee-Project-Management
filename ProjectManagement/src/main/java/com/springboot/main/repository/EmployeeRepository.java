package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.main.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByManagerId(int mid);

	List<Employee> findByName(String name);

	

	

	
	
	

}
