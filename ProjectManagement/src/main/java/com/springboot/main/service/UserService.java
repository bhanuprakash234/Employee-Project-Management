package com.springboot.main.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Employee;
import com.springboot.main.model.User;
import com.springboot.main.repository.EmployeeRepository;
import com.springboot.main.repository.UserRepository;



@Service
public class UserService implements UserDetailsService {
	
	
	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
    
	@Autowired
	private JavaMailSender mailSender;

    

	

	public User insert(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loan username......called....");
		User user = userRepository.findByUsername(username);
		System.out.println(user);
		return user;
	}

	public List<User> getAllUser(Pageable pageable) {
		
		return userRepository.findAll(pageable).getContent();
	}
	
	public User getUserById(int id) throws InvalidIdException{
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent())
			throw new InvalidIdException("User Id Invalid");
		User user = optional.get();
		return user;
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	public User insertUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	public User getUserByUserName(String username) {
		 
		return userRepository.findByUsername(username);
	}

	  public void updateEncryptedPassword(String username, String newPassword) {
	        User user = userRepository.findByUsername(username);

	        // Encode the new password
	        

	        // Update the password
	        user.setPassword(newPassword);

	        // Save the updated user
	        userRepository.save(user);
	    }

	  public void sendEmailOnRegistration(int userId) throws InvalidIdException {
		    Optional<Employee> optional = employeeRepository.findById(userId);
		    
		    if (!optional.isPresent()) {
		        throw new InvalidIdException("id not found");
		    }
		    
		    Employee employee = optional.get();
		    // Assuming userRepository.findById method requires a parameter, replace it accordingly
		    // User user = userRepository.findById(customer.getId()).orElse(new User());
		    
		    String subject = "Registration confirmation";
		    String text = "Dear " + employee.getName() + ",\n\n" +
		            "Welcome to Corporate World – where every journey begins with excitement and comfort!  We are thrilled to have you on board as a valued member of our community.\n\n" +
		            "Feel free to explore our user-friendly website,named xxyyy.com discover our organization verticals and horizontals and tools , If you ever need assistance or have questions, our dedicated support team is here to help.\n\n" +
		            "Thank you for choosing our organization for your career. Get ready to explore the corporate world!\n\n" +
		            "Warm regards";
		           

		    // Assuming mailSender is an instance of JavaMailSender
		    SimpleMailMessage message = new SimpleMailMessage();
		    message.setTo(employee.getEmail());
		    message.setSubject(subject);
		    message.setText(text);
		    mailSender.send(message);
		}
	  
		
	
	

}
