package com.sunny.service.messageboard.repository.util;

import com.sunny.service.messageboard.exception.ClientValidationException;
import com.sunny.service.messageboard.exception.ClientValidationExceptionType;
import com.sunny.service.messageboard.repository.domain.ClientDTO;
import com.sunny.service.messageboard.repository.domain.MessageDTO;
import org.junit.Assert;
import org.junit.Test;

public class ValidationUtilsTest {

    private final long MESSAGE_ID = System.currentTimeMillis();
    private final long CLIENT_ID = System.currentTimeMillis();
    private final String MESSAGE_TEXT = "Hello World";
    private final String MESSAGE_TITLE = "Hi";
    private final String MESSAGE_TEXT_LONG = "Hello World jdqjdiijpakdpoakdposakdpakpc,asc,acl[psal[p" +
            "saod[psaod[psaod[psa" +
            "od[psaod[psald[psaod[psaod[psaod[psaod[psao[pdosa[pdo" +
            "dk;lsadksakdsakdakd;ak;lkdak;lkda" +
            " akd;lkd;lad akldkapiwqeiqr9fpofd;wxqji9wqfe[qfkq,9284rklk;k kaskdkd;la" +
            "adlsaldsalda ldkadak;ldak;ldkaslkda alsdkslakd";
    private final String MESSAGE_TITLE_TOO_LONG = "dasdlksajdsajdsajdkaj ajdlksajdsajdsa lajdlksajdksajd djalks";
    private final String CLIENT_NAME = "test client";
    private final String CLIENT_DESCRIPTION = "test client description";
    private final String CLIENT_NAME_LONG = "Hello World jdqjdiijpakdpoakdposakdpakpc,asc,acl[psal[p" +
            "saod[psaod[psaod[psa" +
            "od[psaod[psald[psaod[psaod[psaod[psaod[psao[pdosa[pdo" +
            "dk;lsadksakdsakdakd;ak;lkdak;lkda" +
            " akd;lkd;lad akldkapiwqeiqr9fpofd;wxqji9wqfe[qfkq,9284rklk;k kaskdkd;la" +
            "adlsaldsalda ldkadak;ldak;ldkaslkda alsdkslakd";
    private final String CLIENT_DESCRIPTION_LONG = "dasdlksajdsajdsajdkaj ajdlksajdsajdsa lajdlksajdksajd djalks";

    @Test
    public void testValidateMessageNormal() throws ClientValidationException {
        MessageDTO messageDTO = getValidMessageDTOProperTextAndTitle();
        ValidationUtils.validateMessageDetails(messageDTO);
    }

    @Test
    public void testValidateMessageTextTooLong() {
        MessageDTO messageDTO = getInValidMessageDTOTextTooLong();
        try{
            ValidationUtils.validateMessageDetails(messageDTO);
        }catch (ClientValidationException e){
            Assert.assertEquals(e.getExceptionType(), ClientValidationExceptionType.MESSAGE_TEXT_MAX_LENGTH_EXCEEDED);
        }
    }

    @Test
    public void testValidateMessageTextTitleTooLong(){
        MessageDTO messageDTO = getInValidMessageDTOTitleTooLong();
        try{
            ValidationUtils.validateMessageDetails(messageDTO);
        }catch (ClientValidationException e){
            Assert.assertEquals(e.getExceptionType(), ClientValidationExceptionType.MESSAGE_TITLE_MAX_LENGTH_EXCEEDED);
        }
    }

    @Test
    public void testValidateMessageValidClientOwnership() throws ClientValidationException {
        MessageDTO messageDTO = getValidMessageDTOProperTextAndTitle();
        ValidationUtils.validateMessage(messageDTO,messageDTO.getClientId(),messageDTO.getMessageId());
    }

