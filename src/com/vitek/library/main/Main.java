package com.vitek.library.main;

import java.io.InputStreamReader;
import java.util.Scanner;

import com.vitek.library.dao.BookWithSuchIsbnAlreadyExistsException;
import com.vitek.library.entity.Book;
import com.vitek.library.service.BookFormatException;
import com.vitek.library.service.BookService;
import com.vitek.library.service.Command;
import com.vitek.library.service.CommandsService;

public class Main {
	public static final String STOP_WORD = "exit";

	public static <T> void main(String[] args) {

		CommandsService commandService = new CommandsService();
		BookService bookService = new BookService();

		Scanner userInputReader = new Scanner(new InputStreamReader(System.in));
		userInputReader.useDelimiter("\n");
		System.out.println("Write a command");
		System.out.println("To find a book by ISBN command format is: \"find ISBN\" ");
		System.out.println("To add a book by ISBN command format is: \"add ISBN|book name|author|year\" ");
		System.out.println("To delete a book by ISBN command format is: \"delete ISBN\" ");
		System.out.println("To update a book command format is: \"update ISBN|book name|author|year\" ");

		System.out.println("Write \"" + STOP_WORD + "\" to exit");

		String input = null;

		do {
			input = userInputReader.next().trim();

			Command<Book> commandBook;
			Command<String> commandISBN;
			try {

				if ((commandBook = commandService.isAdd(input)) != null) {
					try {
						bookService.add(commandBook);
					} catch (BookWithSuchIsbnAlreadyExistsException e) {
						System.err.println(e.getMessage());
					}
				} else if ((commandBook = commandService.isUpdate(input)) != null) {
					bookService.update(commandBook);

				} else if ((commandISBN = commandService.isFind(input)) != null) {
					try {
						Book book = bookService.find(commandISBN);
						System.out.println(book.getIsbn() + " " + book.getName() + " " + book.getAuthor() + " " + book.getYear());
					} catch (NullPointerException e1) {
						throw new BookFormatException("There is no such book in the library");
					}
				} else if ((commandISBN = commandService.isDelete(input)) != null) {
					bookService.delete(commandISBN);
				} else if (!STOP_WORD.equals(input)) {
					System.err.println("Wrong command format!");
				}

			} catch (BookFormatException e) {
				System.err.println(e.getMessage());
			}

		} while (!STOP_WORD.equals(input));

		bookService.storeData();
		userInputReader.close();

		bookService.storeData();
		userInputReader.close();
	}
}