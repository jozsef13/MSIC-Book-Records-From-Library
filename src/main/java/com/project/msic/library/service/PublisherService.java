package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Publisher;
import com.project.msic.library.repository.PublisherRepository;

/**
 * @author Simon Jozsef-Gabriel
 * Class representing the Publisher service, the linkage between the data tier 
 * and the UI, where the business logic is implemented.
 */
@Service
public class PublisherService{

	/**
	 * The repository instance for the lbr_publisher table implementing the CRUD operations and
	 * other data tier method
	 */
	@Autowired
	private PublisherRepository publisherRepository;

	/**
	 * @return a list containing all the Publisher objects from the database
	 */
	public Collection<Publisher> getAll() {
		List<Publisher> publishers = publisherRepository.findAll();
		return Collections.unmodifiableList(publishers);
	}

	/**
	 * @param publisher representing the Publisher object that will be removed
	 */
	public void remove(Publisher publisher) {
		if(publisher != null) {
			publisherRepository.delete(publisher);
		}
	}

	/**
	 * @param publisher representing the Publisher object that will be saved/updated
	 */
	public void saveUpdate(Publisher publisher) {
		if(publisher != null) {
			publisherRepository.saveAndFlush(publisher);
		}
	}
}
