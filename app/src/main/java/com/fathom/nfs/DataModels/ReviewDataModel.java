package com.fathom.nfs.DataModels;

public class ReviewDataModel {

    private int rating;
    private String reviewText;

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
}
