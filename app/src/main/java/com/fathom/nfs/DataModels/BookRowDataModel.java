package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class BookRowDataModel extends ShopItemDataModel {

    /**
     * @class Book row data model
     * @desription  Hold book row arrays
     * @date 4 feb 2021
     */
    private ArrayList<ShopItemDataModel> books;

    public BookRowDataModel(ArrayList<ShopItemDataModel> books) {
        this.books = books;
    }

    public ArrayList<ShopItemDataModel> getBooks() {
        return books;
    }


}
