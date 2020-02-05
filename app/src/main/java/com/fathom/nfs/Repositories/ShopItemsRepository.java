package com.fathom.nfs.Repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;
import java.util.ArrayList;
import java.util.List;

public class ShopItemsRepository {

    // Creating one instance
    private static ShopItemsRepository instance;

    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();


    public static ShopItemsRepository getInstance() {
        if (instance == null) {

            instance = new ShopItemsRepository();
        }

        return instance;
    }

    public MutableLiveData<List<ShopItemDataModel>> getShopItems() {

        // calling the webservice task of function
        setShopItems();
        MutableLiveData<List<ShopItemDataModel>> data = new MutableLiveData<>();
        data.setValue(mShopItems);

        return data;
    }


    // Getting live data from webservice
    private  void setShopItems () {

        Log.d("MVVM"," Loading the data is going to start");

        if (mShopItems.isEmpty()) {
            mShopItems.add (
                    new ShopItemDataModel(R.drawable.shop_item_1, "BHD 6.000", "Meebie - For Play & Emotional Expression")
            );
            mShopItems.add (
                    new ShopItemDataModel(R.drawable.shop_item_2, "BHD 7.375", "Kimochis Mixed Feelings Pack…")
            );
            mShopItems.add (
                    new ShopItemDataModel(R.drawable.shop_item_3, "BHD 4.720", "Meebie - For Play & Emotional Expression")
            );

            Log.d("MVVM"," Loading the data is DONE");

        }
    }
}
