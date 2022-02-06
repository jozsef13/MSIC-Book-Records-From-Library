package com.project.msic.library.ui.dialog;

import com.project.msic.library.message.Messages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * @author Simon Jozsef-Gabriel
 * Error dialog which appears when an error is triggered 
 */
public class ErrorDialog extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5572450293934228933L;

	public ErrorDialog(String caption, String text) {
		final VerticalLayout content = new VerticalLayout();
		content.setPadding(false);
		add(content);

		add(new H3(caption));
		add(new Span(text));

		final HorizontalLayout buttons = new HorizontalLayout();
		buttons.setPadding(false);
		add(buttons);

		final Button cancel = new Button(Messages.CANCEL_BUTTON_MESSAGE, e -> close());
		buttons.add(cancel);
	}
}
