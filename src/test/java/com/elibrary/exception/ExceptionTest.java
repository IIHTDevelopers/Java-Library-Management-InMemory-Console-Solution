package com.elibrary.exception;

import static com.elibrary.testutils.TestUtils.testReport;
import static com.elibrary.testutils.TestUtils.yakshaAssert;
import static com.elibrary.testutils.TestUtils.exceptionTestFile;
import static com.elibrary.testutils.TestUtils.currentTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elibrary.inventory.Inventory;
import com.elibrary.models.Book;
import com.elibrary.models.User;

class BookExceptionTest {

	private Inventory inventory;

	@BeforeEach
	public void setUp() {
		inventory = new Inventory();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddBookWithDuplicateISBN() throws IOException {
		try {
			Book existingBook = new Book("1234567890123", "Existing Book", "Author", "Publisher", true, LocalDate.now(),
					LocalDate.now().plusDays(14));
			inventory.books.add(existingBook);
			Book duplicateBook = new Book("1234567890123", "Duplicate Book", "Author", "Publisher", true,
					LocalDate.now(), LocalDate.now().plusDays(14));
			inventory.addBook(duplicateBook);
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), true, exceptionTestFile);
		}
	}

	@Test
	public void testGetBookByNameNotFound() throws IOException {
		try {
			inventory.books.add(new Book("1234567890123", "Book1", "Author1", "Publisher1", true, LocalDate.now(),
					LocalDate.now().plusDays(14)));
			inventory.books.add(new Book("2345678901234", "Book2", "Author2", "Publisher2", true, LocalDate.now(),
					LocalDate.now().plusDays(14)));
			Optional<Book> result = inventory.getBookByName("NonExistentBook");
			yakshaAssert(currentTest(), result != null && result.isEmpty(), exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testSearchBooksByAuthorNotFound() throws IOException {
		try {
			inventory.books.add(new Book("1234567890123", "Book1", "Author1", "Publisher1", true, LocalDate.now(),
					LocalDate.now().plusDays(14)));
			inventory.books.add(new Book("2345678901234", "Book2", "Author2", "Publisher2", true, LocalDate.now(),
					LocalDate.now().plusDays(14)));
			List<Book> result = inventory.searchBooksByAuthor("NonExistentAuthor");
			yakshaAssert(currentTest(), result != null && result.isEmpty(), exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testSearchBooksByPublisherNotFound() throws IOException {
		try {
			inventory.books.add(new Book("1234567890123", "Book1", "Author1", "Publisher1", true, LocalDate.now(),
					LocalDate.now().plusDays(14)));
			inventory.books.add(new Book("2345678901234", "Book2", "Author2", "Publisher2", true, LocalDate.now(),
					LocalDate.now().plusDays(14)));
			List<Book> result = inventory.searchBooksByPublisher("NonExistentPublisher");
			yakshaAssert(currentTest(), result != null && result.isEmpty(), exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testUpdateBookNotFound() throws IOException {
		try {
			Book updatedBook = new Book("1234567890123", "Updated Book", "Updated Author", "Updated Publisher", true,
					LocalDate.now(), LocalDate.now().plusDays(14));
			Book book = inventory.updateBook(updatedBook);
			yakshaAssert(currentTest(), book == null, exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testReturnBookNotFound() throws IOException {
		try {
			User user = new User("user123", "John Doe", new ArrayList<>());
			Book book = new Book("1234567890123", "Book1", "Author1", "Publisher1", true, LocalDate.now(),
					LocalDate.now().plusDays(14));
			boolean result = inventory.returnBook("NonExistentISBN", user);
			yakshaAssert(currentTest(), result == false, exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testRenewBookNotFound() throws IOException {
		try {
			User user = new User("user123", "John Doe", new ArrayList<>());
			Book book = new Book("1234567890123", "Book1", "Author1", "Publisher1", true, LocalDate.now(),
					LocalDate.now().plusDays(14));
			boolean result = inventory.renewBook("NonExistentISBN", user, LocalDate.now().plusDays(7));
			yakshaAssert(currentTest(), result == false, exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testGetTopBorrowersWithNullUsers() throws IOException {
		try {
			List<User> result = inventory.getTopBorrowers(5);
			yakshaAssert(currentTest(), result != null && result.isEmpty(), exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testGetPopularBooksWithNoBorrowedBooks() throws IOException {
		try {
			List<Book> result = inventory.getPopularBooks(5);
			yakshaAssert(currentTest(), result != null && result.isEmpty(), exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}
}