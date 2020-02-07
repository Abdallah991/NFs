package com.fathom.nfs.DataModels;

public class ShopItemDataModel {

    // TODO: add ShopItem name
    private int imageUrl;
    private String price;
    private String itemDescription;
    private boolean bookmark;


    public ShopItemDataModel(int imageUrl, String price, String itemDescription) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.itemDescription = itemDescription;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
