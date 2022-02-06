package com.project.msic.library.authentication;

import org.springframework.stereotype.Component;

import com.project.msic.library.model.User;
import com.project.msic.library.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Default mock implementation of {@link AccessControl}.
 */
@Component
public class AcessControlImpl implements AccessControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8799282120804999512L;

	private ServiceRef<UserService> userService;

	public AcessControlImpl(ServiceRef<UserService> userService2) {
		this.userService = userService2;
	}

	@Override
	public boolean signIn(String username, String password) {
		if (username == null || username.isEmpty()) {
			return false;
		}

		if (!username.equals(password)) {
			return false;
		}

		User user = userService.get().findByUserName(username);
		if (user == null) {
			return false;
		}

		if (!user.getPassword().equals(password)) {
			return false;
		}

		CurrentUser.set(username);
		return true;
	}

	@Override
	public boolean isUserSignedIn() {
		return !CurrentUser.get().isEmpty();
	}

	@Override
	public boolean isUserInRole(String role) {
		User user = userService.get().findByUserName(getPrincipalName());
		if (user != null) {
			return user.getUserRole().equals(role);
		}

		return true;
	}

	@Override
	public String getPrincipalName() {
		return CurrentUser.get();
	}

	@Override
	public void signOut() {
		VaadinSession.getCurrent().getSession().invalidate();
		UI.getCurrent().navigate("");
	}

	@Override
	public void createUser(User userBean) {
		userService.get().saveUpdate(userBean);
		signIn(userBean.getUserName(), userBean.getPassword());
	}

	@Override
	public UserService getUserService() {
		return userService.get();
	}

}
