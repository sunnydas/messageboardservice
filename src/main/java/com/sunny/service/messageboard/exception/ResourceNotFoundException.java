package com.sunny.service.messageboard.exception;

/**
 * This class represents an exception scenario where a particular resource (read message) is not found
 */
public class ResourceNotFoundException extends Exception{

    public ClientValidationExceptionType getMsgBoardServiceClientValidationExceptionType() {
        return msgBoardServiceClientValidationExceptionType;
    }

    private ClientValidationExceptionType msgBoardServiceClientValidationExceptionType;


    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message, ClientValidationExceptionType msgBoardServiceClientValidationExceptionType) {
        super(message);
        this.msgBoardServiceClientValidationExceptionType = msgBoardServiceClientValidationExceptionType;
    }
}