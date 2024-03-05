package com.tag.backend.exceptionhandling;

public class UnAuthorizedException extends RuntimeException
{
	private String message;

	public UnAuthorizedException(String message)
	{
		super(message);
		this.message = message;
	}

	public UnAuthorizedException() {
		super();
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
