package com.vitek.library.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vitek.library.entity.Book;

public class CommandsServiceTest {
	CommandsService cs = new CommandsService();

	@Test
	public void testIsAddValidInput() throws BookFormatException {

		String commandString = "add 1111|Kobzar|Shevchenko|2005";

		Command<Book> actual = cs.isAdd(commandString);
		Book bookToCheck = actual.getValue();
		Book book = new Book("1111", "Kobzar", "Shevchenko", 2005);

		assertEquals(book, bookToCheck);
	}

	@Test
	public void testIsAddNoAdd() throws BookFormatException {

		String commandString = "add1111|Kobzar|Shevchenko|2005";
		Command<Book> actual = cs.isAdd(commandString);
		assertTrue(actual == null);
	}

	@Test
	public void testIsFindValidInput() throws BookFormatException {

		String commandString = "find 1111";
		Command<String> actual = cs.isFind(commandString);
		String isbnToCheck = actual.getValue();
		String isbn = "1111";

		assertEquals(isbn, isbnToCheck);
	}

	@Test
	public void testIsFindNoFind() throws BookFormatException {

		String commandString = "find1111";
		Command<String> actual = cs.isFind(commandString);

		assertTrue(actual == null);
	}

	@Test(expected = BookFormatException.class)
	public void testFetchBookCommandFromStringNotEnoughElements() throws BookFormatException {

		cs.fetchBookCommandFromString("add book1|author1|2001");
	}

	@Test(expected = BookFormatException.class)
	public void testFetchBookCommandFromStringYearNotInRange() throws BookFormatException {

		cs.fetchBookCommandFromString("add 1111|Kobzar|Shevchenko|-2005");

		cs.fetchBookCommandFromString("add 1111|Kobzar|Shevchenko|3005");
	}

	@Test(expected = BookFormatException.class)
	public void testFetchBookCommandFromStringYearIsWord() throws BookFormatException {

		String commandString = "add 1111|Kobzar|Shevchenko|abc";
		cs.fetchBookCommandFromString(commandString);
	}

	@Test(expected = BookFormatException.class)
	public void testFetchStringCommandTooManyElements() throws BookFormatException {

		cs.fetchISBNCommandFromString("find 1111|1111");
	}

	@Test(expected = BookFormatException.class)
	public void testFetchStringCommandNotEnoughElements() throws BookFormatException {

		cs.fetchISBNCommandFromString("find ");
	}

	@Test(expected = BookFormatException.class)
	public void testFetchStringCommandISBNIsTooShort() throws BookFormatException {

		cs.fetchISBNCommandFromString("find 1");
	}

}
