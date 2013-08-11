package com.vitek.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.vitek.library.entity.Book;

public class BookDAOJDBCImpl implements BookDAO {

	Connection connection;

	@Override
	public boolean addBook(Book book) throws BookWithSuchIsbnAlreadyExistsException {

		String isbn = book.getIsbn();
		String name = book.getName();
		String author = book.getAuthor();
		Integer year = book.getYear();
		String Year = year.toString();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into book values (?,?,?,?)");

			preparedStatement.setString(1, isbn);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, author);
			Date date = new SimpleDateFormat("yyyy").parse(Year);
			Timestamp timestamp = new Timestamp(date.getTime());
			preparedStatement.setTimestamp(4, timestamp);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException sqlexception) {
			if (sqlexception.getErrorCode() == 1062)
				System.out.println("Book with this ISBN already exists in the library");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateBookByISBN(Book newBook) {

		String ISBN = newBook.getIsbn();
		String name = newBook.getName();
		String author = newBook.getAuthor();
		Integer year = newBook.getYear();
		String Year = year.toString();

		try {
			Book bookToUpdate = findBookByISBN(ISBN);
			if (bookToUpdate != null) {
				PreparedStatement preparedStatement = connection.prepareStatement("update book set isbn= ?, name=?, author=?, date=? where isbn =" + ISBN);
				preparedStatement.setString(1, ISBN);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, author);
				Date date = new SimpleDateFormat("yyyy").parse(Year);
				Timestamp timestamp = new Timestamp(date.getTime());
				preparedStatement.setTimestamp(4, timestamp);
				preparedStatement.executeUpdate();

				System.out.println("Book is successfully updated");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteBookByISBN(String neededIsbn) {

		try {
			Book bookToDelete = findBookByISBN(neededIsbn);
			if (bookToDelete != null) {
				PreparedStatement preparedStatement = connection.prepareStatement("delete from book where isbn = ?;");
				preparedStatement.setString(1, neededIsbn);
				preparedStatement.executeUpdate();
				System.out.println("Book is successfully deleted");
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Book findBookByISBN(String neededIsbn) {

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from book where isbn =" + neededIsbn);

			while (resultSet.next()) {
				Integer isbn = resultSet.getInt("isbn");
				String name = resultSet.getString("name");
				String author = resultSet.getString("author");
				Timestamp timestamp = resultSet.getTimestamp("date");

				String ISBN = isbn.toString();
				String date = timestamp.toString();

				DateFormat df = new SimpleDateFormat("yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(date));
				int year = calendar.get(Calendar.YEAR);

				closeStatementAndResultset(statement, resultSet);

				return new Book(ISBN, name, author, year);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("There is no book with this ISBN in the library");
		return null;

	}

	private void closeStatementAndResultset(Statement statement, ResultSet resultSet) throws SQLException {
		resultSet.close();
		statement.close();
	}

	@Override
	public Set<Book> getAllBooks() {

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from book");
			Set<Book> books = new HashSet<Book>();

			while (resultSet.next()) {
				Integer isbn = resultSet.getInt("isbn");
				String name = resultSet.getString("name");
				String author = resultSet.getString("author");
				Timestamp timestamp = resultSet.getTimestamp("date");

				String ISBN = isbn.toString();
				String date = timestamp.toString();

				DateFormat df = new SimpleDateFormat("yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(df.parse(date));
				int year = calendar.get(Calendar.YEAR);

				Book book = new Book(ISBN, name, author, year);
				books.add(book);
			}
			closeStatementAndResultset(statement, resultSet);
			return books;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void loadData() {

		try {
			System.out.println("Initializing connection");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/simple_library", "root", "root");
			System.out.println("Initializing successfully finished");
		} catch ( SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}

	}

	@Override
	public void storeData() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
