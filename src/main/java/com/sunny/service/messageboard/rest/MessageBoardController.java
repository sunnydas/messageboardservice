package com.sunny.service.messageboard.rest;

import com.sunny.service.messageboard.exception.ClientValidationException;
import com.sunny.service.messageboard.exception.ResourceNotFoundException;
import com.sunny.service.messageboard.repository.domain.MessageDTO;
import com.sunny.service.messageboard.rest.domain.MessagePayLoad;
import com.sunny.service.messageboard.service.MessageBoardRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/messageboard/messages")
/*
  This class exposes the REST functionality for the message board service.
 */
class MessageBoardController {

    @Autowired
    private MessageBoardRepoService messageBoardRepoService;

    @RequestMapping(value = "/{clientId}",
            method = RequestMethod.POST)
    public ResponseEntity<MessageDTO> createMessage(@PathVariable("clientId") long clientId
            , @NotNull @RequestBody MessagePayLoad messagePayLoad) throws ClientValidationException {
        MessageDTO message = messageBoardRepoService.createMessage(messagePayLoad,clientId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/{clientId}/{messageId}",
    method = RequestMethod.PUT)
    public ResponseEntity<MessageDTO> updateMessage(@NotNull @PathVariable("clientId") long clientId,
                                                    @NotNull @PathVariable("messageId") long messageId,
                                                    @NotNull @RequestBody MessagePayLoad messagePayLoad) throws ClientValidationException, ResourceNotFoundException {
        MessageDTO message = messageBoardRepoService.updateMessage(messagePayLoad,clientId,messageId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @RequestMapping(value = "/{clientId}/{messageId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMessage(@NotNull @PathVariable("clientId") long clientId,
                                                 @NotNull @PathVariable("messageId") long messageId) throws ClientValidationException, ResourceNotFoundException {
        messageBoardRepoService.deleteMessage(clientId,messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<MessageDTO>> findAllMessages(){
        Iterable<MessageDTO> messages = messageBoardRepoService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}