package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.WorkLog;

public interface WorklogRepository extends JpaRepository<WorkLog, Integer> {

	List<WorkLog> findByTaskId(int tid);

}
