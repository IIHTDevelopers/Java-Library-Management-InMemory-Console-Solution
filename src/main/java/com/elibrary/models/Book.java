package com.elibrary.models;

import java.time.LocalDate;

public class Book {
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private boolean isAvailable;
	private LocalDate issuedDate;
	private LocalDate dueDate;

	public Book() {
		super();
	}

	public Book(String isbn, String title, String author, String publisher, boolean isAvailable, LocalDate issuedDate,
			LocalDate dueDate) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isAvailable = isAvailable;
		this.issuedDate = issuedDate;
		this.dueDate = dueDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public LocalDate getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDate issuedDate) {
		this.issuedDate = issuedDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", publisher=" + publisher + ", isAvailable="
				+ isAvailable + ", issuedDate=" + issuedDate + ", dueDate=" + dueDate + "]";
	}
}
