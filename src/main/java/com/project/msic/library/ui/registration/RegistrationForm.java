package com.project.msic.library.ui.registration;

import java.util.stream.Stream;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class RegistrationForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5268567155027543921L;

	private H3 title;

	private TextField firstName;
	private TextField lastName;
	private TextField userName;
	private EmailField email;
	private TextField address;
	private DatePicker birthDate;
	private TextField phoneNumber;

	private Span errorMessageField;

	private Button submitButton;
	private Button backToLogin;

	public RegistrationForm() {
		title = new H3("Create Account");
		firstName = new TextField("First Name");
		lastName = new TextField("Last Name");
		userName = new TextField("Username");
		email = new EmailField("Email");
		address = new TextField("Address");
		birthDate = new DatePicker("BirthDay");
		phoneNumber = new TextField("Phone Number");

		setRequiredIndicatorVisible(firstName, lastName, userName, email, address, birthDate, phoneNumber);

		errorMessageField = new Span();

		submitButton = new Button("Sign Up");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		backToLogin = new Button("Back To Login");
		backToLogin.addThemeVariants(ButtonVariant.LUMO_ERROR);

		add(title, firstName, lastName, email, userName, address, birthDate, errorMessageField, phoneNumber,
				submitButton, backToLogin);

		// Max width of the Form
		setMaxWidth("500px");
		setSizeUndefined();

		// Allow the form layout to be responsive.
		// On device widths 0-490px we have one column.
		// Otherwise, we have two columns.
		setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
				new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

		// These components always take full width
		setColspan(title, 2);
		setColspan(email, 2);
		setColspan(errorMessageField, 2);
		setColspan(phoneNumber, 2);
	}

	private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}

	public Span getErrorMessageField() {
		return errorMessageField;
	}

	public Button getSubmitButton() {
		return submitButton;
	}

	public TextField getPhoneNumber() {
		return phoneNumber;
	}

	public Button getBackToLogin() {
		return backToLogin;
	}

	public DatePicker getBirthDate() {
		return birthDate;
	}

	public TextField getUserName() {
		return userName;
	}

	public EmailField getEmail() {
		return email;
	}

}
