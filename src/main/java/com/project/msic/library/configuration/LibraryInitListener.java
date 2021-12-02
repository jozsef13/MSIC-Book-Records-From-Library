package com.project.msic.library.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.msic.library.authentication.AccessControl;
import com.project.msic.library.authentication.AccessControlFactory;
import com.project.msic.library.service.UserService;
import com.project.msic.library.ui.login.LoginScreen;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * This class is used to listen to BeforeEnter event of all UIs in order to
 * check whether a user is signed in or not before allowing entering any page.
 * It is registered in a file named
 * com.vaadin.flow.server.VaadinServiceInitListener in META-INF/services.
 */
@Component
public class LibraryInitListener implements VaadinServiceInitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4010422433242452549L;
	@Autowired
	private ServiceRef<UserService> userService;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		final AccessControl accessControl = AccessControlFactory.getInstance().createAccessControl(userService);

		event.getSource().addUIInitListener(uiInitEvent -> {
			uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
				if (!accessControl.isUserSignedIn() && !LoginScreen.class.equals(enterEvent.getNavigationTarget())) {
					enterEvent.rerouteTo(LoginScreen.class);
				}
			});
		});
	}

}
