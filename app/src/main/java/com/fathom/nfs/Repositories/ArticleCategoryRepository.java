package com.fathom.nfs.Repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.fathom.nfs.DataModels.ArticleCategoryDataModel;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticleCategoryRepository {

    /**
     * @class Article category Repository
     * @desription   Article category from backend
     * currently its being hardcoded since no functionality from the admin dashboard
     * @date 4 feb 2021
     */
    private static ArticleCategoryRepository instance;

    private ArrayList<ArticleCategoryDataModel> mArticleCategories = new ArrayList<>();
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();


    public static ArticleCategoryRepository getInstance() {

        if (instance == null) {

            instance = new ArticleCategoryRepository();
        }


        return instance;
    }

    public MutableLiveData<List<ArticleCategoryDataModel>> getArticleCategories() {

        // calling the webservice task of function
        setArticleCategories();
        MutableLiveData<List<ArticleCategoryDataModel>> data = new MutableLiveData<>();
        data.setValue(mArticleCategories);

        return data;
    }


    // Article category data
    private  void setArticleCategories () {

        if (mArticles.isEmpty()) {

            mArticles.add (
                    new ArticleDataModel(R.drawable.autism_article, "When to Test your Child for Autism", "Dr MUNEERA"));

            mArticles.add (
                    new ArticleDataModel(R.drawable.abuse_article, "Suicid Awareness in the Middle East", "Dr EMAD"));

            mArticles.add (
                    new ArticleDataModel(R.drawable.suicide_article, "5 Steps to Deal with Domestic Abuse", "Dr KHULOOD"));


        }

        if (mArticleCategories.isEmpty()) {


            mArticleCategories.add (
                    new ArticleCategoryDataModel(R.drawable.article_card ,"Articles", mArticles));
            mArticleCategories.add (
                    new ArticleCategoryDataModel(R.drawable.videos_card ,"Videos", mArticles));
            mArticleCategories.add (
                    new ArticleCategoryDataModel(R.drawable.blogs_card ,"Blog", mArticles));
            mArticleCategories.add (
                    new ArticleCategoryDataModel(R.drawable.community_card ,"Community", mArticles));




        }
    }
}
