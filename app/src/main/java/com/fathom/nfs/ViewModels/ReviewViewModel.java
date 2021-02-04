package com.fathom.nfs.ViewModels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.Repositories.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewViewModel extends ViewModel {
    /**
     * @class review View model
     * @desription  setting reviews as live data
     * @date 4 feb 2021
     */
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


        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = ReviewRepository.getInstance();
        mReviews = mRepository.getReviews(email);
    }

    // adding review
    public void addReview(ReviewDataModel review) {

        ArrayList<ReviewDataModel> reviews = new ArrayList<>();
        reviews = (ArrayList<ReviewDataModel>) mReviews.getValue();
        boolean found = false;

        for (ReviewDataModel r: reviews) {
            if (r.getUserEmail().equals(review.getUserEmail())) {
                r.setReviewText(review.getReviewText());
                int rating = (int) review.getRating();
                r.setRating(rating);
                found = true;
            }
        }
        if (!found) {
            reviews.add(review);

        }
        mReviews.setValue(reviews);
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<ReviewDataModel>> getReviews() {
        return mReviews;
    }
}
