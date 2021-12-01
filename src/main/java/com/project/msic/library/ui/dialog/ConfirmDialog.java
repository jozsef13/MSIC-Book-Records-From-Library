package com.project.msic.library.ui.dialog;

import com.project.msic.library.message.Messages;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmDialog extends Dialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -202287567921638814L;

	public ConfirmDialog(String caption, String text, String confirmButtonText, Runnable confirmListener) {

		final VerticalLayout content = new VerticalLayout();
		content.setPadding(false);
		add(content);

		add(new H3(caption));
		add(new Span(text));

		final HorizontalLayout buttons = new HorizontalLayout();
		buttons.setPadding(false);
		add(buttons);

		final Button confirm = new Button(confirmButtonText, e -> {
			confirmListener.run();
			close();
		});
		confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttons.add(confirm);

		final Button cancel = new Button(Messages.CANCEL_BUTTON_MESSAGE, e -> close());
		buttons.add(cancel);

	}
}
