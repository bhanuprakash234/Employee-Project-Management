package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{


	

	List<Notification> getByEmployeeId(int eid);

}
