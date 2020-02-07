package com.fathom.nfs.ViewModels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.Repositories.BookArrayRepository;
import java.util.List;

public class BookArrayViewModel  extends ViewModel {

    private MutableLiveData<List<BookRowDataModel>> mBookArrays;
    private MutableLiveData<List<ShopItemDataModel>> mBooks;
    private BookArrayRepository mRepository;
    private int positionOfItems;
    private int positionOfRow;

    // select the data list and pass the position of list item
    public void SelectRow (List Items, int position) {

        mBookArrays.setValue(Items);
        positionOfRow = position;
    }

    public void SelectBook (List Items, int position) {

        mBookArrays.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfRow() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initBookArrays(){


        if (mBookArrays != null){
            return;
        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = BookArrayRepository.getInstance();
        mBookArrays = mRepository.getBookRows();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<BookRowDataModel>> getBookArrays() {
        return mBookArrays;
    }

}
