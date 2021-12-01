package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Author;
import com.project.msic.library.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	public Collection<Author> getAll() {
		List<Author> authors = authorRepository.findAll();
		return Collections.unmodifiableList(authors);
	}

	public void remove(Author author) {
		if(author != null) {
			authorRepository.delete(author);
		}
	}

	public void saveUpdate(Author author) {
		if(author != null) {
			authorRepository.saveAndFlush(author);
		}
	}
}
