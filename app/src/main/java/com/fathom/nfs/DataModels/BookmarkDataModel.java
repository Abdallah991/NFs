package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class BookmarkDataModel {

    /**
     * @class Bookmark data model
     * @desription  Hold book arrays
     * @date 4 feb 2021
     */
    public static ArrayList<ShopItemDataModel> shopItemsBookmarked = new ArrayList<>();
    public static ArrayList<DoctorDataModel> doctorItemsBookmarked  = new ArrayList<>();
    public static ArrayList<ArticleDataModel> articleItemsBookmarked = new ArrayList<>();


    public static int positionOfBookMark;

    public static boolean isClickedFromBookmarks() {
        return clickedFromBookmarks;
    }

    public static void setClickedFromBookmarks(boolean clickedFromBookmarks) {
        BookmarkDataModel.clickedFromBookmarks = clickedFromBookmarks;
    }

    public static boolean clickedFromBookmarks;


    public static ArrayList<ShopItemDataModel> getShopItemsBookmarked() {
        return shopItemsBookmarked;
    }

    public static ArrayList<DoctorDataModel> getDoctorItemsBookmarked() {
        return doctorItemsBookmarked;
    }

    public static ArrayList<ArticleDataModel> getArticleItemsBookmarked() {
        return articleItemsBookmarked;
    }

    public static int getPositionOfBookMark() {
        return positionOfBookMark;
    }

    public static void setPositionOfBookMark(int positionOfBookMark) {
        BookmarkDataModel.positionOfBookMark = positionOfBookMark;
    }
}
