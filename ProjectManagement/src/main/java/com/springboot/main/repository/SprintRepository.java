package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Integer>{

	List<Sprint> getByBacklogId(int bid);

	@Query("SELECT s from Sprint s JOIN s.backlog b JOIN b.project p WHERE p.id = ?1 ")
	List<Sprint> getSprintsByProjectId(int pid);

	

}
