package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Category;
import com.project.msic.library.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public synchronized Collection<Category> getAll() {
		List<Category> categories = categoryRepository.findAll();
		return Collections.unmodifiableList(categories);
	}

	public void remove(Category category) {
		if(category != null) {
			categoryRepository.delete(category);
		}
	}

	public void saveUpdate(Category category) {
		if(category != null) {
			categoryRepository.saveAndFlush(category);
		}
	}
}
