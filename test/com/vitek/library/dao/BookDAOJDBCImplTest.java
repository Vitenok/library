package com.vitek.library.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vitek.library.entity.Book;

public class BookDAOJDBCImplTest {

	private BookDAO dao = new BookDAOJDBCImpl();

	@Before
	public void setUpBeforeClass() throws Exception {
		dao.loadData();
	}

	@After
	public void tearDownAfterClass() throws Exception {
		dao.storeData();
	}

	@Test
	public void testAddBook() throws BookWithSuchIsbnAlreadyExistsException {
		System.out.println("add");
		Book book = new Book("1", "", "", 2012);
		dao.addBook(book);
	}

	@Test
	public void testUpdateBookByISBN() {
		System.out.println("Update");
		Book book1 = new Book("10", "testn1", "testa1", 1999);
		dao.updateBookByISBN(book1);
	}

	@Test
	public void testDeleteBookByISBN() {
		System.out.println("delete");
		System.out.println(dao.deleteBookByISBN("8"));
	}

	@Test
	public void testFindBookByISBN() {
		System.out.println("find");
		System.out.println(dao.findBookByISBN("5"));
	}

	@Test
	public void testGetAllBooks() {
		System.out.println("GetAllBooks");
		System.out.println(dao.getAllBooks());
	}
	
	
}