    @Test
    public void testValidateMessageInValidClientOwnership() {
        MessageDTO messageDTO = getValidMessageDTOProperTextAndTitle();
        long someOtherClientId = System.currentTimeMillis();
        try{
            ValidationUtils.validateMessage(messageDTO,someOtherClientId,messageDTO.getMessageId());
        }catch (ClientValidationException e){
            Assert.assertEquals(e.getExceptionType(), ClientValidationExceptionType.
                    CLIENT_NOT_OWNER_OF_MESSAGE);
        }
    }

    @Test
    public void testValidateClientDetailsValidNameAndDescription() throws ClientValidationException {
        ClientDTO clientDTO = getClientDTO();
        setClientName(clientDTO,CLIENT_NAME);
        setClientDescription(clientDTO);
        ValidationUtils.validatedClientDetails(clientDTO);
    }

    @Test
    public void testValidateClientDetailsInValidName() {
        ClientDTO clientDTO = getClientDTO();
        setClientName(clientDTO,CLIENT_NAME_LONG);
        setClientDescription(clientDTO);
        try{
            ValidationUtils.validatedClientDetails(clientDTO);
        }catch (ClientValidationException e){
            Assert.assertEquals(e.getExceptionType(), ClientValidationExceptionType.
                    CLIENT_NAME_LENGTH_EXCEEDED);
        }
    }

    @Test
    public void testValidateClientDetailsInValidDescription() {
        ClientDTO clientDTO = getClientDTO();
        setClientName(clientDTO,CLIENT_NAME);
        setClientDescription(clientDTO);
        try{
            ValidationUtils.validatedClientDetails(clientDTO);
        }catch (ClientValidationException e){
            Assert.assertEquals(e.getExceptionType(), ClientValidationExceptionType.
                    CLIENT_DESCRIPTION_TEXT_TOO_LONG);
        }
    }

    @Test
    public void testValidateClientDetailsMissingClientName() {
        ClientDTO clientDTO = getClientDTO();
        setClientDescription(clientDTO);
        try{
            ValidationUtils.validatedClientDetails(clientDTO);
        }catch (ClientValidationException e){
            Assert.assertEquals(e.getExceptionType(), ClientValidationExceptionType.
                    CLIENT_NAME_EMPTY);
        }
    }

    private MessageDTO getValidMessageDTOProperTextAndTitle(){
        MessageDTO messageDTO = getMessageDTO();
        setMessageText(messageDTO);
        setMessageText(messageDTO, MESSAGE_TEXT);
        return messageDTO;
    }

    private void setMessageText(MessageDTO messageDTO) {
        setMessageTitle(messageDTO, MESSAGE_TITLE);
    }

    private MessageDTO getInValidMessageDTOTextTooLong(){
        MessageDTO messageDTO = getMessageDTO();
        setMessageText(messageDTO, MESSAGE_TEXT_LONG);
        setMessageTitle(messageDTO, MESSAGE_TITLE);
        return messageDTO;
    }

    private MessageDTO getMessageDTO() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(MESSAGE_ID);
        messageDTO.setClientId(CLIENT_ID);
        return messageDTO;
    }

    private void setMessageText(MessageDTO messageDTO, String message_text) {
        messageDTO.setMessageText(message_text);
    }

    private MessageDTO getInValidMessageDTOTitleTooLong(){
        MessageDTO messageDTO = getMessageDTO();
        setMessageText(messageDTO, MESSAGE_TEXT);
        setMessageTitle(messageDTO, MESSAGE_TITLE_TOO_LONG);
        return messageDTO;
    }

    private void setMessageTitle(MessageDTO messageDTO, String message_title) {
        messageDTO.setMessageTitle(message_title);
    }

    private ClientDTO getClientDTO(){
        return new ClientDTO();
    }

    private void setClientDescription(ClientDTO clientDTO) {
        clientDTO.setClientDescription(CLIENT_DESCRIPTION);
    }

    private void setClientName(ClientDTO clientDTO,String clientName) {
        clientDTO.setClientName(clientName);
    }

}