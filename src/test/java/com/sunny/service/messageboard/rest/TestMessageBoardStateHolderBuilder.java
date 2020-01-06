package com.sunny.service.messageboard.rest;

import java.util.List;

public class TestMessageBoardStateHolderBuilder {
    private List messages;
    private String s;
    private String hi1;
    private String s2;
    private String hi2;
    private String s3;
    private String hi3;

    public TestMessageBoardStateHolderBuilder setMessages(List messages) {
        this.messages = messages;
        return this;
    }

    public TestMessageBoardStateHolderBuilder setS(String s) {
        this.s = s;
        return this;
    }

    public TestMessageBoardStateHolderBuilder setHi1(String hi1) {
        this.hi1 = hi1;
        return this;
    }

    public TestMessageBoardStateHolderBuilder setS2(String s2) {
        this.s2 = s2;
        return this;
    }

    public TestMessageBoardStateHolderBuilder setHi2(String hi2) {
        this.hi2 = hi2;
        return this;
    }

    public TestMessageBoardStateHolderBuilder setS3(String s3) {
        this.s3 = s3;
        return this;
    }

    public TestMessageBoardStateHolderBuilder setHi3(String hi3) {
        this.hi3 = hi3;
        return this;
    }

    public MessageBoardStateHolder createMessageBoardStateHolder() {
        return new MessageBoardStateHolder(messages, s, hi1, s2, hi2, s3, hi3);
    }
}