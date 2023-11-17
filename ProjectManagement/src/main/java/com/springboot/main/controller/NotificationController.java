package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.Manager;
import com.springboot.main.model.Notification;
import com.springboot.main.service.EmployeeService;
import com.springboot.main.service.ManagerService;
import com.springboot.main.service.NotificationService;

@RestController
public class NotificationController {
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/sendNotification/{mid}/{eid}")
	public ResponseEntity<?> SendNotification(@PathVariable("mid") int mid,
			                     @PathVariable("eid") int eid,
			                     @RequestBody Notification notification){
	try {
		
		Manager manager = managerService.getById(mid);
		
		Employee employee = employeeService.getById(eid);
		
		notification.setManager(manager);
		
		notification.setEmployee(employee);
		
		notification.setDate(LocalDate.now());
		
		notification = notificationService.sendNotification(notification);
		
		return ResponseEntity.ok().body(notification);
		

      }  catch(InvalidIdException e) {
    	  return ResponseEntity.badRequest().body(e.getMessage());
      }
}
	
	@GetMapping("/receiveNotification/{eid}")
	public ResponseEntity<?> ReceiveNotificationsByEmployeeId(@PathVariable("eid") int eid) {
		try {
			Employee employee = employeeService.getById(eid);
			List<Notification> list = notificationService.ReceiveNotificationsByEmployeeId(eid);
			return ResponseEntity.ok().body(list);
		} catch(InvalidIdException e) {
	    	  return ResponseEntity.badRequest().body(e.getMessage());
	      }
	}
}