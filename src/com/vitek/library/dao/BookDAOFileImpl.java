package com.vitek.library.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.vitek.library.dao.file.BookParser;
import com.vitek.library.entity.Book;

public class BookDAOFileImpl extends BookDAOImpl {

	public static final String DATAFILE_NAME = "Database.txt";

	@Override
	public void storeData() {
		saveBooks(DATAFILE_NAME);
	}

	@Override
	public void loadData() {
		loadBooks(DATAFILE_NAME);
	}

	/**
	 * override data in file DATAFILE_NAME Next format is used for storing: isbn="BOOK_ISBN"name="BOOK_NAME"author="BOOK_AUTHOR"year="BOOK_YEAR"
	 **/
	void saveBooks(String fileName) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
			try {
				writer.write(BookParser.format(iterator.next()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (iterator.hasNext()) {
				try {
					writer.write("\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * if DATAFILE_NAME exists - read data and return set else - return empty set
	 * 
	 * @return
	 */
	void loadBooks(String fileName) {

		File dataBaseFile = new File(fileName);
		if (dataBaseFile.exists()) {
			try {
				books = readAndTransformIntoBooks(fileName);
			} catch (IOException e) {
				books = new HashSet<Book>();
				System.err.println("Problem reading from file");
				e.printStackTrace();
			}
		} else {
			books = new HashSet<Book>();
		}
	}

	public Set<Book> readAndTransformIntoBooks(String fileName) throws IOException {

		Set<Book> books = new HashSet<Book>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		String stringBook;

		while ((stringBook = reader.readLine()) != null) {

			int len = stringBook.trim().length();

			if (len > 0) {
				books.add(BookParser.parse(stringBook));
			}
		}
		reader.close();
		return books;
	}

}
