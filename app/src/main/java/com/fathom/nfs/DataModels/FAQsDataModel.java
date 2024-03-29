package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class FAQsDataModel {

    /**
     * @class FAQ data model
     * @desription  Hold FAQ category
     * @date 4 feb 2021
     */
    private String FAQCategory;
    private CategoryDataModel categoryName;
    private ArrayList<QuestionDataModel> FAQs;


    public FAQsDataModel(String questionTitle, CategoryDataModel categoryName, ArrayList<QuestionDataModel> FAQs) {
        this.FAQCategory = questionTitle;
        this.categoryName = categoryName;
        this.FAQs = FAQs;
    }

    public String getFAQCategory() {
        return FAQCategory;
    }

    public CategoryDataModel getCategoryName() {
        return categoryName;
    }

    public ArrayList<QuestionDataModel> getFAQs() {
        return FAQs;
    }
}
