package com.project.msic.library.ui.publisher;

import java.util.ArrayList;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Publisher;
import com.project.msic.library.service.PublisherService;
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

public class PublisherView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6519051398584097567L;

	private final VirtualList<Publisher> publisherListing;
	private final ListDataProvider<Publisher> publisherDataProvider;
	private final Button newPublisherButton;
	private ServiceRef<PublisherService> publisherService;

	public PublisherView(ServiceRef<PublisherService> publisherService2) {
		this.setVisible(false);
		this.publisherService = publisherService2;
		
		publisherListing = new VirtualList<>();
		
		publisherDataProvider = new ListDataProvider<>(new ArrayList<>(publisherService.get().getAll()));
		publisherListing.setDataProvider(publisherDataProvider);
		publisherListing.setRenderer(new ComponentRenderer<>(this::createPublisherEditor));

		newPublisherButton = new Button(Messages.ADD_NEW_PUBLISHER_MESSAGE, event -> {
			final Publisher publisher = new Publisher();
			publisherDataProvider.getItems().add(publisher);
			publisherDataProvider.refreshAll();
		});

		newPublisherButton.setDisableOnClick(true);
		add(new H4(Messages.EDIT_PUBLISHERS_MESSAGE), newPublisherButton, publisherListing);
	}

	private Component createPublisherEditor(Publisher publisher) {
		final TextField nameField = new TextField();
		if (publisher.getPublisherId() < 0) {
			nameField.focus();
		}

		final Button deleteButton = new Button(VaadinIcon.MINUS_CIRCLE_O.create(), event -> {

			// Ask for confirmation before deleting stuff
			final ConfirmDialog dialog = SimpleDialogUtil.confirmDialog(Messages.CONFIRM_DIALOG_TITLE,
					Messages.PUBLISHER_DELETE_WARN_MESSAGE,
					Messages.DELETE_MESSAGE, () -> {
						publisherService.get().remove(publisher);
						publisherDataProvider.getItems().remove(publisher);
						publisherDataProvider.refreshAll();
						Notification.show(Messages.PUBLISHER_DELETED_MESSAGE);
					});

			dialog.open();

		});
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

		final BeanValidationBinder<Publisher> binder = new BeanValidationBinder<>(Publisher.class);
		binder.forField(nameField).bind("name");
		binder.setBean(publisher);
		binder.addValueChangeListener(event -> {
			if (binder.isValid()) {
				publisherService.get().saveUpdate(publisher);
				deleteButton.setEnabled(true);
				newPublisherButton.setEnabled(true);
				Notification.show(Messages.PUBLISHER_SAVED_MESSAGE);
			}
		});
		deleteButton.setEnabled(publisher.getPublisherId() > 0);

		final HorizontalLayout layout = new HorizontalLayout(nameField, deleteButton);
		layout.setFlexGrow(1);
		return layout;
	}
}
