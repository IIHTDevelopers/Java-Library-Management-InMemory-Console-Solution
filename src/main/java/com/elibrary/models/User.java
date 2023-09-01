package com.elibrary.models;

import java.util.List;

public class User {
	private String userId;
	private String name;
	private List<Book> borrowedBooks;

	public User() {
		super();
	}

	public User(String userId, String name, List<Book> borrowedBooks) {
		super();
		this.userId = userId;
		this.name = name;
		this.borrowedBooks = borrowedBooks;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBorrowedBooks(List<Book> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", borrowedBooks=" + borrowedBooks + "]";
	}

	public void borrowBook(Book book) {
		borrowedBooks.add(book);
	}

	public void returnBook(Book book) {
		borrowedBooks.remove(book);
	}

	public boolean hasBorrowedBook(Book book) {
		return borrowedBooks.contains(book);
	}

	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}
}
