package com.project.msic.library.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LBR_PUBLISHER")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long publisherId = -1L;
	private String name;
	@OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Book> publishedBooks;

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getPublishedBooks() {
		return publishedBooks;
	}

	public void setPublishedBooks(Set<Book> publishedBooks) {
		this.publishedBooks = publishedBooks;
	}

	@Override
	public int hashCode() {
		if (publisherId == -1) {
			return super.hashCode();
		}

		return Objects.hash(publisherId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || publisherId == -1) {
			return false;
		}

		if (obj instanceof Publisher) {
			return publisherId == ((Publisher) obj).publisherId;
		}

		return false;
	}
}
