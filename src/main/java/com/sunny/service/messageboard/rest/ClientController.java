package com.sunny.service.messageboard.rest;

import com.sunny.service.messageboard.exception.ClientValidationException;
import com.sunny.service.messageboard.repository.domain.ClientDTO;
import com.sunny.service.messageboard.repository.util.ValidationUtils;
import com.sunny.service.messageboard.service.MessageBoardRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/messageboard/clients")
/*
  This class represents the controller, responsible for creating clients.
 */
class ClientController {

    @Autowired
    private MessageBoardRepoService messageBoardRepoService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ClientDTO> addClient(@RequestBody @NotNull
                                                       ClientDTO messageBoardClient) throws ClientValidationException {
        ValidationUtils.validatedClientDetails(messageBoardClient);
        messageBoardClient = messageBoardRepoService.addMessageBoardClient(messageBoardClient);
        return new ResponseEntity(messageBoardClient,HttpStatus.CREATED);
    }
}
