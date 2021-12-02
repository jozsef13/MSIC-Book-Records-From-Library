package com.project.msic.library.ui.inventory;

import java.io.Serializable;

import com.project.msic.library.authentication.AccessControl;
import com.project.msic.library.authentication.AccessControlFactory;
import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Book;
import com.project.msic.library.service.BookService;
import com.project.msic.library.service.UserService;
import com.project.msic.library.ui.dialog.ErrorDialog;
import com.project.msic.library.ui.dialog.SimpleDialogUtil;
import com.vaadin.flow.component.UI;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the book editor form and the data source, including
 * fetching and saving books.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class InventoryViewPresenter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -793131444780007858L;

	private final InventoryView view;
	private ServiceRef<BookService> bookService;
	private ServiceRef<UserService> userService;

	public InventoryViewPresenter(InventoryView view, ServiceRef<BookService> bookService2,
			ServiceRef<UserService> userService2) {
		this.view = view;
		this.bookService = bookService2;
		this.userService = userService2;
	}

	/**
	 * Does the initialization of the inventory view including disabling the buttons
	 * if the user doesn't have access.
	 */
	public void init() {
		if (!AccessControlFactory.getInstance().createAccessControl(userService)
				.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
			view.setNewBookEnabled(false);
		}
	}

	public void cancelBook() {
		setFragmentParameter("");
		view.clearSelection();
	}

	/**
	 * Updates the fragment without causing this class navigator to change
	 * view. It actually appends the bookId as a parameter to the URL. The
	 * parameter is set to keep the view state the same during e.g. a refresh and to
	 * enable bookmarking of individual book selections.
	 *
	 */
	private void setFragmentParameter(String bookId) {
		String fragmentParameter;
		if (bookId == null || bookId.isEmpty()) {
			fragmentParameter = "";
		} else {
			fragmentParameter = bookId;
		}

		UI.getCurrent().navigate(InventoryView.class, fragmentParameter);
	}

	/**
	 * Opens the book form and clears its fields to make it ready for entering a
	 * new book if bookId is null, otherwise loads the book with the given
	 * bookId and shows its data in the form fields so the user can edit them.
	 *
	 * 
	 * @param bookId
	 */
	public void enter(String bookId) {
		if (bookId != null && !bookId.isEmpty()) {
			if (bookId.equals("new")) {
				newBook();
			} else {
				// Ensure this is selected even if coming directly here from
				// login
				try {
					final Long bid = Long.parseLong(bookId);
					final Book book = findBook(bid);
					view.selectRow(book);
				} catch (final NumberFormatException e) {
					final ErrorDialog errorDialog = SimpleDialogUtil.errorDialog(Messages.ERROR_TITLE,
							Messages.BOOK_ID_NUMBER_FORMAT_ERROR_TEXT);
					errorDialog.open();
				}
			}
		} else {
			view.showForm(false);
		}
	}

	private Book findBook(Long bookId) {
		return bookService.get().getById(bookId);
	}

	public void saveBook(Book book) {
		final boolean newBook = book.isNewBook();
		view.clearSelection();
		view.updateBook(book);
		setFragmentParameter("");
		view.showNotification(book.getTitle() + (newBook ? " created" : " updated"));
	}

	public void deleteBook(Book book) {
		view.clearSelection();
		view.removeBook(book);
		setFragmentParameter("");
		view.showNotification(book.getTitle() + " removed");
	}

	public void newBook() {
		view.clearSelection();
		setFragmentParameter("new");
		view.editBook(new Book());
	}

	public void rowSelected(Book book) {
		if (AccessControlFactory.getInstance().createAccessControl(userService)
				.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
			editBook(book);
		}
	}

	public void editBook(Book book) {
		if (book == null) {
			setFragmentParameter("");
		} else {
			setFragmentParameter(book.getBookId() + "");
		}

		view.editBook(book);
	}
}
