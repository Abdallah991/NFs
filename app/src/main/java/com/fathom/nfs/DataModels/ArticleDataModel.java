package com.fathom.nfs.DataModels;

import android.graphics.Bitmap;

public class ArticleDataModel {

    private int imageUrl;
    private Bitmap articleImage;
    private String articleTitle;
    private String authorName;
    private String articleContent;
    private boolean bookemark;
    private String VideoUrl;
    private String articleType;

    public ArticleDataModel(int imageUrl, String articleTitle, String authorName) {
        this.imageUrl = imageUrl;
        this.articleTitle = articleTitle;
        this.authorName = authorName;

    }

    public ArticleDataModel() {

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

    public void setBookemark(boolean bookemark) {
        this.bookemark = bookemark;
    }

    public Bitmap getArticleImage() {
        return articleImage;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setArticleImage(Bitmap articleImage) {
        this.articleImage = articleImage;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
}
