package com.project.msic.library.ui.category;

import java.util.ArrayList;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Category;
import com.project.msic.library.service.CategoryService;
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

/**
 * @author Simon Jozsef-Gabriel
 * The class representing the Category view, which
 * is a component appearing in the Admin page
 * It is responsible with the display of the CRUD
 * method over the Category objects
 */
public class CategoryView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2649468604079188520L;

	/**
	 * The list which is displayed in the application under the Admin page
	 */
	private final VirtualList<Category> categoriesListing;
	/**
	 * The data provider, which retrieves and displays the list of Categories
	 */
	private final ListDataProvider<Category> categoryDataProvider;
	private final Button newCategoryButton;
	/**
	 * The service which holds the business implementation for the Category
	 */
	private ServiceRef<CategoryService> categoryService;

	public CategoryView(ServiceRef<CategoryService> categoryService2) {
		this.setVisible(false);
		this.categoryService = categoryService2;
		
		categoriesListing = new VirtualList<>();
	
		categoryDataProvider = new ListDataProvider<>(new ArrayList<>(categoryService.get().getAll()));
		categoriesListing.setDataProvider(categoryDataProvider);
		categoriesListing.setRenderer(new ComponentRenderer<>(this::createCategoryEditor));

		newCategoryButton = new Button(Messages.ADD_NEW_CATEGORY_MESSAGE, event -> {
			final Category category = new Category();
			categoryDataProvider.getItems().add(category);
			categoryDataProvider.refreshAll();
		});
		newCategoryButton.setDisableOnClick(true);

		add(new H4(Messages.EDIT_CATEGORIES_TEXT), newCategoryButton, categoriesListing);
	}

	private Component createCategoryEditor(Category category) {
		final TextField nameField = new TextField();
		if (category.getCategoryId() < 0) {
			nameField.focus();
		}

		final Button deleteButton = new Button(VaadinIcon.MINUS_CIRCLE_O.create(), event -> {

			// Ask for confirmation before deleting stuff
			final ConfirmDialog dialog = SimpleDialogUtil.confirmDialog(Messages.CONFIRM_DIALOG_TITLE,
					Messages.DELETE_CATEGORY_WARN_MESSAGE,
					Messages.DELETE_MESSAGE, () -> {
						categoryService.get().remove(category);
						categoryDataProvider.getItems().remove(category);
						categoryDataProvider.refreshAll();
						Notification.show(Messages.CATEGORY_DELETED_MESSAGE);
					});

			dialog.open();

		});
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

		final BeanValidationBinder<Category> binder = new BeanValidationBinder<>(Category.class);
		binder.forField(nameField).bind("name");
		binder.setBean(category);
		binder.addValueChangeListener(event -> {
			if (binder.isValid()) {
				categoryService.get().saveUpdate(category);
				deleteButton.setEnabled(true);
				newCategoryButton.setEnabled(true);
				Notification.show(Messages.CATEGORY_SAVED_MESSAGE);
			}
		});
		deleteButton.setEnabled(category.getCategoryId() > 0);

		final HorizontalLayout layout = new HorizontalLayout(nameField, deleteButton);
		layout.setFlexGrow(1);
		return layout;
	}
}
