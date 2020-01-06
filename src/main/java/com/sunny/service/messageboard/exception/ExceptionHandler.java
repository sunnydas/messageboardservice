package com.sunny.service.messageboard.exception;


import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@EnableWebMvc
/*
  This class handles some of the exceptions that might originate from the service
 */ class ExceptionHandler extends ResponseEntityExceptionHandler {


    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);


    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolations(
            ConstraintViolationException ex) {
        logger.error(ex.getMessage(),ex);
        APIError apiError = new APIError(BAD_REQUEST);
        apiError.setMessage(ExceptionMessages.BUSINESS_CONSTRAINTS_VIOLATED);
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ClientValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(
            ClientValidationException ex) {
        logger.error(ex.getMessage(),ex);
        APIError apiError = new APIError(BAD_REQUEST);
        apiError.setMessage(String.format(ExceptionMessages.VALIDATION_ERROR_BASE_TEXT,
                ex.getExceptionType()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        logger.error(ex.getMessage(),ex);
        APIError apiError = new APIError(NOT_FOUND);
        apiError.setMessage(String.format(ExceptionMessages.RESOURCE_NOT_FOUND,
                ex.getMsgBoardServiceClientValidationExceptionType()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(APIError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}