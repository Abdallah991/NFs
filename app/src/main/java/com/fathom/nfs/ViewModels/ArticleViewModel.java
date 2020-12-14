package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.Repositories.ArticlesRepository;


import java.util.List;

public class ArticleViewModel extends ViewModel {


    private MutableLiveData<List<ArticleDataModel>> mArticles;
    private ArticlesRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectArticle (List Items, int position) {

        mArticles.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfArticle() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initArticles(){

        if (mArticles != null){
            return;
        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = ArticlesRepository.getInstance();
        mArticles = mRepository.getArticles();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<ArticleDataModel>> getArticles() {
        return mArticles;
    }
}
