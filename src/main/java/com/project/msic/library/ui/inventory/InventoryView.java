package com.project.msic.library.ui.inventory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Book;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link InventoryViewLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
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

		final HorizontalLayout topLayout = createTopBar();
		grid = new BookGrid();
		grid.setItems(dataProvider);
		// Allows user to select a single row in the grid.
		grid.asSingleSelect().addValueChangeListener(event -> presenter.rowSelected(event.getValue()));
		form = new BookForm(presenter);
		form.setCategories(categoryService.get().getAll());
		form.setAuthors(authorService.get().getAll());
		form.setPublishers(publisherService.get().getAll());
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

	public HorizontalLayout createTopBar() {
		filter = new TextField();
		filter.setPlaceholder(Messages.FILTER_PLACEHOLDER_TEXT);
		// Apply the filter to grid's data provider. TextField value is never
		filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
		// A shortcut to focus on the textField by pressing ctrl + F
		filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

		newBookButton = new Button(Messages.NEW_BOOK_BUTTON_TEXT);
		// Setting theme variant of new production button to LUMO_PRIMARY that
		// changes its background color to blue and its text color to white
		newBookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		newBookButton.setIcon(VaadinIcon.PLUS_CIRCLE.create());
		newBookButton.addClickListener(click -> presenter.newBook());
		// A shortcut to click the new product button by pressing ALT + N
		newBookButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
		final HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setWidth("100%");
		topLayout.add(filter);
		topLayout.add(newBookButton);
		topLayout.setVerticalComponentAlignment(Alignment.START, filter);
		topLayout.expand(filter);
		return topLayout;
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
	 * Enables/Disables the new product button.
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
	 * Updates a product in the list of products.
	 * 
	 * @param book
	 */
	public void updateBook(Book book) {
		dataProvider.save(book);
		refreshGrid();
	}

	/**
	 * Removes a product from the list of products.
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
	 * Shows and hides the new product form
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
