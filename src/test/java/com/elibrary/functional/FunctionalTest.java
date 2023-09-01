package com.elibrary.functional;

import static com.elibrary.testutils.TestUtils.businessTestFile;
import static com.elibrary.testutils.TestUtils.currentTest;
import static com.elibrary.testutils.TestUtils.testReport;
import static com.elibrary.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elibrary.inventory.Inventory;
import com.elibrary.models.Book;
import com.elibrary.models.User;

public class FunctionalTest {

	private Inventory inventory;
	private User user;
	private Book book;

	@BeforeEach
	public void setUp() {
		inventory = new Inventory();
		user = new User("user123", "John Doe", new ArrayList<>());
		book = new Book("1234567890123", "Mock Book", "Mock Author", "Mock Publisher", true, LocalDate.now(),
				LocalDate.now().plusDays(14));
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddBook() throws IOException, ClassNotFoundException {
		try {
			inventory.addBook(book);
			yakshaAssert(currentTest(), inventory.books.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetBookByName() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.getBookByName("mock").isPresent(), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testSearchBooksByAuthor() throws IOException {
		try {
			inventory.books.add(book);
			List<Book> result = inventory.searchBooksByAuthor("mock");
			yakshaAssert(currentTest(), result.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testSearchBooksByPublisher() throws IOException {
		try {
			inventory.books.add(book);
			List<Book> result = inventory.searchBooksByPublisher("mock");
			yakshaAssert(currentTest(), result.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testUpdateBook() throws IOException {
		try {
			inventory.books.add(book);
			Book updatedBook = new Book("1234567890123", "Updated Book", "Updated Author", "Updated Publisher", false,
					LocalDate.now(), LocalDate.now().plusDays(7));
			Book book = inventory.updateBook(updatedBook);
			yakshaAssert(currentTest(), book != null && book.getTitle().equals("Updated Book"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testIssueBook() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7)) == true
					&& inventory.isBookAvailable("1234567890123") == false, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testBookAvailableAfterReturn() throws IOException {
		try {
			inventory.books.add(book);
			inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7));
			yakshaAssert(currentTest(),
					inventory.returnBook("1234567890123", user) && inventory.isBookAvailable("1234567890123") == true,
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testIsBookAvailable() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.isBookAvailable("1234567890123"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetBorrowedBooks() throws IOException {
		try {
			inventory.books.add(book);
			inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7));
			List<Book> borrowedBooks = inventory.getBorrowedBooks();
			yakshaAssert(currentTest(), borrowedBooks.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testRenewBook() throws IOException {
		try {
			inventory.books.add(book);
			inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7));
			yakshaAssert(currentTest(), inventory.renewBook("1234567890123", user, LocalDate.now().plusDays(14)),
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetTopBorrowers() throws IOException {
		try {
			inventory.users = Map.of("user123", user);
			List<User> topBorrowers = inventory.getTopBorrowers(1);
			yakshaAssert(currentTest(), topBorrowers.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetPopularBooks() throws IOException {
		try {
			inventory.books.add(book);
			inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7));
			List<Book> popularBooks = inventory.getPopularBooks(1);
			yakshaAssert(currentTest(), popularBooks.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetUserById() throws IOException {
		try {
			inventory.users = Map.of("user123", user);
			User retrievedUser = inventory.getUserById("user123");
			yakshaAssert(currentTest(), retrievedUser != null, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetUsers() throws IOException {
		try {
			inventory.users = Map.of("user123", user);
			Map<String, User> retrievedUsers = inventory.getUsers();
			yakshaAssert(currentTest(), retrievedUsers.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testIssueBookSuccessful() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7)),
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testIssueBookUnavailable() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7)),
					businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testReturnBookSuccessful() throws IOException {
		try {
			inventory.books.add(book);
			inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7));
			yakshaAssert(currentTest(), inventory.returnBook("1234567890123", user), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testReturnBookUnavailable() throws IOException {
		try {
			inventory.users = Map.of("user123", user);
			inventory.issueBook("1234567890123", user, LocalDate.now().plusDays(7));
			yakshaAssert(currentTest(), !inventory.returnBook("1234567890123", user), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
}
