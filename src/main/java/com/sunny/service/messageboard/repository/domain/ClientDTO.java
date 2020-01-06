package com.sunny.service.messageboard.repository.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This class represents a MessageDTO client , basically a generic stakeholder
 * for the message board service. This could represent a user or any other logical
 * entity capable of using the message board service, for example other applications.
 *
 */
@Entity(name = "message_board_client")
public class ClientDTO implements Serializable {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long clientId;

    @Column(name = "client_name",
    unique = true)
    private String clientName;

    @Column(name = "client_description")
    private String clientDescription;

    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "creation_ts")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @Column(name = "updated_ts")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @Override
    public String toString() {
        return "ClientDTO{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientDescription='" + clientDescription + '\'' +
                ", clientType=" + clientType +
                ", createdDate=" + createdDate +
                ", updateDate=" + updateDate +
                '}';
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO that = (ClientDTO) o;
        return clientId == that.clientId &&
                Objects.equals(clientName, that.clientName) &&
                Objects.equals(clientDescription, that.clientDescription) &&
                clientType == that.clientType &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientName, clientDescription, clientType, createdDate, updateDate);
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}