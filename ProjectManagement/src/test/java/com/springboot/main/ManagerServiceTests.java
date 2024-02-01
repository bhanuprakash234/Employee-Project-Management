package com.springboot.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.main.exception.InvalidIdException;

import com.springboot.main.model.Manager;

import com.springboot.main.repository.ManagerRepository;

import com.springboot.main.service.ManagerService;

@SpringBootTest
public class ManagerServiceTests {
	

	@Mock
	private ManagerRepository managerRepository;
	
	@InjectMocks
	private ManagerService managerService;
	
	@Test
	public void testId() throws InvalidIdException{
		int mid = 44;
		Manager manager = new Manager();
		when(managerRepository.findById(mid)).thenReturn(Optional.of(manager));
		
		Manager result = managerService.getById(mid);
		
		assertEquals(manager, result);
	}

}
