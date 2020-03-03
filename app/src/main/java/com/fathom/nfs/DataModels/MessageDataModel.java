package com.fathom.nfs.DataModels;

public class MessageDataModel {

    private String message;
    private String author;

    public MessageDataModel(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public MessageDataModel() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
