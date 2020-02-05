package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class BookRowDataModel {

    private ArrayList<ShopItemDataModel> books;

    public BookRowDataModel(ArrayList<ShopItemDataModel> books) {
        this.books = books;
    }

    public ArrayList<ShopItemDataModel> getBooks() {
        return books;
    }


}
