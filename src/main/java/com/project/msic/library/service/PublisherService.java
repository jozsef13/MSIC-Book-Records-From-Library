package com.project.msic.library.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.msic.library.model.Publisher;
import com.project.msic.library.repository.PublisherRepository;

@Service
public class PublisherService{

	@Autowired
	private PublisherRepository publisherRepository;

	public Collection<Publisher> getAll() {
		List<Publisher> publishers = publisherRepository.findAll();
		return Collections.unmodifiableList(publishers);
	}

	public void remove(Publisher publisher) {
		if(publisher != null) {
			publisherRepository.delete(publisher);
		}
	}

	public void saveUpdate(Publisher publisher) {
		if(publisher != null) {
			publisherRepository.saveAndFlush(publisher);
		}
	}
}
