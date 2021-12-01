package com.project.msic.library.ui.inventory;

import java.util.Locale;
import java.util.Objects;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Book;
import com.project.msic.library.service.BookService;
import com.vaadin.flow.data.provider.ListDataProvider;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link Product} entities.
 * <p>
 * Used to simplify the code in {@link SampleCrudView} and
 * {@link SampleCrudLogic}.
 */
public class BookDataProvider extends ListDataProvider<Book> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8265390551625014469L;

	private ServiceRef<BookService> bookService;
	/** Text filter that can be changed separately. */
	private String filterText = "";

	public BookDataProvider(ServiceRef<BookService> bookService2) {
		super(bookService2.get().getAll());
		this.bookService = bookService2;
	}

	/**
	 * Store given product to the backing data service.
	 *
	 * @param book the updated or new book
	 */
	public void save(Book book) {
		final boolean newBook = book.isNewBook();

		if (newBook) {
			refreshAll();
		} else {
			refreshItem(book);
		}
		bookService.get().saveUpdate(book);
	}

	/**
	 * Delete given product from the backing data service.
	 *
	 * @param book the book to be deleted
	 */
	public void delete(Book book) {
		bookService.get().delete(book);
		refreshAll();
	}

	/**
	 * Sets the filter to use for this data provider and refreshes data.
	 * <p>
	 * Filter is compared for product name, availability and category.
	 *
	 * @param filterText the text to filter by, never null
	 */
	public void setFilter(String filterText) {
		Objects.requireNonNull(filterText, Messages.FILTER_TEXT_CANNOT_BE_NULL_MESSAGE);
		if (Objects.equals(this.filterText, filterText.trim())) {
			return;
		}
		this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

		setFilter(book -> passesFilter(book.getTitle(), this.filterText)
				|| passesFilter(book.getAvailability(), this.filterText)
				|| passesFilter(book.getCategory().getName(), this.filterText));
	}

	@Override
	public Long getId(Book book) {
		Objects.requireNonNull(book, Messages.NULL_BOOK_MESSAGE);

		return book.getBookId();
	}

	private boolean passesFilter(Object object, String filterText) {
		return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
	}
}
