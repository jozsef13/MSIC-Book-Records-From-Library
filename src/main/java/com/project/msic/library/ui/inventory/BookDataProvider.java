package com.project.msic.library.ui.inventory;

import java.util.Locale;
import java.util.Objects;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Author;
import com.project.msic.library.model.Book;
import com.project.msic.library.model.Category;
import com.project.msic.library.model.Publisher;
import com.project.msic.library.service.BookService;
import com.vaadin.flow.data.provider.ListDataProvider;

import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link Book} entities.
 * <p>
 */
public class BookDataProvider extends ListDataProvider<Book> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8265390551625014469L;

	private ServiceRef<BookService> bookService;
	/** Text filter that can be changed separately. */
	private String filterText = "";
	private String publisherText = "";
	private String authorText = "";
	private String categoryText = "";

	public BookDataProvider(ServiceRef<BookService> bookService2) {
		super(bookService2.get().getAll());
		this.bookService = bookService2;
	}

	/**
	 * Store given book to the backing data service.
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
	 * Delete given book from the backing data service.
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
	 * Filter is compared for book name.
	 *
	 * @param filterText the text to filter by, never null
	 */
	public void setTextFilter(String filterText) {
		Objects.requireNonNull(filterText, Messages.FILTER_TEXT_CANNOT_BE_NULL_MESSAGE);
		if (Objects.equals(this.filterText, filterText.trim())) {
			return;
		}
		this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

		setFilter(book -> passesFilter(book.getTitle(), this.filterText));
	}

	@Override
	public Long getId(Book book) {
		Objects.requireNonNull(book, Messages.NULL_BOOK_MESSAGE);

		return book.getBookId();
	}

	private boolean passesFilter(Object object, String filterText) {
		return object != null && object.toString().toLowerCase(Locale.ENGLISH).contains(filterText);
	}

	public void setPublisherFilter(Publisher publisher) {
		if (publisher == null) {
			this.publisherText = "";
			setFilter(book -> passesFilter(book.getPublisher().getName(), this.publisherText));
			return;
		}
		if (Objects.equals(this.publisherText, publisher.getName())) {
			return;
		}
		this.publisherText = publisher.getName().trim().toLowerCase(Locale.ENGLISH);
		setFilter(book -> passesFilter(book.getPublisher().getName(), this.publisherText));
	}

	public void setAuthorFilter(Author author) {
		if (author == null) {
			this.authorText = "";
			setFilter(book -> passesFilter(book.getAuthor().getName(), this.authorText));
			return;
		}
		if (Objects.equals(this.authorText, author.getName())) {
			return;
		}
		this.authorText = author.getName().trim().toLowerCase(Locale.ENGLISH);
		setFilter(book -> passesFilter(book.getAuthor().getName(), this.authorText));
	}

	public void setCategoryFilter(Category category) {
		if (category == null) {
			this.categoryText = "";
			setFilter(book -> passesFilter(book.getCategory().getName(), this.categoryText));
			return;
		}
		if (Objects.equals(this.categoryText, category.getName())) {
			return;
		}
		this.categoryText = category.getName().trim().toLowerCase(Locale.ENGLISH);
		setFilter(book -> passesFilter(book.getCategory().getName(), this.categoryText));
	}
}
