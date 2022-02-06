package com.project.msic.library.model;

import java.io.Serializable;
import java.util.Objects;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Simon Jozsef-Gabriel 
 * Category entity POJO class used for the representation of the objects from the LBR_CATEGORY table
 */
@Entity
@Table(name = "LBR_CATEGORY")
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7062992072861469391L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId = -1L;
	private String name;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Book> books;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		if (categoryId == -1) {
			return super.hashCode();
		}

		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || categoryId == -1) {
			return false;
		}

		if (obj instanceof Category) {
			return categoryId == ((Category) obj).categoryId;
		}

		return false;
	}
}
