package com.fathom.nfs.DataModels;

public class ReviewDataModel {

    private int rating;
    private String reviewText;
    private String userEmail;
    private String doctorEmail;

    public ReviewDataModel(int rating, String ratingText) {
        this.rating = rating;
        this.reviewText = ratingText;
    }

    public ReviewDataModel(int rating, String reviewText, String userEmail, String doctorEmail) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.userEmail = userEmail;
        this.doctorEmail = doctorEmail;
    }

    public ReviewDataModel() {
    }

    public float getRating() {
        return rating;
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

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }
}
