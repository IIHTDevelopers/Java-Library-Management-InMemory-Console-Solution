package com.elibrary.inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.elibrary.exception.ISBNAlreadyExistsException;
import com.elibrary.models.Book;
import com.elibrary.models.User;

public class Inventory {
	public List<Book> books = new ArrayList<>();
	public Map<String, User> users;

	public void addBook(Book book) throws ISBNAlreadyExistsException {
		boolean isbnExists = books.stream().anyMatch(existingBook -> existingBook.getIsbn().equals(book.getIsbn()));
		if (isbnExists) {
			throw new ISBNAlreadyExistsException("Book with the same ISBN already exists.");
		}
		books.add(book);
	}

	public Optional<Book> getBookByName(String name) {
		return books.stream().filter(book -> book.getTitle().toLowerCase().contains(name.toLowerCase())).findFirst();
	}

	public List<Book> searchBooksByAuthor(String author) {
		return books.stream().filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
				.collect(Collectors.toList());
	}

	public List<Book> searchBooksByPublisher(String publisher) {
		return books.stream().filter(book -> book.getPublisher().toLowerCase().contains(publisher.toLowerCase()))
				.collect(Collectors.toList());
	}

	public Book updateBook(Book updatedBook) {
		Optional<Book> bookToUpdate = books.stream()
				.filter(book -> book.getIsbn().equals(updatedBook.getIsbn()))
				.findFirst();
		if (bookToUpdate.isPresent()) {
			Book book = bookToUpdate.get();
			book.setTitle(updatedBook.getTitle());
			book.setAuthor(updatedBook.getAuthor());
			book.setPublisher(updatedBook.getPublisher());
			book.setAvailable(updatedBook.isAvailable());
			book.setDueDate(updatedBook.getDueDate());
			book.setIssuedDate(updatedBook.getIssuedDate());
			return book;
		} else {
			return null;
		}
	}
	
	public List<Book> getAllBooks() {
		return books;
	}

	public List<Book> getBooksByIssuedDate(LocalDate issuedDate) {
		return books.stream().filter(book -> book.getIssuedDate() != null && book.getIssuedDate().equals(issuedDate))
				.collect(Collectors.toList());
	}

	public boolean issueBook(String isbn, User user, LocalDate dueDate) {
		Book book = books.stream().filter(b -> b.getIsbn().equals(isbn) && b.isAvailable()).findFirst().orElse(null);
		if (book != null) {
			book.setAvailable(false);
			book.setIssuedDate(LocalDate.now());
			book.setDueDate(dueDate);
			user.borrowBook(book);
			return true;
		}
		return false;
	}

	public boolean returnBook(String isbn, User user) {
		Book book = books.stream().filter(b -> b.getIsbn().equals(isbn) && !b.isAvailable()).findFirst().orElse(null);

		if (book != null && user.hasBorrowedBook(book)) {
			book.setAvailable(true);
			book.setIssuedDate(null);
			book.setDueDate(null);
			user.returnBook(book);
			return true;
		}
		return false;
	}

	public boolean isBookAvailable(String isbn) {
		return books.stream().anyMatch(book -> book.getIsbn().equals(isbn) && book.isAvailable());
	}

	public List<Book> getBorrowedBooks() {
		return books.stream().filter(book -> !book.isAvailable()).collect(Collectors.toList());
	}

	public boolean renewBook(String isbn, User user, LocalDate newDueDate) {
		Book book = books.stream().filter(b -> b.getIsbn().equals(isbn) && !b.isAvailable()).findFirst().orElse(null);

		if (book != null && user.hasBorrowedBook(book)) {
			book.setDueDate(newDueDate);
			return true;
		}
		return false;
	}

	public List<User> getTopBorrowers(int count) {
		if (users != null) {

			return users.values().stream()
					.sorted(Comparator.<User>comparingInt(user -> user.getBorrowedBooks().size()).reversed())
					.limit(count).collect(Collectors.toList());
		} else {
			List<User> users = new ArrayList<>();
			return users;
		}
	}

	public List<Book> getPopularBooks(int count) {
		Map<Book, Integer> bookBorrowCount = new HashMap<>();
		for (Book book : books) {
			if (!book.isAvailable()) {
				bookBorrowCount.put(book, bookBorrowCount.getOrDefault(book, 0) + 1);
			}
		}

		return bookBorrowCount.entrySet().stream().sorted(Map.Entry.<Book, Integer>comparingByValue().reversed())
				.limit(count).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	public User getUserById(String userId) {
		return users.get(userId);
	}

	public Map<String, User> getUsers() {
		return this.users;
	}
}
