package com.fathom.nfs.DataModels;

import android.graphics.Bitmap;

public class ShopItemDataModel {

    /**
     * @class shop item data model
     * @desription  Hold shop item data
     * @date 4 feb 2021
     */
    private int imageUrl;
    private Bitmap shopItemImage;
    private String price;
    private String shopItemName;
    private String shopItemSubName;
    private String itemDescription;
    private boolean bookmark;
    private String shopItemType;


    public ShopItemDataModel(int imageUrl, String price, String itemDescription) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.itemDescription = itemDescription;
    }

    public ShopItemDataModel () {

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

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public Bitmap getShopItemImage() {
        return shopItemImage;
    }

    public String getShopItemName() {
        return shopItemName;
    }

    public String getShopItemSubName() {
        return shopItemSubName;
    }

    public String getShopItemType() {
        return shopItemType;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShopItemImage(Bitmap shopItemImage) {
        this.shopItemImage = shopItemImage;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setShopItemName(String shopItemName) {
        this.shopItemName = shopItemName;
    }

    public void setShopItemSubName(String shopItemSubName) {
        this.shopItemSubName = shopItemSubName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setShopItemType(String shopItemType) {
        this.shopItemType = shopItemType;
    }
}
