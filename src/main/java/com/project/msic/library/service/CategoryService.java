package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Category;
import com.project.msic.library.repository.CategoryRepository;

/**
 * @author Simon Jozsef-Gabriel
 * Class representing the Category service, the linkage between the data tier 
 * and the UI, where the business logic is implemented.
 */
@Service
public class CategoryService {

	/**
	 * The repository instance for the lbr_category table implementing the CRUD operations and
	 * other data tier method
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * @return a list containing all the Category objects from the database
	 */
	public synchronized Collection<Category> getAll() {
		List<Category> categories = categoryRepository.findAll();
		return Collections.unmodifiableList(categories);
	}
	
	/**
	 * @param category representing the Category object that will be removed
	 */
	public void remove(Category category) {
		if(category != null) {
			categoryRepository.delete(category);
		}
	}

	/**
	 * @param category representing the Category object that will be saved/updated
	 */
	public void saveUpdate(Category category) {
		if(category != null) {
			categoryRepository.saveAndFlush(category);
		}
	}
}
