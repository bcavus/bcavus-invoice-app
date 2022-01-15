package com.bcavus.invoiceapp.invoiceservice.exception;

import com.bcavus.invoiceapp.invoiceservice.dto.request.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler({
            IllegalArgumentException.class,
            NoInvoiceFoundException.class
    })
    public ResponseEntity<APIResponse> handleRuntimeServiceExceptions(RuntimeException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        final APIResponse response = APIResponse.builder()
                .code(status.value())
                .status(status.name())
                .exception(this.getExceptionModel(status,exception.getMessage(),this.getExceptionStackTrace(exception)))
                .success(false)
                .build();

        logger.error("Runtime Exception:" + exception.getMessage());

        return ResponseEntity.status(status).body(response);
    }

    private ServiceException getExceptionModel(HttpStatus status, String exceptionMessage, String stackTrace) {
        return new ServiceException(status,exceptionMessage,stackTrace);
    }

    private String getExceptionStackTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}