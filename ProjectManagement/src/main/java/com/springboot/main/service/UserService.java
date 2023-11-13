package com.springboot.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.main.model.User;
import com.springboot.main.repository.UserRepository;



@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

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
	
	
	
	

}
