package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.EmployeeProject;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Integer> {

	
	@Query("select ep from EmployeeProject ep JOIN Employee e ON ep.employee.id=e.id WHERE e.manager.id=?1")
	List<EmployeeProject> getEmployeeProjectByManagerId(int mid);

}
