package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.Repositories.ArticlesRepository;


import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.DataModels.BookmarkDataModel.articleItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;

public class ArticleViewModel extends ViewModel {


    private MutableLiveData<List<ArticleDataModel>> mArticles;
    private ArticlesRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectArticle (List Items, int position) {

        mArticles.setValue(Items);
        positionOfItems = position;
        // To hold the articles to the original value
//        mArticles = mRepository.getArticles();
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

    public void getAllArticles() {
        mArticles = mRepository.getArticles();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<ArticleDataModel>> getArticles() {
        return mArticles;
    }


    public void bookmarkItem(ArticleDataModel article) {
        ArrayList<ArticleDataModel> articles = new ArrayList<>();
        for (ArticleDataModel a: mArticles.getValue()) {
            if ((article.getArticleTitle()).equals(a.getArticleTitle())) {
                article.setBookmark(true);
            }
            articles.add(a);
        }
        mArticles.setValue(articles);
        Log.d("DOCTOR", "bookmark have been set");
        articleItemsBookmarked.add(article);


    }

    public void unBookmarkItem(ArticleDataModel article) {
        ArrayList<ArticleDataModel> articles = new ArrayList<>();
        for (ArticleDataModel a: mArticles.getValue()) {
            if ((article.getArticleTitle()).equals(a.getArticleTitle())) {
                article.setBookmark(false);
            }
            articles.add(a);
        }
        mArticles.setValue(articles);

        articleItemsBookmarked.remove(article);


        Log.d("DOCTOR", "bookmark have been removed");

    }
}
