package com.project.msic.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.User;
import com.project.msic.library.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

}
