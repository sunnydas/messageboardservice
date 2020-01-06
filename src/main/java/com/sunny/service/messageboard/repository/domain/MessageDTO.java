package com.sunny.service.messageboard.repository.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a single message on the message board.
 */
@Entity(name = "message")
public class MessageDTO implements Serializable {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long messageId;

    @Column(name = "message_client_id")
    private long clientId;

    @Column(name = "creation_ts")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "messageId=" + messageId +
                ", clientId=" + clientId +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", messageText='" + messageText + '\'' +
                ", messageTitle='" + messageTitle + '\'' +
                '}';
    }

    @Column(name = "updated_ts")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    @Column(name = "text")
    private String messageText;

    @Column(name = "title")
    private String messageTitle;

}