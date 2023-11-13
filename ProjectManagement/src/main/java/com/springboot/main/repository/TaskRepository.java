package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByEmployeeId(int eid);

	

	List<Task> findByBacklogId(int bid);

	

}
