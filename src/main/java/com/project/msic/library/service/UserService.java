package com.project.msic.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.User;
import com.project.msic.library.repository.UserRepository;

/**
 * @author Simon Jozsef-Gabriel Class representing the User service, the linkage
 *         between the data tier and the UI, where the business logic is
 *         implemented.
 */
@Service
public class UserService {

	/**
	 * The repository instance for the lbr_user table implementing the CRUD operations and
	 * other data tier method
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * @param username representing the username of the returning User
	 * @return a user object based on it's username from the database
	 */
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	/**
	 * @param userBean representing the User object that will be saved/updated
	 */
	public void saveUpdate(User userBean) {
		userRepository.saveAndFlush(userBean);
	}

	/**
	 * @return a list containing all the User objects from the database
	 */
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
