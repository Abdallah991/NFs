package com.fathom.nfs.DataModels;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class UserDataModel {

    /**
     * @class user data model
     * @desription  Hold shop item data
     * @date 4 feb 2021
     */
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String accountType;
    private int imgUrl;
    private Bitmap userImage;
    private ArrayList<String> bookmarkedDoctors;
    private ArrayList<String> bookmarkedShopItems;
    private ArrayList<String> bookmarkedArticles;
    private ArrayList<AppointmentDataModel> bookedAppointments;
    private ArrayList<CartDataModel> orders;

    public UserDataModel(String firstName, String lastName, String email, String accountType, int imgUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
        this.imgUrl = imgUrl;
    }

    public UserDataModel() {

    }

    public String getFirstName() {
        return firstName ;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public ArrayList<String> getBookmarkedDoctors() {
        return bookmarkedDoctors;
    }

    public ArrayList<String> getBookmarkedShopItems() {
        return bookmarkedShopItems;
    }

    public ArrayList<String> getBookmarkedArticles() {
        return bookmarkedArticles;
    }

    public ArrayList<AppointmentDataModel> getBookedAppointments() {
        return bookedAppointments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setBookmarkedDoctors(ArrayList<String> bookmarkedDoctors) {
        this.bookmarkedDoctors = bookmarkedDoctors;
    }

    public void setBookmarkedShopItems(ArrayList<String> bookmarkedShopItems) {
        this.bookmarkedShopItems = bookmarkedShopItems;
    }

    public void setBookmarkedArticles(ArrayList<String> bookmarkedArticles) {
        this.bookmarkedArticles = bookmarkedArticles;
    }

    public void setBookedAppointments(ArrayList<AppointmentDataModel> bookedAppointments) {
        this.bookedAppointments = bookedAppointments;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

    public ArrayList<CartDataModel> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<CartDataModel> orders) {
        this.orders = orders;
    }

}
