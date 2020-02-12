package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class DoctorDataModel {

    private String doctorFirstName;
    private String doctorLastName;
    private int imageUrl;
    private boolean bookmark;
    private double rating;
    private String specialty;
    private String about;
    private String education;
    private String experience;
    private ArrayList<ReviewDataModel> reviews = new ArrayList<>();
    public static ArrayList<AppointmentDataModel> doctorAppointments = new ArrayList<>();



    public DoctorDataModel
            (String doctorFirstName,
             String doctorLastName,
             int imageUrl,
             boolean bookmark,
             double rating,
             String specialty,
             String about,
             String education,
             String experience,
             ArrayList<ReviewDataModel> reviews)
    {
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.imageUrl = imageUrl;
        this.bookmark = bookmark;
        this.rating = rating;
        this.specialty = specialty;
        this.about = about;
        this.education = education;
        this.experience = experience;
        this.reviews = reviews;
    }

    public DoctorDataModel(String doctorFirstName, String doctorLastName, int imageUrl, double rating) {
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public DoctorDataModel() {

    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public double getRating() {
        return rating;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getAbout() {
        return about;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }

    public ArrayList<ReviewDataModel> getReviews() {
        return reviews;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
