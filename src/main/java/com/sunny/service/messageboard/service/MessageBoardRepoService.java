package com.sunny.service.messageboard.service;

import com.sunny.service.messageboard.exception.ClientValidationException;
import com.sunny.service.messageboard.exception.ClientValidationExceptionType;
import com.sunny.service.messageboard.exception.ResourceNotFoundException;
import com.sunny.service.messageboard.repository.ClientRepo;
import com.sunny.service.messageboard.repository.MessageBoardRepo;
import com.sunny.service.messageboard.repository.domain.ClientDTO;
import com.sunny.service.messageboard.repository.domain.MessageDTO;
import com.sunny.service.messageboard.repository.util.RepositoryUtils;
import com.sunny.service.messageboard.repository.util.ValidationUtils;
import com.sunny.service.messageboard.rest.domain.MessagePayLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
/*
  This class represents the service layer to the DB back end
 */
public class MessageBoardRepoService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private MessageBoardRepo messageBoardRepo;

    public ClientDTO addMessageBoardClient(ClientDTO messageBoardClient){
        Date currentDate = RepositoryUtils.getDate();
        messageBoardClient.setCreatedDate(currentDate);
        messageBoardClient.setUpdateDate(currentDate);
        messageBoardClient = clientRepo.save(messageBoardClient);
        return messageBoardClient;
    }


    public MessageDTO createMessage(MessagePayLoad messagePayLoad, long clientId) throws ClientValidationException {
        MessageDTO message = RepositoryUtils.createMessage(messagePayLoad,clientId);
        ValidationUtils.validateMessageDetails(message);
        return messageBoardRepo.save(message);
    }

    public MessageDTO updateMessage(MessagePayLoad messagePayLoad, long clientId, long messageId) throws ClientValidationException,
            ResourceNotFoundException {
        Optional<MessageDTO> optional = messageBoardRepo.findById(messageId);
        MessageDTO message = null;
        if(optional.isPresent()){
            message = optional.get();
            message.setMessageText(messagePayLoad.getMessageText());
            message.setMessageTitle(messagePayLoad.getMessageTitle());
            RepositoryUtils.updateMessageTimeStamps(message);
            ValidationUtils.validateMessage(message,clientId,messageId);
            message =  messageBoardRepo.save(message);
        }else{
             handleMissingMessageRecord(messageId);
        }
        return message;
    }

    private static void handleMissingMessageRecord(long messageId) throws ResourceNotFoundException {
        String error = String.format("MessageDTO not found for message id %s",messageId);
        throw new ResourceNotFoundException(error, ClientValidationExceptionType.MESSAGE_NOT_FOUND);
    }

    public void deleteMessage(long clientId,long messageId) throws ClientValidationException, ResourceNotFoundException {
        Optional<MessageDTO> optional = messageBoardRepo.findById(messageId);
        if(optional.isPresent()){
            MessageDTO message = optional.get();
            ValidationUtils.validateOwnerOfMessage(clientId,messageId,message);
            deleteMessage(messageId);
        }else{
            handleMissingMessageRecord(messageId);
        }
    }

    private void deleteMessage(long messageId){
        messageBoardRepo.deleteById(messageId);
    }

    public Iterable<MessageDTO> getAllMessages(){
        return messageBoardRepo.findAll();
    }
}