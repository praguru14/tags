package com.tag.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeolocationPositionError {
    private int code;
    private String message;

    // Constructor with @JsonCreator annotation to deserialize from JSON string
    @JsonCreator
    public GeolocationPositionError(@JsonProperty("code") int code, @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Override toString() method for printing the object
    @Override
    public String toString() {
        return "GeolocationPositionError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
