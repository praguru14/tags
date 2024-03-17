package com.tag.backend.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import org.springframework.http.HttpStatus;
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
    private String accessToken;


    public DataMessage(HttpStatus status, Object data, String message, String accessToken) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.accessToken = accessToken;
    }

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

    public static Builder builder() {
        return new Builder();
    }
    @Override
    public String toString() {
        return "DataMessage{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", rideId='" + rideId + '\'' +
                ", totalResults=" + totalResults +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }


    public static class Builder {
        private HttpStatus status;
        private Object data;
        private String message;
        private String rideId;
        private Long totalResults;
        private String accessToken;

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder rideId(String rideId) {
            this.rideId = rideId;
            return this;
        }

        public Builder totalResults(Long totalResults) {
            this.totalResults = totalResults;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public DataMessage build() {
            return new DataMessage(this.toString());
        }
    }
}
