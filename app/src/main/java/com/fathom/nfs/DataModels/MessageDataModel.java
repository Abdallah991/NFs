package com.fathom.nfs.DataModels;

public class MessageDataModel {

    private String subject;
    private String text;

    public MessageDataModel(String message, String author) {
        this.subject = message;
        this.text = author;
    }

    public MessageDataModel() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
