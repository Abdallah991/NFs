package com.fathom.nfs.DataModels;

import java.util.ArrayList;

public class CartDataModel {


    public static ArrayList<ShopItemDataModel> shoppingCartItems = new ArrayList<>();


    public static ArrayList<ShopItemDataModel> getShoppingCartItems() {
        return shoppingCartItems;
    }


    public static void addShoppingCartItems(ShopItemDataModel item) {

        shoppingCartItems.add(item);
    }


}
