package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Book;
import com.project.msic.library.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Book getById(Long bookId) {
		return bookRepository.getById(bookId);
	}

	public Collection<Book> getAll() {
		List<Book> books = bookRepository.findAll();
		return Collections.unmodifiableList(books);
	}

	public void saveUpdate(Book book) {
		if (book != null) {
			bookRepository.saveAndFlush(book);
		}
	}

	public void delete(Book book) {
		if (book != null) {
			bookRepository.delete(book);
		}
	}
}
