package com.project.msic.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.msic.library.model.Publisher;

/**
 * @author Simon Jozsef-Gabriel
 * Interface representing the Publisher repository, the database tier
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
