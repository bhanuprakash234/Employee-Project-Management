package com.springboot.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Integer>{

}
