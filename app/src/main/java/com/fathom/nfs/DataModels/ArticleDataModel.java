package com.fathom.nfs.DataModels;

import android.graphics.Bitmap;

public class ArticleDataModel {

    private int imageUrl;
    private Bitmap articleImage;
    private String articleTitle;
    private String authorName;
    private String articleContent;
    private boolean bookmark;
    private String VideoUrl;
    private String articleType;
    private String authorEducation;
    private String imagePath;
    private String id;


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

    public boolean isBookmark() {
        return bookmark;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
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

    public String getAuthorEducation() {
        return authorEducation;
    }

    public void setAuthorEducation(String authorEducation) {
        this.authorEducation = authorEducation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
