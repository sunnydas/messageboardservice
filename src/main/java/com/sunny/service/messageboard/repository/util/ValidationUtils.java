package com.sunny.service.messageboard.repository.util;

import com.sunny.service.messageboard.exception.ClientValidationException;
import com.sunny.service.messageboard.exception.ClientValidationExceptionType;
import com.sunny.service.messageboard.repository.domain.ClientDTO;
import com.sunny.service.messageboard.repository.domain.MessageDTO;

public class ValidationUtils {

    public static void validateMessage(MessageDTO message, long clientId, long messageId) throws ClientValidationException {
        validateMessageDetails(clientId, messageId, message);
    }

    private static void validateMessageDetails(long clientId, long messageId, MessageDTO message) throws ClientValidationException {
        validateOwnerOfMessage(clientId, messageId, message);
        validateMessageDetails(message);
    }

    public static void validateOwnerOfMessage(long clientId, long messageId, MessageDTO message) throws ClientValidationException {
        String error;
        if(message.getClientId() != clientId){
            error = String.format("Client %1$s not not owner of message id %2$s",clientId,
                    messageId);
            throw new ClientValidationException(error, ClientValidationExceptionType.CLIENT_NOT_OWNER_OF_MESSAGE);
        }
    }

    public static void validateMessageDetails(MessageDTO message) throws ClientValidationException {
        validateMessageText(message);
        validateMessageTitle(message);
    }

    private static void validateMessageText(MessageDTO message) throws ClientValidationException {
        String error = null;
        String messageText = message.getMessageText();
        if(messageText != null && messageText.length() > 250){
            error = "Message text needs to be less than or equal to 250 characters.";
            throw new ClientValidationException(error,
                    ClientValidationExceptionType.MESSAGE_TEXT_MAX_LENGTH_EXCEEDED);
        }
    }

    private static void validateMessageTitle(MessageDTO message) throws ClientValidationException {
        String error = null;
        String messageTitle = message.getMessageTitle();
        if(messageTitle != null && messageTitle.length() > 50){
            error = "Message title text needs to be less than or equal to 50 characters.";
            throw new ClientValidationException(error,
                    ClientValidationExceptionType.MESSAGE_TITLE_MAX_LENGTH_EXCEEDED);
        }
    }

    public static void validatedClientDetails(ClientDTO clientDTO) throws ClientValidationException {
        validateClientName(clientDTO);
        validateClientDescription(clientDTO);
    }

    private static void validateClientName(ClientDTO clientDTO) throws ClientValidationException {
        String clientName = clientDTO.getClientName();
        String errorMessage = null;
        if(clientName ==  null || clientName.trim().equals("")){
            errorMessage = "Client name cannot be null or empty";
            throw new ClientValidationException(errorMessage,
                    ClientValidationExceptionType.CLIENT_NAME_EMPTY);
        }
        else if(clientName.length() > 50){
            errorMessage = "Client name cannot be more than 50 characters";
            throw new ClientValidationException(errorMessage,
                    ClientValidationExceptionType.CLIENT_NAME_LENGTH_EXCEEDED);
        }
    }

    private static void validateClientDescription(ClientDTO clientDTO) throws ClientValidationException {
        String clientDescription = clientDTO.getClientDescription();
        String errorMessage = null;
        if(clientDescription != null && clientDescription.length() > 50){
            errorMessage = "Client name cannot be more than 50 characters.";
            throw new ClientValidationException(errorMessage,
                    ClientValidationExceptionType.CLIENT_DESCRIPTION_TEXT_TOO_LONG);
        }
    }

}