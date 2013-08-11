package com.vitek.library.service;

import com.vitek.library.entity.Book;

public class CommandsService {

	public static String ADD = "add";// add 'isbn' 'name' 'author' 'year'
	public static String FIND_BY_ISBN = "find";// find 'isbn'
	public static String UPDATE_BY_ISBN = "update";// update 'isbn' 'name' 'author' 'year'
	public static String DELETE_BY_ISBN = "delete";// delete 'isbn'
	public static String COMMAND_PARAMEERS_DELIMETER = "\\|";

	Command<Book> fetchBookCommandFromString(String commandString) throws BookFormatException {

		String userInput = commandString.substring(commandString.indexOf(" ") + 1);

		String[] userInputArray = userInput.split(COMMAND_PARAMEERS_DELIMETER);

		int numberOfArrayElements = userInputArray.length;

		if (numberOfArrayElements == 4) {

			String stringYear = userInputArray[3];
			int stringYearLength = stringYear.length();
			int year = 0;
			try {
				year = Integer.parseInt(stringYear);
			} catch (NumberFormatException e1) {
				throw new BookFormatException("Year is not a number!");
			}
			if (stringYearLength == 4) {

				if (year > 0 && year < 2200) {

					Book book = new Book();
					book.setIsbn(userInputArray[0]);
					book.setName(userInputArray[1]);
					book.setAuthor(userInputArray[2]);
					book.setYear(year);

					Command<Book> command = new Command<Book>(book);

					// Command command = new Command();
					// command.setBookValue(book);

					return command;
				} else {
					throw new BookFormatException("Year is not in range!");
				}
			}
		}
		throw new BookFormatException("Amount of book fields is wrong!");
	}

	Command<String> fetchISBNCommandFromString(String commandString) throws BookFormatException {

		String one = commandString.substring(commandString.indexOf(" ") + 1);
		String[] userInputArray = one.split(COMMAND_PARAMEERS_DELIMETER);

		if (userInputArray.length == 1) {
			String isbn = userInputArray[0];
			if (isbn.length() > 1) {

				Command<String> command = new Command<String>(isbn);
				// Command command = new Command();
				// command.setStringValue(isbn);
				return command;

			} else {
				throw new BookFormatException("ISBN is too short!");
			}
		}
		throw new BookFormatException("Amount of fields is wrong! Command format is: command ISBN.");

	}

	public Command<Book> isAdd(String commandString) throws BookFormatException {

		String add = commandString.substring(0, commandString.indexOf(" ") + 1);

		if ("add ".equals(add)) {
			return fetchBookCommandFromString(commandString);
		}
		return null;
	}

	public Command<String> isFind(String commandString) throws BookFormatException {

		String find = commandString.substring(0, commandString.indexOf(" ") + 1);

		if (find.equals("find ")) {
			return fetchISBNCommandFromString(commandString);
		}

		return null;
	}

	public Command<Book> isUpdate(String commandString) throws BookFormatException {

		String update = commandString.substring(0, commandString.indexOf(" ") + 1);

		if ("update ".equals(update)) {
			return fetchBookCommandFromString(commandString);
		}
		return null;
	}

	public Command<String> isDelete(String commandString) throws BookFormatException {

		String delete = commandString.substring(0, commandString.indexOf(" ") + 1);
		if (delete.equals("delete ")) {
			return fetchISBNCommandFromString(commandString);
		}
		return null;
	}

}
