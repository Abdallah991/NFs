package com.fathom.nfs.ViewModels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.Repositories.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends ViewModel {

    private MutableLiveData<List<ReviewDataModel>> mReviews;
    private ReviewRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectReview (List Items, int position) {

        mReviews.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfReview() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initReviews(String email){

//        if (mReviews != null){
//            return;
//        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = ReviewRepository.getInstance();
        mReviews = mRepository.getReviews(email);
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<ReviewDataModel>> getReviews() {
        return mReviews;
    }
}
