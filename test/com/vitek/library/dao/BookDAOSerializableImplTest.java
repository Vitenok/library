package com.vitek.library.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import org.junit.Test;

import com.vitek.library.entity.Book;

public class BookDAOSerializableImplTest {

	public static final String TEST_DATAFILE_NAME = "SerializedDatabaseTest.txt";
	private static final String SAVED_TEST_DATAFILE_NAME = "SavedSerializedDatabaseTest.txt";

	BookDAOSerializableImpl dao = new BookDAOSerializableImpl();

	@Test
	public void testBookDAOSerializableIsSerializable() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(TEST_DATAFILE_NAME);
		oos.close();
		assertTrue(baos.toByteArray().length > 0);
	}

	@Test
	public void testLoadAndStoreData() throws IOException, ClassNotFoundException {
		Book book01 = new Book("0001", "name01", "author01", 2000);
		Book book02 = new Book("00002", "name02", "author02", 2000);

		HashSet<Book> orginalBbooks = new HashSet<Book>();
		orginalBbooks.add(book01);
		orginalBbooks.add(book02);

		// serialization
		FileOutputStream fos = new FileOutputStream(SAVED_TEST_DATAFILE_NAME);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(orginalBbooks);
		oos.close();

		// deserialization
		FileInputStream fis = new FileInputStream(SAVED_TEST_DATAFILE_NAME);
		ObjectInputStream ois = new ObjectInputStream(fis);

		@SuppressWarnings("unchecked")
		HashSet<Book> copyBooks = (HashSet<Book>) ois.readObject();
		ois.close();

		assertEquals(orginalBbooks, copyBooks);
	}

	@Test(expected = ClassNotFoundException.class)
	public void loadDataIOException() throws IOException, ClassNotFoundException {
		dao.loadBooks("blabla");
	}

}
