package com.elibrary;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.elibrary.exception.ISBNAlreadyExistsException;
import com.elibrary.inventory.Inventory;
import com.elibrary.models.Book;
import com.elibrary.models.User;

public class LibraryManagementApp {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Inventory inventory = new Inventory();

		while (true) {
			System.out.println("Options:");
			System.out.println("1. Add Book");
			System.out.println("2. Get Book by Name");
			System.out.println("3. Search Books by Author");
			System.out.println("4. Search Books by Publisher");
			System.out.println("5. Issue a Book");
			System.out.println("6. Return a Book");
			System.out.println("7. Check Availability");
			System.out.println("8. List Borrowed Books");
			System.out.println("9. Renew a Book");
			System.out.println("10. Top Borrowers");
			System.out.println("11. Popular Books");
			System.out.println("12. Update Book");
			System.out.println("13. Get All Books");
			System.out.println("14. Get Books by Issued Date");
			System.out.println("15. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				addBook(inventory);
				break;
			case 2:
				getBookByName(inventory);
				break;
			case 3:
				searchBooksByAuthor(inventory);
				break;
			case 4:
				searchBooksByPublisher(inventory);
				break;
			case 5:
				issueBook(inventory);
				break;
			case 6:
				returnBook(inventory);
				break;
			case 7:
				checkAvailability(inventory);
				break;
			case 8:
				listBorrowedBooks(inventory);
				break;
			case 9:
				renewBook(inventory);
				break;
			case 10:
				topBorrowers(inventory);
				break;
			case 11:
				popularBooks(inventory);
				break;
			case 12:
				updateBook(inventory);
				break;
			case 13:
				getAllBooks(inventory);
				break;
			case 14:
				getBooksByIssuedDate(inventory);
				break;
			case 15:
				System.out.println("Exiting...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void addBook(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter ISBN: ");
		String isbn = scanner.nextLine();
		System.out.print("Enter Title: ");
		String title = scanner.nextLine();
		System.out.print("Enter Author: ");
		String author = scanner.nextLine();
		System.out.print("Enter Publisher: ");
		String publisher = scanner.nextLine();
		Book newBook = new Book(isbn, title, author, publisher, true, null, null);
		try {
			inventory.addBook(newBook);
			System.out.println("Book added successfully.");
		} catch (ISBNAlreadyExistsException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void getBookByName(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the name of the book: ");
		String bookName = scanner.nextLine();
		Optional<Book> bookOptional = inventory.getBookByName(bookName);
		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();
			System.out.println("Book found:");
			System.out.println("Title: " + book.getTitle());
			System.out.println("Author: " + book.getAuthor());
			System.out.println("Publisher: " + book.getPublisher());
			System.out.println("ISBN: " + book.getIsbn());
			if (book.isAvailable()) {
				System.out.println("Status: Available");
			} else {
				System.out.println("Status: Issued");
				System.out.println("Issued Date: " + book.getIssuedDate());
				System.out.println("Due Date: " + book.getDueDate());
			}
		} else {
			System.out.println("Book not found.");
		}
	}

	private static void searchBooksByAuthor(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the author's name: ");
		String authorName = scanner.nextLine();

		List<Book> booksByAuthor = inventory.searchBooksByAuthor(authorName);

		if (!booksByAuthor.isEmpty()) {
			System.out.println("Books by author " + authorName + ":");
			for (Book book : booksByAuthor) {
				System.out.println("Title: " + book.getTitle());
				System.out.println("ISBN: " + book.getIsbn());
				System.out.println("Publisher: " + book.getPublisher());
				if (book.isAvailable()) {
					System.out.println("Status: Available");
				} else {
					System.out.println("Status: Issued");
					System.out.println("Issued Date: " + book.getIssuedDate());
					System.out.println("Due Date: " + book.getDueDate());
				}
				System.out.println();
			}
		} else {
			System.out.println("No books found by author " + authorName);
		}
	}

	private static void searchBooksByPublisher(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the publisher's name: ");
		String publisherName = scanner.nextLine();

		List<Book> booksByPublisher = inventory.searchBooksByPublisher(publisherName);

		if (!booksByPublisher.isEmpty()) {
			System.out.println("Books by publisher " + publisherName + ":");
			for (Book book : booksByPublisher) {
				System.out.println("Title: " + book.getTitle());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("ISBN: " + book.getIsbn());
				if (book.isAvailable()) {
					System.out.println("Status: Available");
				} else {
					System.out.println("Status: Issued");
					System.out.println("Issued Date: " + book.getIssuedDate());
					System.out.println("Due Date: " + book.getDueDate());
				}
				System.out.println();
			}
		} else {
			System.out.println("No books found by publisher " + publisherName);
		}
	}

	private static void issueBook(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter ISBN of the book: ");
		String isbn = scanner.nextLine();

		if (!inventory.isBookAvailable(isbn)) {
			System.out.println("The book with ISBN " + isbn + " is not available for issuance.");
			return;
		}

		System.out.print("Enter user ID: ");
		String userId = scanner.nextLine();
		User user = inventory.getUserById(userId);

		if (user == null) {
			System.out.println("User with ID " + userId + " not found.");
			return;
		}

		System.out.print("Enter due date (YYYY-MM-DD): ");
		String dueDateStr = scanner.nextLine();
		LocalDate dueDate = LocalDate.parse(dueDateStr);

		boolean success = inventory.issueBook(isbn, user, dueDate);

		if (success) {
			System.out.println("Book issued successfully to user " + user.getName() + ".");
		} else {
			System.out.println("Failed to issue the book. Please check the book availability and user details.");
		}
	}

	private static void returnBook(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter ISBN of the book: ");
		String isbn = scanner.nextLine();

		System.out.print("Enter userId: ");
		String userId = scanner.nextLine();

		User user = inventory.getUserById(userId); // Assuming you have a method to get user by ID
		if (user == null) {
			System.out.println("User not found.");
			return;
		}

		boolean success = inventory.returnBook(isbn, user);

		if (success) {
			System.out.println("Book returned successfully by user " + user.getName() + ".");
		} else {
			System.out.println("Failed to return the book. Please check the book details.");
		}
	}

	private static void checkAvailability(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter ISBN of the book: ");
		String isbn = scanner.nextLine();

		if (inventory.isBookAvailable(isbn)) {
			System.out.println("The book with ISBN " + isbn + " is available.");
		} else {
			System.out.println("The book with ISBN " + isbn + " is not available.");
		}
	}

	private static void listBorrowedBooks(Inventory inventory) {
		List<Book> borrowedBooks = inventory.getBorrowedBooks();

		if (!borrowedBooks.isEmpty() && inventory.getUsers() != null) {
			System.out.println("Borrowed Books:");
			for (Book book : borrowedBooks) {
				System.out.println("Title: " + book.getTitle());
				System.out.println("ISBN: " + book.getIsbn());

				// Find the user who borrowed the book
				Optional<User> borrower = inventory.getUsers().values().stream()
						.filter(user -> user.hasBorrowedBook(book)).findFirst();

				if (borrower.isPresent()) {
					System.out.println("Borrower: " + borrower.get().getName());
				} else {
					System.out.println("Borrower: Unknown");
				}

				System.out.println("Due Date: " + book.getDueDate());
				System.out.println();
			}
		} else {
			System.out.println("No books are currently borrowed.");
		}
	}

	private static void renewBook(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter ISBN of the book: ");
		String isbn = scanner.nextLine();

		User borrower = inventory.getUsers().values().stream()
				.filter(user -> user.getBorrowedBooks().stream().anyMatch(book -> book.getIsbn().equals(isbn)))
				.findFirst().orElse(null);

		if (borrower != null) {
			System.out.print("Enter new due date (YYYY-MM-DD): ");
			String newDueDateStr = scanner.nextLine();
			LocalDate newDueDate = LocalDate.parse(newDueDateStr);

			boolean success = inventory.renewBook(isbn, borrower, newDueDate);

			if (success) {
				System.out.println("Book renewed successfully for user " + borrower.getName() + ".");
			} else {
				System.out.println("Failed to renew the book. Please check the book details.");
			}
		} else {
			System.out.println("No user found who has borrowed the book with ISBN " + isbn + ".");
		}
	}

	private static void topBorrowers(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the number of top borrowers to display: ");
		int count = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		List<User> topBorrowers = inventory.getTopBorrowers(count);

		if (!topBorrowers.isEmpty()) {
			System.out.println("Top Borrowers:");
			for (User user : topBorrowers) {
				System.out.println("User ID: " + user.getUserId());
				System.out.println("Name: " + user.getName());
				System.out.println("Number of Borrowed Books: " + user.getBorrowedBooks().size());
				System.out.println();
			}
		} else {
			System.out.println("No borrowers found.");
		}
	}

	private static void popularBooks(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the number of popular books to display: ");
		int count = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character
		List<Book> popularBooks = inventory.getAllBooks().stream()
				.sorted(Comparator.comparingInt(book -> -getBorrowCount(inventory, book))) // Sort by borrow count in
																							// descending order
				.limit(count).collect(Collectors.toList());
		if (!popularBooks.isEmpty()) {
			System.out.println("Popular Books:");
			for (Book book : popularBooks) {
				int borrowCount = getBorrowCount(inventory, book);
				System.out.println("Title: " + book.getTitle());
				System.out.println("ISBN: " + book.getIsbn());
				System.out.println("Borrow Count: " + borrowCount);
				System.out.println();
			}
		} else {
			System.out.println("No popular books found.");
		}
	}

	private static int getBorrowCount(Inventory inventory, Book book) {
		int borrowCount = 0;
		if (inventory.getUsers() != null) {
			for (User user : inventory.getUsers().values()) {
				for (Book borrowedBook : user.getBorrowedBooks()) {
					if (borrowedBook.getIsbn().equals(book.getIsbn())) {
						borrowCount++;
					}
				}
			}
		}
		return borrowCount;
	}

	private static void updateBook(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter ISBN of the book to update: ");
		String isbn = scanner.nextLine();

		Book bookToUpdate = inventory.getAllBooks().stream().filter(book -> book.getIsbn().equals(isbn)).findFirst()
				.orElse(null);

		if (bookToUpdate != null) {
			System.out.print("Enter updated title: ");
			String updatedTitle = scanner.nextLine();
			System.out.print("Enter updated author: ");
			String updatedAuthor = scanner.nextLine();
			System.out.print("Enter updated publisher: ");
			String updatedPublisher = scanner.nextLine();

			Book updatedBook = new Book(isbn, updatedTitle, updatedAuthor, updatedPublisher, bookToUpdate.isAvailable(),
					bookToUpdate.getDueDate(), bookToUpdate.getIssuedDate());
			inventory.updateBook(updatedBook);
			System.out.println("Book updated successfully.");
		} else {
			System.out.println("No book found with ISBN " + isbn + ".");
		}
	}

	private static void getAllBooks(Inventory inventory) {
		List<Book> allBooks = inventory.getAllBooks();

		if (!allBooks.isEmpty()) {
			System.out.println("All Books:");
			for (Book book : allBooks) {
				System.out.println("Title: " + book.getTitle());
				System.out.println("ISBN: " + book.getIsbn());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("Publisher: " + book.getPublisher());
				System.out.println("Available: " + (book.isAvailable() ? "Yes" : "No"));
				System.out.println();
			}
		} else {
			System.out.println("No books available.");
		}
	}

	private static void getBooksByIssuedDate(Inventory inventory) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter issued date (YYYY-MM-DD): ");
		String issuedDateStr = scanner.nextLine();
		LocalDate issuedDate = LocalDate.parse(issuedDateStr);

		List<Book> booksByIssuedDate = inventory.getBooksByIssuedDate(issuedDate);

		if (!booksByIssuedDate.isEmpty()) {
			System.out.println("Books Issued on " + issuedDate + ":");
			for (Book book : booksByIssuedDate) {
				System.out.println("Title: " + book.getTitle());
				System.out.println("ISBN: " + book.getIsbn());
				System.out.println();
			}
		} else {
			System.out.println("No books issued on " + issuedDate + ".");
		}
	}

}
