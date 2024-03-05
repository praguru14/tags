package com.tag.backend.exceptionhandling;

public class InvalidDataException extends RuntimeException
{
	private String message;

	public InvalidDataException(String message)
	{
		super(message);
		this.message = message;
	}

	public InvalidDataException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
