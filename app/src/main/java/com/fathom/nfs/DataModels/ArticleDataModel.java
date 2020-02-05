package com.fathom.nfs.DataModels;

public class ArticleDataModel {

    private int imageUrl;
    private String articleTitle;
    private String authorName;
    private String articleContent;
    private boolean bookemark;
    private String VideoUrl;

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

    public String getArticleContent() {
        return articleContent;
    }

    public boolean isBookemark() {
        return bookemark;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }
}
