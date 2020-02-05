package com.fathom.nfs.Repositories;



import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;
import java.util.List;

public class BookArrayRepository {

    // Creating one instance
    private static BookArrayRepository instance;

    private ArrayList<BookRowDataModel> mBookRows = new ArrayList<>();
    private ArrayList<ShopItemDataModel> mBooks = new ArrayList<>();
    private ArrayList<ShopItemDataModel> row1 = new ArrayList<>();
    private ArrayList<ShopItemDataModel> row2 = new ArrayList<>();
    private ArrayList<ShopItemDataModel> row3 = new ArrayList<>();


    public static BookArrayRepository getInstance() {
        if (instance == null) {

            instance = new BookArrayRepository();
        }


        return instance;
    }

    public MutableLiveData<List<BookRowDataModel>> getBookRows() {

        // calling the webservice task of function
        setBookRows();
        MutableLiveData<List<BookRowDataModel>> data = new MutableLiveData<>();
        data.setValue(mBookRows);

        return data;
    }


    // Getting live data from webservice
    private  void setBookRows () {

        if (mBookRows.isEmpty()) {
        ShopItemDataModel book1 = new ShopItemDataModel(R.drawable.book3, "BHD 7.500", "This book is great");
        ShopItemDataModel book2 = new ShopItemDataModel(R.drawable.book1, "BHD 9.765", "Meebie - For Play And Emotional Expression");
        ShopItemDataModel book3 = new ShopItemDataModel(R.drawable.book2, "BHD 5.243", "This book is About scaling your business");

        row1.add(book3);
        row1.add(book1);
        row2.add(book2);
        row2.add(book3);
        row3.add(book1);
        row3.add(book2);

        BookRowDataModel booksRow1 = new BookRowDataModel(row1);
        BookRowDataModel booksRow2 = new BookRowDataModel(row2);
        BookRowDataModel booksRow3 = new BookRowDataModel(row3);



            mBookRows.add (booksRow1);
            mBookRows.add (booksRow2);
            mBookRows.add (booksRow3);


        }
    }
}
