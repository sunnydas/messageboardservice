package com.sunny.service.messageboard.exception;

/**
 * Current validation exception codes
 */
public enum ClientValidationExceptionType {
    CLIENT_NOT_OWNER_OF_MESSAGE,
    MESSAGE_NOT_FOUND,
    MESSAGE_TEXT_MAX_LENGTH_EXCEEDED,
    MESSAGE_TITLE_MAX_LENGTH_EXCEEDED,
    CLIENT_DESCRIPTION_TEXT_TOO_LONG,
    CLIENT_NAME_EMPTY,
    CLIENT_NAME_LENGTH_EXCEEDED
}
