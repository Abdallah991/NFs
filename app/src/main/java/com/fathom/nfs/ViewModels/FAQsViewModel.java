package com.fathom.nfs.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.FAQsDataModel;
import com.fathom.nfs.Repositories.FAQsRepository;

import java.util.List;

public class FAQsViewModel extends ViewModel {

    private MutableLiveData<List<FAQsDataModel>> mFAQs;
    private FAQsRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectFAQs (List Items, int position) {

        mFAQs.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfFAQ() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initFAQs(){

        if (mFAQs != null){
            return;
        }


        mRepository = FAQsRepository.getInstance();
        mFAQs = mRepository.getFAQs();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<FAQsDataModel>> getFAQs() {
        return mFAQs;
    }

}
