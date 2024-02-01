package com.springboot.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.repository.EmployeeRepository;
import com.springboot.main.service.EmployeeService;

@SpringBootTest
public class EmployeeServiceTests {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeService employeeService;
	
	@Test
	public void testId() throws InvalidIdException{
		int id = 44;
		Employee employee = new Employee();
		when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
		
		Employee result = employeeService.getEmployeeById(id);
		
		assertEquals(employee, result);
	}

}
