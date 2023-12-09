package com.springboot.main.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.main.SecurityConfig;
import com.springboot.main.dto.ProjectDto;
import com.springboot.main.dto.UserDto;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.Project;
import com.springboot.main.model.User;
import com.springboot.main.repository.UserRepository;



@Service
public class UserService implements UserDetailsService {
	
	
	@Autowired
    private  UserRepository userRepository;
    

    

	

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
	  
		public void updateProject(UserDto dto) throws InvalidIdException {
			// TODO Auto-generated method stub
			Optional<User> optional =  userRepository.findById(dto.getId());
			if(!optional.isPresent())
				throw new InvalidIdException("User ID Invalid");
			User user =  optional.get();
			user.setEmail(dto.getEmail());
			user.setUsername(dto.getUsername());

			userRepository.save(user);
			
		}
	
	

}
