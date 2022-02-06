package com.project.msic.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.msic.library.model.Category;

/**
 * @author Simon Jozsef-Gabriel
 * Interface representing the Category repository, the database tier
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
