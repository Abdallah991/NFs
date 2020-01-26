package com.fathom.nfs.DataModels;

public class ArticleDataModel {

    private int imageUrl;
    private String articleTitle;
    private String authorName;
    private boolean bookemark;

    public ArticleDataModel(int imageUrl, String articleTitle, String authorName) {
        this.imageUrl = imageUrl;
        this.articleTitle = articleTitle;
        this.authorName = authorName;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getAuthorName() {
        return authorName;
    }
}
