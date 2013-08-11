package com.vitek.library.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vitek.library.dao.file.BookParser;
import com.vitek.library.entity.Book;

public class BookParserTest {

	@Test
	public void testParseValidInput() {
		Book actual = BookParser.parse("1234|Maugli|Kipling|1234");
		Book expected = new Book("1234", "Maugli", "Kipling", 1234);

		assertEquals("Objects should be equal!", expected, actual);
	}

	@Test
	public void testFetchParameterValueFromStringBookValidInput() {
		String actualIsbn = BookParser.parseIsbn("1234|Maugli|Kipling|1234");
		String expectedIsbn = "1234";
		assertEquals("Objects should be equal!", expectedIsbn, actualIsbn);

		String actualName = BookParser.parseName("1234|Maugli|Kipling|1234");
		String expectedName = "Maugli";
		assertEquals("Objects should be equal!", actualName, expectedName);

		String actualAuthor = BookParser.parseAuthor("1234|Maugli|Kipling|1234");
		String expectedAuthor = "Kipling";
		assertEquals("Objects should be equal!", actualAuthor, expectedAuthor);

		String actualYear = BookParser.parseYear("1234|Maugli|Kipling|1234");
		String expectedYear = "1234";
		assertEquals("Objects should be equal!", actualYear, expectedYear);
	}

	public void testFetchParameterValueFromStringBookInvalidInput() {
		String actualIsbn = BookParser.parseIsbn("1234 Maugli|Kipling|1234");
		assertTrue(actualIsbn == null);
	}

	public void testFetchParameterValueFromStringBookEmptyString() {
		String actualIsbn = BookParser.parseIsbn("");
		assertTrue(actualIsbn == null);
	}

	@Test
	public void testFormat() {
		String actual = BookParser.format(new Book("1234", "Maugli", "Kipling", 1234));
		String expected = "1234|Maugli|Kipling|1234";
		assertEquals("Objects should be equal!", expected, actual);
	}

}
