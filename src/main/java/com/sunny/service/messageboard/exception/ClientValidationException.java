package com.sunny.service.messageboard.exception;

/**
 * This class represents the various validation exceptions that can happen while processing request
 */
public class ClientValidationException extends Exception{

    public ClientValidationExceptionType getExceptionType() {
        return msgBoardServiceClientValidationExceptionType;
    }

    private ClientValidationExceptionType msgBoardServiceClientValidationExceptionType;


    public ClientValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientValidationException(String message, ClientValidationExceptionType msgBoardServiceClientValidationExceptionType) {
        super(message);
        this.msgBoardServiceClientValidationExceptionType = msgBoardServiceClientValidationExceptionType;
    }
}
