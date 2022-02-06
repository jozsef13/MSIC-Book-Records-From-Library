package com.project.msic.library.authentication;

import com.project.msic.library.service.UserService;

import de.codecamp.vaadin.serviceref.ServiceRef;

public class AccessControlFactory {
	// Instance of the AccessControlFactory, applying Singleton and Factory design
	// patterns
	private static final AccessControlFactory INSTANCE = new AccessControlFactory();
	//The access control object which will be returned by the factory
	private AccessControl accessControl;

	private AccessControlFactory() {
	}

	public static AccessControlFactory getInstance() {
		return INSTANCE;
	}

	public AccessControl createAccessControl(ServiceRef<UserService> userService) {
		if (accessControl == null) {
			accessControl = new AcessControlImpl(userService);
		}
		return accessControl;
	}

	public AccessControl createAccessControl() {
		return accessControl;
	}
}
