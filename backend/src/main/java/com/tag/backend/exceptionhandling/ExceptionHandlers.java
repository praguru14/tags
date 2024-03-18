package com.tag.backend.exceptionhandling;

import com.tag.backend.model.DataMessage;
import com.tag.backend.model.Message;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import com.google.api.client.http.HttpResponseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        List<Map<String, String>> errorList = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            Map<String, String> error = new HashMap<>();
            error.put("fieldName", violation.getPropertyPath().toString());
            error.put("errorMessage", violation.getMessage());
            errorList.add(error);
        }
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseEntity<?> genericException(RuntimeException exp) {
        return new ResponseEntity<Object>(new Message(HttpStatus.BAD_REQUEST, exp.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpResponseException.class, HttpClientErrorException.class})
    public ResponseEntity<?> genericException(Exception exp) {
        return new ResponseEntity<Object>(new Message(HttpStatus.BAD_REQUEST, exp.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<?> unAuthorized(RuntimeException exp) {
        return new ResponseEntity<Object>(new Message(HttpStatus.UNAUTHORIZED, exp.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DataMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "";
        int firstIndex = ex.getMessage().indexOf("'");
        int secondIndex = ex.getMessage().indexOf("'", firstIndex + 1);
        boolean duplicateEmail = ex.getMessage().substring(firstIndex + 1, ex.getMessage().lastIndexOf("'")).contains("@");
        boolean duplicatePhoneNo = ex.getMessage().substring(firstIndex + 1, secondIndex).matches("\\d+");

        if (duplicateEmail && duplicatePhoneNo) {
            message = "Email and Phone Number already exists";
        } else if (duplicatePhoneNo) {
            message = "Phone Number already exists";
        } else if (duplicateEmail) {
            message = "Email already exists";
        } else if (ex.getMessage().contains("Data too long")) {
            message = "Size of the data is too long";
        } else {
            message = "Data already exists";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new DataMessage(HttpStatus.CONFLICT, message));
    }
}
