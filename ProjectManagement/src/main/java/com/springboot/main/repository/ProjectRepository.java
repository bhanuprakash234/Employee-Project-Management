package com.springboot.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
