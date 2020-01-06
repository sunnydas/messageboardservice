package com.sunny.service.messageboard.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.service.messageboard.repository.domain.ClientDTO;
import com.sunny.service.messageboard.repository.domain.ClientType;
import com.sunny.service.messageboard.repository.domain.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MessageBoardControllerTestUtils {

    private static final Logger logger = LoggerFactory.getLogger(MessageBoardControllerTestUtils.class);

    public static String getClientAsJsonString(String description,String clientName){
        ClientDTO messageBoardClient = new ClientDTO();
        messageBoardClient.setClientDescription(description);
        messageBoardClient.setClientName(clientName);
        messageBoardClient.setClientType(ClientType.USER);
        return asJsonString(messageBoardClient);
    }

    public static String getMessageAsJsonString(String text,String title){
        MessageDTO message = new MessageDTO();
        message.setMessageTitle(title);
        message.setMessageText(text);
        return asJsonString(message);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertFrom(String json, Class<T> aClass){
        ObjectMapper objectMapper = new ObjectMapper();
        T type = null;
        try {
            type = objectMapper.readValue(json, aClass);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return type;
    }
}
