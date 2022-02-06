package com.project.msic.library.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Simon Jozsef-Gabriel Book entity POJO class used for the
 *         representation of the objects from the LBR_BOOK table
 */
@Entity
@Table(name = "LBR_BOOK")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7279100007503919298L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId = -1L;
	private String title;
	@Column(name = "year", nullable = true)
	private String year;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authorId")
	private Author author;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	private Category category;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "publisherId")
	private Publisher publisher;
	private BookAvailability availability = BookAvailability.AVAILABLE;

	public BookAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(BookAvailability availability) {
		this.availability = availability;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean isNewBook() {
		return bookId < 0;
	}

	@Override
	public int hashCode() {
		if (bookId == -1) {
			return super.hashCode();
		}

		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || bookId == -1) {
			return false;
		}

		if (obj instanceof Book) {
			return bookId == ((Book) obj).bookId;
		}

		return false;
	}
}
