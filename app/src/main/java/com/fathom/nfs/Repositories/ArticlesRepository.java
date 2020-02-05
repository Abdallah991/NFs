package com.fathom.nfs.Repositories;

import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.R;
import java.util.ArrayList;
import java.util.List;

public class ArticlesRepository {

    // Creating one instance
    private static ArticlesRepository instance;

    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();


    public static ArticlesRepository getInstance() {

        if (instance == null) {

            instance = new ArticlesRepository();
        }


        return instance;
    }

    public MutableLiveData<List<ArticleDataModel>> getArticles() {

        // calling the webservice task of function
        setArticles();
        MutableLiveData<List<ArticleDataModel>> data = new MutableLiveData<>();
        data.setValue(mArticles);

        return data;
    }


    // Getting live data from webservice
    private  void setArticles () {


        if (mArticles.isEmpty()) {

            mArticles.add (
                    new ArticleDataModel(R.drawable.autism_article, "When to Test your Child for Autism", "Dr MUNEERA"));

            mArticles.add (
                    new ArticleDataModel(R.drawable.abuse_article, "Suicid Awareness in the Middle East", "Dr EMAD"));

            mArticles.add (
                    new ArticleDataModel(R.drawable.suicide_article, "5 Steps to Deal with Domestic Abuse", "Dr KHULOOD"));




        }
    }
}
