package com.vitek.library.service;

public class Command<T> {

	private T t;

	public Command(T t) {
		this.t = t;
	}

	public T getValue() {
		return t;
	}

}