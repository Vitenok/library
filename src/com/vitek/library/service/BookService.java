package com.vitek.library.service;

import com.vitek.library.dao.BookDAO;
import com.vitek.library.dao.BookDAOFileImpl;
import com.vitek.library.dao.BookWithSuchIsbnAlreadyExistsException;
import com.vitek.library.entity.Book;

public class BookService {

	private BookDAO dao = new BookDAOFileImpl();
	
	public BookService() {
		dao.loadData();
	}

	public boolean add(Command<Book> command) throws BookWithSuchIsbnAlreadyExistsException {

		Book book = command.getValue();
		dao.addBook(book);
		return true;
	}

	public Book find(Command<String> command) {

		String isbn = command.getValue();
		Book book = dao.findBookByISBN(isbn);
		return book;
	}

	public boolean update(Command<Book> command) {

		Book book = command.getValue();
		dao.updateBookByISBN(book);
		return true;

	}

	public boolean delete(Command<String> command) {

		String neededIsbn = command.getValue();
		dao.deleteBookByISBN(neededIsbn);
		return true;
	}

	public void storeData() {
		dao.storeData();
	}

}
