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
 * Author entity POJO class used for the representation of the objects from the LBR_AUTHOR table
 */
@Entity
@Table(name = "LBR_AUTHOR")
public class Author implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3795431677958203496L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorId = -1L;
	private String name;
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Book> writtenBooks;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getWrittenBooks() {
		return writtenBooks;
	}

	public void setWrittenBooks(Set<Book> writtenBooks) {
		this.writtenBooks = writtenBooks;
	}

	@Override
	public int hashCode() {
		if (authorId == -1) {
			return super.hashCode();
		}

		return Objects.hash(authorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || authorId == -1) {
			return false;
		}

		if (obj instanceof Author) {
			return authorId == ((Author) obj).authorId;
		}

		return false;
	}

}
