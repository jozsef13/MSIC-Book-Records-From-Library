package com.project.msic.library.ui.author;

import java.util.ArrayList;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Author;
import com.project.msic.library.service.AuthorService;
import com.project.msic.library.ui.dialog.ConfirmDialog;
import com.project.msic.library.ui.dialog.SimpleDialogUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import de.codecamp.vaadin.serviceref.ServiceRef;

public class AuthorView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8269710859618127684L;

	private final VirtualList<Author> authorListing;
	private final ListDataProvider<Author> authorDataProvider;
	private final Button newAuthorButton;
	private ServiceRef<AuthorService> authorService;

	public AuthorView(ServiceRef<AuthorService> authorService2) {
		this.setVisible(false);
		this.authorService = authorService2;
		authorListing = new VirtualList<>();

		authorDataProvider = new ListDataProvider<>(new ArrayList<>(authorService.get().getAll()));
		authorListing.setDataProvider(authorDataProvider);
		authorListing.setRenderer(new ComponentRenderer<>(this::createAuthorEditor));

		newAuthorButton = new Button(Messages.ADD_NEW_AUTHOR_TEXT, event -> {
			final Author author = new Author();
			authorDataProvider.getItems().add(author);
			authorDataProvider.refreshAll();
		});
		newAuthorButton.setDisableOnClick(true);

		add(new H4(Messages.EDIT_AUTHORS_TITLE), newAuthorButton, authorListing);
	}

	private Component createAuthorEditor(Author author) {
		final TextField nameField = new TextField();
		if (author.getAuthorId() < 0) {
			nameField.focus();
		}

		final Button deleteButton = new Button(VaadinIcon.MINUS_CIRCLE_O.create(), event -> {

			// Ask for confirmation before deleting stuff
			final ConfirmDialog dialog = SimpleDialogUtil.confirmDialog(Messages.CONFIRM_DIALOG_TITLE,
					Messages.DELETE_AUTHOR_WARN_TEXT, Messages.DELETE_MESSAGE, () -> {
						authorService.get().remove(author);
						authorDataProvider.getItems().remove(author);
						authorDataProvider.refreshAll();
						Notification.show(Messages.AUTHOR_DELETED_TEXT);
					});

			dialog.open();

		});
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

		final BeanValidationBinder<Author> binder = new BeanValidationBinder<>(Author.class);
		binder.forField(nameField).bind("name");
		binder.setBean(author);
		binder.addValueChangeListener(event -> {
			if (binder.isValid()) {
				authorService.get().saveUpdate(author);
				deleteButton.setEnabled(true);
				newAuthorButton.setEnabled(true);
				Notification.show(Messages.AUTHOR_SAVED_TEXT);
			}
		});
		deleteButton.setEnabled(author.getAuthorId() > 0);

		final HorizontalLayout layout = new HorizontalLayout(nameField, deleteButton);
		layout.setFlexGrow(1);
		return layout;
	}
}
