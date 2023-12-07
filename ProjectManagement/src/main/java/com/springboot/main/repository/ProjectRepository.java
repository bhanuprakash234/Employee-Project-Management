package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	
	

	List<Project> getByManagerId(int mid);

	
	@Query("select p from Project p JOIN EmployeeProject ep ON p.id=ep.project.id JOIN Employee e ON ep.employee.id=e.id WHERE e.id=?1")
	List<Project> getByEmployeeId(int eid);


	@Query("select p from Project p where p.title LIKE %?1%")
	List<Project> searchByProjectName(String qStr);

}
