package com.admin.exception;

public class AdminAccessDeniedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminAccessDeniedException(String message) {
        super(message);
    }
}