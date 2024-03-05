package com.tag.backend.model;
import java.io.Serializable;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message implements Serializable
{
	private HttpStatus status;
	private String message;
}
