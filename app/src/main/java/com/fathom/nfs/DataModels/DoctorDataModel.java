package com.fathom.nfs.DataModels;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class DoctorDataModel {

    private String doctorFirstName;
    private String doctorLastName;
    private String email;
    private int imageUrl;
    private Bitmap doctorImage;
    private boolean bookmark;
    private double rating;
    private String specialty;
    private String about;
    private String education;
    private String experience;
    private String gender;
    private String phone;
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

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public static ArrayList<AppointmentDataModel> getDoctorAppointments() {
        return doctorAppointments;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setReviews(ArrayList<ReviewDataModel> reviews) {
        this.reviews = reviews;
    }

    public static void setDoctorAppointments(ArrayList<AppointmentDataModel> doctorAppointments) {
        DoctorDataModel.doctorAppointments = doctorAppointments;
    }

    public Bitmap getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(Bitmap doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
