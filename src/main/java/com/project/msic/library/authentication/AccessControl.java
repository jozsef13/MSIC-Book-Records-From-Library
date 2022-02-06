package com.project.msic.library.authentication;

import java.io.Serializable;

import com.project.msic.library.model.User;
import com.project.msic.library.service.UserService;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface AccessControl extends Serializable {

	String ADMIN_ROLE_NAME = "admin";
	String ADMIN_USERNAME = "admin";

	/**
	 * @param username
	 * @param password
	 * @return a boolean which will state if the user has successfully signed in
	 *         (authenticated) using the credentials
	 */
	boolean signIn(String username, String password);

	/**
	 * @return a boolean which will state if the current user is authenticated or
	 *         not
	 */
	boolean isUserSignedIn();

	/**
	 * @param role
	 * @return a boolean which will state if the user has a specified role
	 */
	boolean isUserInRole(String role);

	/**
	 * @return the current authenticated user's name
	 */
	String getPrincipalName();

	/**
	 * Method used to restart the session for the current user and sign him out
	 */
	void signOut();

	/**
	 * Method that will create a user based on the userBean parameter
	 * @param userBean
	 */
	void createUser(User userBean);
	
	/**
	 * @return the userService object
	 */
	UserService getUserService();
}
