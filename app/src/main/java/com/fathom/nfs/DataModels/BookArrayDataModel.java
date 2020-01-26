package com.fathom.nfs.DataModels;

import java.util.ArrayList;
import java.util.List;

public class BookArrayDataModel {

    // depends on data received
    ArrayList<BookDataModel> books;

    public BookArrayDataModel(ArrayList<BookDataModel> books) {
        this.books = books;
    }

    public List<BookDataModel> getBooks() {
        return books;
    }
}
