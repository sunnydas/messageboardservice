package com.sunny.service.messageboard.repository.util;

import com.sunny.service.messageboard.repository.domain.MessageDTO;
import com.sunny.service.messageboard.rest.domain.MessagePayLoad;

import java.util.Date;

public class RepositoryUtils {


    public static MessageDTO createMessage(MessagePayLoad messagePayLoad, long clientId){
        MessageDTO message = new MessageDTO();
        message.setMessageText(messagePayLoad.getMessageText());
        message.setMessageTitle(messagePayLoad.getMessageTitle());
        message.setClientId(clientId);
        Date currentDate = getDate();
        message.setCreationDate(currentDate);
        message.setUpdateDate(currentDate);
        return message;
    }

    public static void updateMessageTimeStamps(MessageDTO message){
        Date currentDate = getDate();
        message.setCreationDate(currentDate);
        message.setUpdateDate(currentDate);
    }

    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

}
