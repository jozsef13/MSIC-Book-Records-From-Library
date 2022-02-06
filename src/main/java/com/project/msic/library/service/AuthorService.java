package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Author;
import com.project.msic.library.repository.AuthorRepository;

/**
 * @author Simon Jozsef-Gabriel 
 * Class representing the Author service, the linkage between the data 
 * tier and the UI, where the business logic is implemented.
 */
@Service
public class AuthorService {

	/**
	 * The repository for the lbr_author table implementing the CRUD operations and
	 * other data tier method
	 */
	@Autowired
	private AuthorRepository authorRepository;

	/**
	 * @return a list containing all the Author objects from the database
	 */
	public Collection<Author> getAll() {
		List<Author> authors = authorRepository.findAll();
		return Collections.unmodifiableList(authors);
	}

	/**
	 * @param author representing the Author object that will be removed
	 */
	public void remove(Author author) {
		if (author != null) {
			authorRepository.delete(author);
		}
	}

	/**
	 * @param author representing the Author object that will be updated/saved
	 */
	public void saveUpdate(Author author) {
		if (author != null) {
			authorRepository.saveAndFlush(author);
		}
	}
}
