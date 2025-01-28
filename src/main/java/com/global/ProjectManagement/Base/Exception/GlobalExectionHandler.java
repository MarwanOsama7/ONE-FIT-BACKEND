package com.global.ProjectManagement.Base.Exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.global.ProjectManagement.Base.Exception.Entity.ErrorResponse;

@ControllerAdvice
public class GlobalExectionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> handleNotfound(RecordNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<?> AlreadyFriends(FileStorageException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(InvalidFileNameException.class)
	public ResponseEntity<?> AlreadyFriends(InvalidFileNameException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<?> AlreadyFriends(EmailException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<?> EmailNotFound(EmailNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(DuplicateProductException.class)
	public ResponseEntity<?> DuplicateProductException(DuplicateProductException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
}
