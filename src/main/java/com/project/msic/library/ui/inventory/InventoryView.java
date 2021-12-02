package com.project.msic.library.ui.inventory;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Author;
import com.project.msic.library.model.Book;
import com.project.msic.library.model.Category;
import com.project.msic.library.model.Publisher;
import com.project.msic.library.service.AuthorService;
import com.project.msic.library.service.BookService;
import com.project.msic.library.service.CategoryService;
import com.project.msic.library.service.PublisherService;
import com.project.msic.library.service.UserService;
import com.project.msic.library.ui.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * A view for performing create-read-update-delete operations on books.
 *
 * See also {@link InventoryViewPresenter} for fetching the data, the actual
 * CRUD operations and controlling the view based on events from outside.
 */
@Route(value = "Inventory", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class InventoryView extends HorizontalLayout implements HasUrlParameter<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6587779477206692773L;

	public static final String VIEW_NAME = "Inventory";
	private BookGrid grid;
	private BookForm form;
	private TextField filter;
	private Select<Category> categoryFilter;
	private Select<Author> authorFilter;
	private Select<Publisher> publisherFilter;

	private InventoryViewPresenter presenter;
	private Button newBookButton;

	private BookDataProvider dataProvider;
	@Autowired
	private ServiceRef<CategoryService> categoryService;
	@Autowired
	private ServiceRef<AuthorService> authorService;
	@Autowired
	private ServiceRef<PublisherService> publisherService;
	@Autowired
	private ServiceRef<BookService> bookService;
	@Autowired
	private ServiceRef<UserService> userService;

	public InventoryView() {
	}

	@PostConstruct
	public void init() {
		// Sets the width and the height of InventoryView to "100%".
		setSizeFull();
		dataProvider = new BookDataProvider(bookService);
		presenter = new InventoryViewPresenter(this, bookService, userService);

		form = new BookForm(presenter);
		initCategoryRelatedElements();
		initAuthorRelatedElements();
		initPublisherRelatedElements();

		final HorizontalLayout topLayout = createTopBar();
		grid = new BookGrid();
		grid.setItems(dataProvider);
		// Allows user to select a single row in the grid.
		grid.asSingleSelect().addValueChangeListener(event -> presenter.rowSelected(event.getValue()));
		final VerticalLayout barAndGridLayout = new VerticalLayout();
		barAndGridLayout.add(topLayout);
		barAndGridLayout.add(grid);
		barAndGridLayout.setFlexGrow(1, grid);
		barAndGridLayout.setFlexGrow(0, topLayout);
		barAndGridLayout.setSizeFull();
		barAndGridLayout.expand(grid);

		add(barAndGridLayout);
		add(form);

		presenter.init();
	}

	private void initPublisherRelatedElements() {
		Collection<Publisher> publishers = publisherService.get().getAll();
		form.setPublishers(publishers);
		initPublisherFilter();
	}

	private void initAuthorRelatedElements() {
		Collection<Author> authors = authorService.get().getAll();
		form.setAuthors(authors);
		initAuthorFilter();
	}

	private void initCategoryRelatedElements() {
		Collection<Category> categories = categoryService.get().getAll();
		form.setCategories(categories);
		initCategoryFilter();
	}

	public HorizontalLayout createTopBar() {
		filter = new TextField();
		filter.setPlaceholder(Messages.FILTER_PLACEHOLDER_TEXT);
		// Apply the filter to grid's data provider. TextField value is never
		filter.addValueChangeListener(event -> dataProvider.setTextFilter(event.getValue()));
		// A shortcut to focus on the textField by pressing ctrl + F
		filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

		newBookButton = new Button(Messages.NEW_BOOK_BUTTON_TEXT);
		// Setting theme variant of new book button to LUMO_PRIMARY that
		// changes its background color to blue and its text color to white
		newBookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		newBookButton.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		newBookButton.addClickListener(click -> presenter.newBook());
		// A shortcut to click the new book button by pressing ALT + N
		newBookButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
		final HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setWidth("100%");
		topLayout.add(filter);
		topLayout.add(categoryFilter);
		topLayout.add(authorFilter);
		topLayout.add(publisherFilter);
		topLayout.add(newBookButton);
		topLayout.setVerticalComponentAlignment(Alignment.START, filter);
//		topLayout.expand(filter);

		setCategoryFilterItems();
		setAuthorFilterItems();
		setPublisherFilterItems();

		return topLayout;
	}

	private void setPublisherFilterItems() {
		publisherFilter.setItems(publisherService.get().getAll());
		publisherFilter.setItemLabelGenerator(pub -> {
			if(pub == null || pub.getName() == null) {
				return "Select a publisher";
			} else {
				return pub.getName();
			}
		});
		publisherFilter.addValueChangeListener(event -> dataProvider.setPublisherFilter(event.getValue()));
	}

	private void setAuthorFilterItems() {
		authorFilter.setItems(authorService.get().getAll());
		authorFilter.setItemLabelGenerator(auth -> {
			if(auth == null || auth.getName() == null) {
				return "Select an author";
			} else {
				return auth.getName();
			}
		});
		authorFilter.addValueChangeListener(event -> dataProvider.setAuthorFilter(event.getValue()));
	}

	private void setCategoryFilterItems() {
		categoryFilter.setItems(categoryService.get().getAll());
		categoryFilter.setItemLabelGenerator(cat -> {
			if(cat == null || cat.getName() == null) {
				return "Select a category";
			} else {
				return cat.getName();
			}
		});
		categoryFilter.addValueChangeListener(event -> dataProvider.setCategoryFilter(event.getValue()));
	}

	private void initPublisherFilter() {
		publisherFilter = new Select<>();
		publisherFilter.setPlaceholder(Messages.PUBLISHER_TEXT);
		publisherFilter.setWidth("33%");
		publisherFilter.setEmptySelectionCaption("Select a publisher");
		publisherFilter.setEmptySelectionAllowed(true);
	}

	private void initAuthorFilter() {
		authorFilter = new Select<>();
		authorFilter.setPlaceholder(Messages.AUTHOR_TEXT);
		authorFilter.setWidth("33%");
		authorFilter.setEmptySelectionCaption("Select an author");
		authorFilter.setEmptySelectionAllowed(true);
	}

	private void initCategoryFilter() {
		categoryFilter = new Select<>();
		categoryFilter.setPlaceholder(Messages.CATEGORY_TEXT);
		categoryFilter.setWidth("33%");
		categoryFilter.setEmptySelectionCaption("Select a category");
		categoryFilter.setEmptySelectionAllowed(true);
	}

	public void showError(String msg) {
		Notification.show(msg);
	}

	/**
	 * Shows a temporary popup notification to the user.
	 * 
	 * @see Notification#show(String)
	 * @param msg
	 */
	public void showNotification(String msg) {
		Notification.show(msg);
	}

	/**
	 * Enables/Disables the new book button.
	 * 
	 * @param enabled
	 */
	public void setNewBookEnabled(boolean enabled) {
		newBookButton.setEnabled(enabled);
	}

	/**
	 * Deselects the selected row in the grid.
	 */
	public void clearSelection() {
		grid.getSelectionModel().deselectAll();
	}

	/**
	 * Selects a row
	 * 
	 * @param row
	 */
	public void selectRow(Book row) {
		grid.getSelectionModel().select(row);
	}

	/**
	 * Updates a book in the list of books.
	 * 
	 * @param book
	 */
	public void updateBook(Book book) {
		dataProvider.save(book);
		refreshGrid();
	}

	/**
	 * Removes a book from the list of books.
	 * 
	 * @param book
	 */
	public void removeBook(Book book) {
		dataProvider.delete(book);
		refreshGrid();
	}

	private void refreshGrid() {
		grid.setItems(bookService.get().getAll());
	}

	/**
	 * Displays user a form to edit a Book.
	 * 
	 * @param book
	 */
	public void editBook(Book book) {
		showForm(book != null);
		form.editBook(book);
	}

	/**
	 * Shows and hides the new book form
	 * 
	 * @param show
	 */
	public void showForm(boolean show) {
		form.setVisible(show);
		form.setEnabled(show);
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		presenter.enter(parameter);
	}
}
