package com.vitek.library.dao;

public class BookWithSuchIsbnAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 151981239530519415L;
	public BookWithSuchIsbnAlreadyExistsException() {
		super();
	}

	public BookWithSuchIsbnAlreadyExistsException(String message) {
		super(message);

	}

}
