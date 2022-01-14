package com.bcavus.invoiceapp.invoiceservice.exception;

import com.bcavus.invoiceapp.invoiceservice.util.DateOperations;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ServiceException {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateOperations.DATE_TIME_PATTERN, timezone = DateOperations.DEFAULT_ZONE_ID)
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private Object data;

    private String stackTrace;
    
    public ServiceException() {
        timestamp = new Date();
    }

    public ServiceException(HttpStatus httpStatus, String message) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ServiceException(HttpStatus httpStatus, String message, String stackTrace) {
        this(httpStatus, message);

        this.stackTrace = stackTrace;
    }

    public ServiceException(HttpStatus httpStatus, String message, String stackTrace, Object data) {
        this(httpStatus, message, stackTrace);

        this.data = data;
    }
}