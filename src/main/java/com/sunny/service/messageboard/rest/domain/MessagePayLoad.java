package com.sunny.service.messageboard.rest.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the user visible fields that correspond to a message
 */
public class MessagePayLoad implements Serializable {

    private String messageText;

    private String messageTitle;

    @Override
    public String toString() {
        return "MessagePayLoad{" +
                "messageText='" + messageText + '\'' +
                ", messageTitle='" + messageTitle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagePayLoad that = (MessagePayLoad) o;
        return Objects.equals(messageText, that.messageText) &&
                Objects.equals(messageTitle, that.messageTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageText, messageTitle);
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
}
