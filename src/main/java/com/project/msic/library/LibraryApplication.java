package com.project.msic.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "com.project.msic.library.model" })
@EnableJpaRepositories("com.project.msic.library.repository")
@ComponentScan({ "com.project.msic.library.ui", "com.project.msic.library.service", "com.project.msic.library.configuration",
"com.project.msic.library.authentication", "de.codecamp.vaadin.serviceref" })
@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class);
	}
}
