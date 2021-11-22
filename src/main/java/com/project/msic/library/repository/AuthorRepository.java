package com.project.msic.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.msic.library.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
