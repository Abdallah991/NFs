package com.fathom.nfs.DataModels;

public class BookDataModel {

    private int ImageUrl;
    private String price;
    private String description;

    public BookDataModel(int imageUrl, String price, String description) {
        ImageUrl = imageUrl;
        this.price = price;
        this.description = description;
    }

    public int getImageUrl() {
        return ImageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
