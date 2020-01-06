package com.sunny.service.messageboard.rest;

import java.util.List;

public class MessageBoardStateHolder {
    private final List messages;
    private final String s;
    private final String hi1;
    private final String s2;
    private final String hi2;
    private final String s3;
    private final String hi3;

    public MessageBoardStateHolder(List messages, String s, String hi1, String s2, String hi2, String s3, String hi3) {
        this.messages = messages;
        this.s = s;
        this.hi1 = hi1;
        this.s2 = s2;
        this.hi2 = hi2;
        this.s3 = s3;
        this.hi3 = hi3;
    }

    public List getMessages() {
        return messages;
    }

    public String getS() {
        return s;
    }

    public String getHi1() {
        return hi1;
    }

    public String getS2() {
        return s2;
    }

    public String getHi2() {
        return hi2;
    }

    public String getS3() {
        return s3;
    }

    public String getHi3() {
        return hi3;
    }
}
