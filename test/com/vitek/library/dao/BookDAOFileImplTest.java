package com.vitek.library.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.vitek.library.entity.Book;

public class BookDAOFileImplTest {

	private static final String TEST_DATABASE_TXT = "TestDatabase.txt";
	private static final String SAVED_TEST_DATABASE_TXT = "SavedTestDatabase.txt";

	BookDAOFileImpl dao = new BookDAOFileImpl();

	@Test
	public void testLoadBooksWrongFileName() {
		dao.loadBooks("ASDASD");
		assertTrue(dao.getAllBooks().isEmpty());
	}

	@Test
	public void testLoadBooksCorrectFileName() {
		dao.loadBooks(TEST_DATABASE_TXT);
		assertFalse(dao.getAllBooks().isEmpty());
		assertTrue(dao.getAllBooks().size() == 3);
	}

	@Test(expected = IOException.class)
	public void testReadAndTransformIntoBooksIOException() throws IOException {
		dao.readAndTransformIntoBooks("asdf");
	}

	@Test
	public void testReadAndTransformIntoBooksEmptyFile() throws IOException {
		Set<Book> books = dao.readAndTransformIntoBooks("TestEmptyDatabase.txt");
		assertTrue(books.isEmpty());
	}

	@Test
	public void testReadAndTransformIntoBooks() throws IOException {

		Set<Book> books = dao.readAndTransformIntoBooks(TEST_DATABASE_TXT);

		Book book1 = new Book("1111", "Maugli1", "Kipling1", 2001);
		Book book2 = new Book("2222", "Maugli2", "Kipling2", 2002);
		Book book3 = new Book("3333", "Maugli3", "Kipling3", 2003);

		assertTrue(books.contains(book1));
		assertTrue(books.contains(book2));
		assertTrue(books.contains(book3));
	}

	@Test
	public void testSaveBooks() throws IOException {
		dao.loadBooks(TEST_DATABASE_TXT);
		Set<Book> expectedBooks = dao.getAllBooks();

		dao.saveBooks(SAVED_TEST_DATABASE_TXT);
		dao.loadBooks(SAVED_TEST_DATABASE_TXT);
		Set<Book> actualBooks = dao.getAllBooks();

		assertTrue(actualBooks.equals(expectedBooks));
	}

	@Test
	public void testDeleteBookByNotExistedISBN() {

		dao.loadBooks(TEST_DATABASE_TXT);

		String neededIsbn = "5555";

		assertFalse(dao.deleteBookByISBN(neededIsbn));
		assertTrue(dao.getAllBooks().size() == 3);
	}

	@Test
	public void testAddBookNotExistedISBN() throws BookWithSuchIsbnAlreadyExistsException {

		dao.loadBooks(TEST_DATABASE_TXT);
		Set<Book> loadedBooks = dao.getAllBooks();

		Book book1 = new Book("7777", "Kobzar", "Shevchenko", 2005);
		dao.addBook(book1);

		assertTrue(loadedBooks.contains(book1));
	}

	@Test(expected = BookWithSuchIsbnAlreadyExistsException.class)
	public void testAddBookExistedISBN() throws BookWithSuchIsbnAlreadyExistsException {

		dao.loadBooks(TEST_DATABASE_TXT);
		Book book1 = new Book("1111", "Kobzar", "Shevchenko", 2005);
		dao.addBook(book1);
	}

}
