package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Integer>{

	List<Sprint> getByBacklogId(int bid);

	

}
