package com.marcelofs.jackson.erep;

public class ErepLoginException extends Exception {

	private static final long	serialVersionUID	= 1L;

	public ErepLoginException(String username) {
		super("Could not login " + username);
	}

}
