package com.fathom.nfs.DataModels;

public class ReviewDataModel {

    private int rating;
    private String reviewText;
    private String userEmail;

    public ReviewDataModel(int rating, String ratingText) {
        this.rating = rating;
        this.reviewText = ratingText;
    }

    public int getRating() {
        return rating;
    }

    public String getRatingText() {
        return reviewText;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
