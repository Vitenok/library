package com.vitek.library.dao;

import java.util.Iterator;
import java.util.Set;

import com.vitek.library.entity.Book;

public abstract class BookDAOImpl implements BookDAO {

	protected Set<Book> books;

	public BookDAOImpl() {
		super();
	}

	@Override
	public boolean addBook(Book newBook) throws BookWithSuchIsbnAlreadyExistsException {
		String neededBookISBN = newBook.getIsbn();
		Book bookToCheck = findBookByISBN(neededBookISBN);
		if (bookToCheck != null) {
			throw new BookWithSuchIsbnAlreadyExistsException("Book with this ISBN already exists in the library");
		}
		System.out.println("Book is successfully added");
		return books.add(newBook);
	
	}

	@Override
	public boolean updateBookByISBN(Book newBook) {
	
		if (deleteBookByISBN(newBook.getIsbn())) {
			System.out.println("Book is successfully updated");
			return books.add(newBook);
	
		}
		System.out.println("Book is not updated");
		return false;
	}

	@Override
	public boolean deleteBookByISBN(String neededIsbn) {
	
		Book bookToDelete = findBookByISBN(neededIsbn);
		if (bookToDelete != null) {
			System.out.println("Book is successfully deleted");
			return books.remove(bookToDelete);
		}
		System.out.println("There is no book with this ISBN in the library");
		return false;
	}

	@Override
	public Book findBookByISBN(String neededIsbn) {
	
		for (Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
			Book currentBook = iterator.next();
			String currentIsbn = currentBook.getIsbn();
			if (currentIsbn.equals(neededIsbn)) {
				return currentBook;
			}
		}
		return null;
	}

	@Override
	public Set<Book> getAllBooks() {
		return books;
	}

}