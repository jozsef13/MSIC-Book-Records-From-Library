package com.project.msic.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.msic.library.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
