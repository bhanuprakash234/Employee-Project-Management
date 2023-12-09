package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.main.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Page<Employee> findByManagerId(int mid, Pageable pageable);

	List<Employee> findByName(String name);

	List<Employee> getEmployeeListByManagerId(int mid);

	

	

	
	
	

}
