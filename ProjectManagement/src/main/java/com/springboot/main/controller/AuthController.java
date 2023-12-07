package com.springboot.main.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.model.User;
import com.springboot.main.service.UserService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {

	
}
