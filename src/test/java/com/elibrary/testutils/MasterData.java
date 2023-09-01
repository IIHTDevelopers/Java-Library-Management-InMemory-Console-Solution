package com.elibrary.testutils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.elibrary.models.Book;
import com.elibrary.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MasterData {

	private static final Random random = new Random();

	public static Book getBook() {
		return new Book(generateRandomIsbn(), "Mock Book " + random.nextInt(100), "Mock Author " + random.nextInt(100),
				"Mock Publisher " + random.nextInt(100), random.nextBoolean(), LocalDate.now(),
				LocalDate.now().plusDays(14));
	}

	public static List<Book> getBookList() {
		List<Book> books = new ArrayList<>();
		books.add(getBook());
		return books;
	}

	public static User getUser() {
		return new User("user" + random.nextInt(100), "Mock User " + random.nextInt(100), getBookList());
	}

	public static List<User> getUsersList(int count) {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			users.add(getUser());
		}
		return users;
	}

	private static String generateRandomIsbn() {
		StringBuilder isbn = new StringBuilder();
		for (int i = 0; i < 13; i++) {
			isbn.append(random.nextInt(10));
		}
		return isbn.toString();
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);

			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
