package com.fathom.nfs.DataModels;

import java.util.ArrayList;
import java.util.List;

public class BookArrayDataModel extends ShopItemDataModel{

    /**
     * @class Book array data model
     * @desription  Hold book arrays
     * @date 4 feb 2021
     */
    // depends on data received
    private ArrayList<BookRowDataModel> booksArray;

    public BookArrayDataModel(ArrayList<BookRowDataModel> books) {
        this.booksArray = books;
    }

    public List<BookRowDataModel> getBooksArray() {
        return booksArray;
    }
}
