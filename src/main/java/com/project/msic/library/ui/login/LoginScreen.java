package com.project.msic.library.ui.login;

import java.util.Optional;

import com.project.msic.library.authentication.AccessControl;
import com.project.msic.library.authentication.AccessControlFactory;
import com.project.msic.library.message.Messages;
import com.project.msic.library.ui.registration.RegistrationForm;
import com.project.msic.library.ui.registration.RegistrationFormBinder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginScreen extends FlexLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 761396763613815486L;

	private AccessControl accessControl;
	private RegistrationForm registrationForm;
	private LoginForm loginForm;

	public LoginScreen() {
		this.accessControl = AccessControlFactory.getInstance().createAccessControl();
		buildUI();
	}

	private void buildUI() {
		setSizeFull();
		setClassName(Messages.LOGIN_SCREEN_CLASS_MESSAGE);

		// login form, centered in the available part of the screen
		buildLoginForm();

		// registration form, centered in the available part of the screen
		buildRegistrationForm();

		// layout to center login form when there is sufficient screen space
		FlexLayout centeringLayout = centerLayout();

		// information text about logging in
		Component loginInformation = buildLoginInformation();

		add(loginInformation);
		add(centeringLayout);
	}

	private FlexLayout centerLayout() {
		FlexLayout centeringLayout = new FlexLayout();
		centeringLayout.setSizeFull();
		centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		centeringLayout.setAlignItems(Alignment.CENTER);
		centeringLayout.add(loginForm);
		centeringLayout.add(registrationForm);
		return centeringLayout;
	}

	private void buildRegistrationForm() {
		registrationForm = new RegistrationForm();
		registrationForm.getBackToLogin().addClickListener(event -> {
			//The logic for the back to login button from the registration form
			registrationForm.setVisible(false);
			loginForm.setVisible(true);
		});
		add(registrationForm);
		RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm);
		registrationFormBinder.addBindingAndValidation();
		registrationForm.setVisible(false);
	}

	private void buildLoginForm() {
		loginForm = new LoginForm();
		loginForm.addLoginListener(this::login);
		loginForm.addForgotPasswordListener(event -> Notification.show(Messages.LOGIN_HINT_MESSAGE));
	}

	private Component buildLoginInformation() {
		VerticalLayout loginInformation = new VerticalLayout();
		loginInformation.setClassName(Messages.LOGIN_INFORMATION_CLASS_MESSAGE);

		H1 loginInfoHeader = new H1(Messages.LOGIN_INFORMATION_HEADER_TEXT);
		loginInfoHeader.setWidth("100%");
		Span loginInfoText = new Span(Messages.LOGIN_INFO_TEXT);
		loginInfoText.setWidth("100%");

		Button createAccount = new Button("Create Account");
		createAccount.addClickListener(event -> {
			//The logic for the create account button from the Login information component
			registrationForm.setVisible(true);
			loginForm.setVisible(false);
		});
		
		createAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		loginInformation.add(loginInfoHeader);
		loginInformation.add(loginInfoText);
		loginInformation.add(createAccount);

		return loginInformation;
	}

	/**
	 * @param event representing the login event which will be triggered after pressing the
	 * Login button
	 */
	private void login(LoginEvent event) {
		if (accessControl.signIn(event.getUsername(), event.getPassword())) {
			Optional<UI> theUi = getUI();
			if (theUi.isPresent()) {
				theUi.get().navigate("");
			}
		} else {
			event.getSource().setError(true);
		}
	}
}
