package com.project.msic.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.msic.library.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);

	User findByEmail(String email);

}
