package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class BookmarkDataModel {

    public static ArrayList<ShopItemDataModel> shopItemsBookmarked = new ArrayList<>();
    public static ArrayList<DoctorDataModel> doctorItemsBookmarked  = new ArrayList<>();
    public static ArrayList<ArticleDataModel> articleItemsBookmarked = new ArrayList<>();


    public static ArrayList<ShopItemDataModel> getShopItemsBookmarked() {
        return shopItemsBookmarked;
    }

    public static ArrayList<DoctorDataModel> getDoctorItemsBookmarked() {
        return doctorItemsBookmarked;
    }

    public static ArrayList<ArticleDataModel> getArticleItemsBookmarked() {
        return articleItemsBookmarked;
    }
}
