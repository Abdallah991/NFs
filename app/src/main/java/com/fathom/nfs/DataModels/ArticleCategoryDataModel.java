package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class ArticleCategoryDataModel {
    /**
     * @class Article Category Data model
     * @desription  Hold Article Category data
     * @date 4 feb 2021
     */
    // Categories are articles, videos, blog and community
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
