package com.project.msic.library.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.msic.library.message.Messages;
import com.project.msic.library.service.AuthorService;
import com.project.msic.library.service.CategoryService;
import com.project.msic.library.service.PublisherService;
import com.project.msic.library.ui.author.AuthorView;
import com.project.msic.library.ui.category.CategoryView;
import com.project.msic.library.ui.publisher.PublisherView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Admin view that is registered dynamically on admin user login.
 * <p>
 * Allows CRUD operations for the book categories.
 */
@CssImport("./styles/vertical-menu.css")
public class AdminView extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 394991144779409364L;

	public static final String VIEW_NAME = "Admin";
	private CategoryView categoryView;
	private PublisherView publisherView;
	private AuthorView authorView;

	private VerticalLayout verticalMenu;

	@Autowired
	private ServiceRef<AuthorService> authorService;
	@Autowired
	private ServiceRef<CategoryService> categoryService;
	@Autowired
	private ServiceRef<PublisherService> publisherService;

	public AdminView() {
	}

	@PostConstruct
	public void init() {
		initComponents();
		setWidth("100%");
	}

	private void initComponents() {
		initViews();
		initButtons();
		add(categoryView, authorView, publisherView);
	}

	private void initViews() {
		categoryView = new CategoryView(categoryService);
		authorView = new AuthorView(authorService);
		publisherView = new PublisherView(publisherService);
	}

	private void initButtons() {
		verticalMenu = new VerticalLayout();

		Button categoriesButton = new Button(Messages.ADMIN_VIEW_CATEGORIES_MENU_LABEL);
		categoriesButton.addClickListener(event -> {
			authorView.setVisible(false);
			publisherView.setVisible(false);
			categoryView.setVisible(true);
		});
		verticalMenu.add(categoriesButton);

		Button authorsButton = new Button(Messages.ADMIN_VIEW_AUTHORS_MENU_LABEL);
		authorsButton.addClickListener(event -> {
			authorView.setVisible(true);
			publisherView.setVisible(false);
			categoryView.setVisible(false);
		});
		verticalMenu.add(authorsButton);

		Button publishersButton = new Button(Messages.ADMIN_VIEW_PUBLISHERS_MENU_LABEL);
		publishersButton.addClickListener(event -> {
			authorView.setVisible(false);
			publisherView.setVisible(true);
			categoryView.setVisible(false);
		});
		verticalMenu.add(publishersButton);

		verticalMenu.setSizeFull();

		add(verticalMenu);
	}
}
