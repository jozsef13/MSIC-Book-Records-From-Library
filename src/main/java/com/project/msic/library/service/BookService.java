package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Book;
import com.project.msic.library.repository.BookRepository;

/**
 * @author Simon Jozsef-Gabriel
 * Class representing the Book service, the linkage between the data tier 
 * and the UI, where the business logic is implemented.
 */
@Service
public class BookService {

	/**
	 * The repository instance for the lbr_book table implementing the CRUD operations and
	 * other data tier method
	 */
	@Autowired
	private BookRepository bookRepository;

	/**
	 * @param bookId representing the id of the returning Book
	 * @return a book object based on it's id from the database
	 */
	public Book getById(Long bookId) {
		return bookRepository.getById(bookId);
	}

	/**
	 * @return a list containing all the Book objects from the database
	 */
	public Collection<Book> getAll() {
		List<Book> books = bookRepository.findAll();
		return Collections.unmodifiableList(books);
	}

	/**
	 * @param book representing the Book object that will be saved/updated
	 */
	public void saveUpdate(Book book) {
		if (book != null) {
			bookRepository.saveAndFlush(book);
		}
	}

	/**
	 * @param book representing the Book object that will be removed
	 */
	public void delete(Book book) {
		if (book != null) {
			bookRepository.delete(book);
		}
	}
}
