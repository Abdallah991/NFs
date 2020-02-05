package com.fathom.nfs.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.Repositories.ShopItemsRepository;
import java.util.List;

public class ShopItemsViewModel extends ViewModel {

    private MutableLiveData<List<ShopItemDataModel>> mShopItems;
    private ShopItemsRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectShopItems (List Items, int position) {

        mShopItems.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfShopItem() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initShopItem(){


        if (mShopItems != null){
            return;
        }


        mRepository = ShopItemsRepository.getInstance();
        mShopItems = mRepository.getShopItems();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<ShopItemDataModel>> getShopItems() {
        return mShopItems;
    }

}
