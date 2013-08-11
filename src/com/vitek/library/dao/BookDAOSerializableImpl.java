package com.vitek.library.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.vitek.library.entity.Book;

public class BookDAOSerializableImpl extends BookDAOImpl implements Serializable {
	private static final long serialVersionUID = 471812060744149374L;

	public static final String DATAFILE_NAME = "SerializedDatabase.txt";

	@Override
	public void loadData() {

		try {
			loadBooks(DATAFILE_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void storeData() {
		try {
			saveBooks(DATAFILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
			//
		}
	}

	// deserialization
	@SuppressWarnings("unchecked")
	void loadBooks(String fileName) throws ClassNotFoundException, IOException {

		File dataBaseFile = new File(fileName);

		if (dataBaseFile.exists()) {

			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			books = (Set<Book>) objectInputStream.readObject();

			objectInputStream.close();

		} else {
			books = new HashSet<Book>();
			System.out.println("Problem reading from file!");
			throw new ClassNotFoundException(fileName + " not found!");
		}
	}

	// serialization
	void saveBooks(String fileName) throws IOException {

		// create an object books
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		objectOutputStream.writeObject(books);

		objectOutputStream.flush();
		objectOutputStream.close();

	}
}
