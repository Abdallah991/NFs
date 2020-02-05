package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class CategoryDataModel {

    private String category;
    private String categoryDescription;
    private int categoryMonster;
    private int categoryImage;
    private ArrayList<DoctorDataModel> categoryDoctors;


    public CategoryDataModel(int categoryImage ,String category, String categoryDescription, int categoryMonster) {
        this.category = category;
        this.categoryDescription = categoryDescription;
        this.categoryMonster = categoryMonster;
        this.categoryImage = categoryImage;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public int getCategoryMonster() {
        return categoryMonster;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public ArrayList<DoctorDataModel> getCategoryDoctors() {
        return categoryDoctors;
    }
}
