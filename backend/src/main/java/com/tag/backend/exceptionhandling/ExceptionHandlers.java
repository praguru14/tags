package com.tag.backend.exceptionhandling;
import com.tag.backend.model.Message;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import com.google.api.client.http.HttpResponseException;

@RestControllerAdvice
public class ExceptionHandlers
{
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<?> constraintViolation(RuntimeException exp)
	{
		ConstraintViolationException consEx = (ConstraintViolationException) exp;
		String errors = "";
		int count = 1;
		for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
			errors = errors + " Field(" + count + "): " + violation.getMessage();
			count++;
		}
		return new ResponseEntity<Object>(new Message(HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { InvalidDataException.class })
	public ResponseEntity<?> genericException(RuntimeException exp)
	{
		return new ResponseEntity<Object>(new Message(HttpStatus.BAD_REQUEST, exp.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { HttpResponseException.class,HttpClientErrorException.class })
	public ResponseEntity<?> genericException(Exception exp)
	{
		return new ResponseEntity<Object>(new Message(HttpStatus.BAD_REQUEST, exp.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UnAuthorizedException.class)
	public ResponseEntity<?> unAuthorized(RuntimeException exp)
	{
		return new ResponseEntity<Object>(new Message(HttpStatus.UNAUTHORIZED, exp.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}
}
