package com.vitek.library.dao;

import java.util.Set;

import com.vitek.library.entity.Book;

public interface BookDAO {

	boolean addBook(Book book) throws BookWithSuchIsbnAlreadyExistsException;

	boolean updateBookByISBN(Book newBook);

	boolean deleteBookByISBN(String neededIsbn);

	Book findBookByISBN(String neededIsbn);

	Set<Book> getAllBooks();
	
	void loadData();

	void storeData();

}