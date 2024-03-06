package com.tag.backend.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class DataMessage implements Serializable {
    private HttpStatus status;
    private Object data;
    private String message;
    private String rideId;
    private Long totalResults;

    public DataMessage(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public DataMessage(HttpStatus status, Object data) {
        super();
        this.status = status;
        this.data = data;
    }

    public DataMessage(HttpStatus status, Object data, Long totalResults) {
        super();
        this.status = status;
        this.data = data;
        this.totalResults = totalResults;
    }

    public DataMessage(String message) {
        super();
        this.message = message;
    }

    public DataMessage() {
    }
}