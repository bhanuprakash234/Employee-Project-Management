package com.springboot.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.model.Notification;
import com.springboot.main.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	public Notification sendNotification(Notification notification) {
		
		return notificationRepository.save(notification);
	}

	public List<Notification> ReceiveNotificationsByEmployeeId(int eid) {
		
		return notificationRepository.findByEmployeeId(eid);
	}

}
