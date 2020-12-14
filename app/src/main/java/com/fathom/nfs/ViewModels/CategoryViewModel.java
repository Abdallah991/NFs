package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.Repositories.CategoryRepository;


import java.util.List;

public class CategoryViewModel extends ViewModel {


    private MutableLiveData<List<CategoryDataModel>> mCategories;
    private CategoryRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectCategory (List Items, int position) {

        mCategories.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfCategory() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initCategories(){

        if (mCategories != null){
            return;
        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = CategoryRepository.getInstance();
        mCategories = mRepository.getCategories();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<CategoryDataModel>> getCategories() {
        return mCategories;
    }
}
