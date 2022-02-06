package com.project.msic.library.ui.registration;

import java.util.List;

import com.project.msic.library.authentication.AccessControl;
import com.project.msic.library.authentication.AccessControlFactory;
import com.project.msic.library.model.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class RegistrationFormBinder {

	private static final String NUMBER_REGEX = "[0-9]+";
	private RegistrationForm registrationForm;
	private AccessControl accessControl;

	public RegistrationFormBinder(RegistrationForm registrationForm) {
		this.registrationForm = registrationForm;
		this.accessControl = AccessControlFactory.getInstance().createAccessControl();
	}

	/**
	 * Method to add the data binding and validation logics to the registration form
	 */
	public void addBindingAndValidation() {
		BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
		binder.bindInstanceFields(registrationForm);

		// A custom validator for phone number fields
		binder.forField(registrationForm.getPhoneNumber()).withValidator(this::phoneNumberValidator)
				.bind("phoneNumber");
		binder.forField(registrationForm.getUserName()).withValidator(this::userNameValidator).bind("userName");
		binder.forField(registrationForm.getEmail()).withValidator(this::emailValidator).bind("email");

		// Set the label where bean-level error messages go
		binder.setStatusLabel(registrationForm.getErrorMessageField());

		// And finally the submit button
		registrationForm.getSubmitButton().addClickListener(event -> {
			try {
				// Create empty bean to store the details into
				User userBean = new User();

				// Run validators and write the values to the bean
				binder.writeBean(userBean);
				userBean.setPassword(userBean.getUserName());
				userBean.setUserRole("user");
				// Typically, you would here call backend to store the bean
				accessControl.createUser(userBean);

				// Show success message if everything went well
				showSuccess(userBean);
			} catch (ValidationException exception) {
				// validation errors are already visible for each field,
				// and bean-level errors are shown in the status label.
				// We could show additional messages here if we want, do logging, etc.
			}
		});
	}

	/**
	 * Method to validate that:
	 * <p>
	 * 1) Phone number is no more than 10 characters long
	 * <p>
	 * 2) Phone number value is only consisting of numbers
	 */
	private ValidationResult phoneNumberValidator(String phone, ValueContext ctx) {
		if (phone == null || phone.length() > 10) {
			return ValidationResult.error("The phone number should not exceed 10 characters");
		}

		if (phone.matches(NUMBER_REGEX)) {
			return ValidationResult.ok();
		}

		return ValidationResult.error("Phone number is not valid!");
	}

	/**
	 * We call this method when form submission has succeeded
	 */
	private void showSuccess(User userBean) {
		Notification notification = Notification.show("Data saved, welcome " + userBean.getFirstName());
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		// Here you'd typically redirect the user to another view
		UI.getCurrent().navigate("Inventory");
	}

	/**
	 * Method to validate that:
	 * <p>
	 * 1) Username does not contain only numbers
	 * <p>
	 * 2) There is no other user with the same username
	 */
	private ValidationResult userNameValidator(String userName, ValueContext ctx) {
		if (userName.matches(NUMBER_REGEX)) {
			return ValidationResult.error("The username should also contain characters");
		}

		List<User> users = accessControl.getUserService().getAll();
		if (users.stream().anyMatch(user -> user.getUserName().equals(userName))) {
			return ValidationResult.error("There is already an existing account with this username! Choose another.");
		}

		return ValidationResult.ok();
	}

	/**
	 * Method to validate that:
	 * <p>
	 * 1) Email does not contain only numbers
	 * <p>
	 * 2) There is no other user with the same email
	 * <p>
	 * 3) The email is a valid email address based on a Regular Expression
	 */
	private ValidationResult emailValidator(String email, ValueContext ctx) {
		if (email.matches(NUMBER_REGEX)) {
			return ValidationResult.error("The email should also contain characters");
		}

		List<User> users = accessControl.getUserService().getAll();
		if (users.stream().anyMatch(user -> user.getEmail().equals(email))) {
			return ValidationResult.error("There is already an existing account with this email address!");
		}

		return ValidationResult.ok();
	}

}
