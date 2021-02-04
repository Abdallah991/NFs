package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.ArticleCategoryDataModel;

import com.fathom.nfs.Repositories.ArticleCategoryRepository;


import java.util.List;

public class ArticleCategoryViewModel extends ViewModel {


    /**
     * @class Article Category View model
     * @desription  setting article categories as live data
     * @date 4 feb 2021
     */
    private MutableLiveData<List<ArticleCategoryDataModel>> mArticleCategories;
    private ArticleCategoryRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectArticleCategory (List Items, int position) {

        mArticleCategories.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfArticleCategory() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initArticleCategories(){

        if (mArticleCategories != null){
            return;
        }


        mRepository = ArticleCategoryRepository.getInstance();
        mArticleCategories = mRepository.getArticleCategories();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<ArticleCategoryDataModel>> getArticleCategories() {
        return mArticleCategories;
    }
}
