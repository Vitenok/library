package com.vitek.library.dao.file;

import com.vitek.library.entity.Book;

public class BookParser {

	public static Book parse(String stringBook) {
		Book book = new Book();
		book.setIsbn(parseIsbn(stringBook));
		book.setName(parseName(stringBook));
		book.setAuthor(parseAuthor(stringBook));
		book.setYear(Integer.valueOf(parseYear(stringBook)));
		return book;
	}

	public static String format(Book book) {
		return book.getIsbn() + "|" + book.getName() + "|" + book.getAuthor() + "|" + book.getYear();
	}

	static String[] fetchParameterValueFromStringBook(String stringBook) {

		String[] stringBookArray = stringBook.split("\\|");
		int numberOfArrayElemennts = stringBookArray.length;
		if (numberOfArrayElemennts == 4) {
			return stringBookArray;
		}
		return null;
	}

	public static String parseIsbn(String stringBook) {
		String[] fetchName = fetchParameterValueFromStringBook(stringBook);
		String isbn = fetchName[0];
		return isbn;
	}

	public static String parseName(String stringBook) {
		String[] fetchName = fetchParameterValueFromStringBook(stringBook);
		String name = fetchName[1];
		return name;
	}

	public static String parseAuthor(String stringBook) {
		String[] fetchName = fetchParameterValueFromStringBook(stringBook);
		String author = fetchName[2];
		return author;
	}

	public static String parseYear(String stringBook) {
		String[] fetchName = fetchParameterValueFromStringBook(stringBook);
		String year = fetchName[3];
		return year;
	}

}
