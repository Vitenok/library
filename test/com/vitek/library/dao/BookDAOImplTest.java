package com.vitek.library.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.vitek.library.entity.Book;

public class BookDAOImplTest {

	BookDAOImpl dao = new BookDAOImpl() {
		@Override
		public void loadData() {
			books = new HashSet<Book>();
		}

		@Override
		public void storeData() {
		}
	};

	@Before
	public void before() {
		dao.loadData();
	}

	@Test
	public void testDeleteBookByISBN() throws BookWithSuchIsbnAlreadyExistsException {

		Book bookToDelete = new Book("1234", "Maugli1", "Kipling1", 2001);
		Book book2 = new Book("1235", "Maugli2", "Kipling2", 2002);
		Book book3 = new Book("1236", "Maugli3", "Kipling3", 2003);

		dao.addBook(bookToDelete);
		dao.addBook(book2);
		dao.addBook(book3);
		String neededIsbn = "1234";

		assertTrue(dao.deleteBookByISBN(neededIsbn));

		Set<Book> booksDeleted = dao.getAllBooks();

		assertFalse(booksDeleted.contains(bookToDelete));

	}

	@Test
	public void testFindBookByISBN() throws BookWithSuchIsbnAlreadyExistsException {
		Book book1 = new Book("1234", "Maugli1", "Kipling1", 2001);
		Book book2 = new Book("1235", "Maugli2", "Kipling2", 2002);
		Book book3 = new Book("1236", "Maugli3", "Kipling3", 2003);

		dao.addBook(book1);
		dao.addBook(book2);
		dao.addBook(book3);

		String neededISBN = "1234";
		Book bookFinded = dao.findBookByISBN(neededISBN);

		assertTrue(bookFinded.equals(book1));
	}

	@Test
	public void testFindBookByNotExistedISBN() throws BookWithSuchIsbnAlreadyExistsException {
		Book book1 = new Book("1234", "Maugli1", "Kipling1", 2001);
		Book book2 = new Book("1235", "Maugli2", "Kipling2", 2002);
		Book book3 = new Book("1236", "Maugli3", "Kipling3", 2003);

		dao.addBook(book1);
		dao.addBook(book2);
		dao.addBook(book3);

		String neededISBN = "5555";
		Book bookFinded = dao.findBookByISBN(neededISBN);

		assertTrue(bookFinded == null);

	}

	@Test
	public void testUpdateBookByISBN() throws BookWithSuchIsbnAlreadyExistsException {
		Book book1 = new Book("1234", "Maugli1", "Kipling1", 2001);
		Book book2 = new Book("1235", "Maugli2", "Kipling2", 2002);
		Book book3 = new Book("1236", "Maugli3", "Kipling3", 2003);
		Book bookToUpdate = new Book("1235", "Kobzar", "Shevchenko", 2005);

		dao.addBook(book1);
		dao.addBook(book2);
		dao.addBook(book3);

		dao.updateBookByISBN(bookToUpdate);

		assertTrue(dao.getAllBooks().contains(bookToUpdate));
	}

	@Test
	public void testUpdateBookByISBNNotExistedISBN() throws BookWithSuchIsbnAlreadyExistsException {

		Book book1 = new Book("1234", "Maugli1", "Kipling1", 2001);
		Book book2 = new Book("1235", "Maugli2", "Kipling2", 2002);
		Book book3 = new Book("1236", "Maugli3", "Kipling3", 2003);
		Book bookToUpdate = new Book("0000", "Kobzar", "Shevchenko", 2005);

		dao.addBook(book1);
		dao.addBook(book2);
		dao.addBook(book3);

		dao.updateBookByISBN(bookToUpdate);
		assertFalse(dao.getAllBooks().contains(bookToUpdate));

	}

}