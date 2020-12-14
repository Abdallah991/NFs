package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class ArticleCategoryDataModel {

    // Categories are articles, videos, blogs and community

    private String articleCategory;
    private int categoryImage;
    private ArrayList<ArticleDataModel> articles;

    public ArticleCategoryDataModel(int image ,String articleCategory, ArrayList<ArticleDataModel> articles) {
        categoryImage = image;
        this.articleCategory = articleCategory;
        this.articles = articles;
    }

    public String getArticleCategory() {
        return articleCategory;
    }

    public ArrayList<ArticleDataModel> getArticles() {
        return articles;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
