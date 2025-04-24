package com.user.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	 	@ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
	        Map<String, Object> errorDetails = new HashMap();
	        errorDetails.put("timestamp", new Date());
	        errorDetails.put("message", ex.getMessage());
	        errorDetails.put("details", request.getDescription(false));
	        
	        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
	        Map<String, Object> errorDetails = new HashMap<>();
	        errorDetails.put("timestamp", new Date());
	        errorDetails.put("message", ex.getMessage());
	        errorDetails.put("details", request.getDescription(false));
	        
	        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
