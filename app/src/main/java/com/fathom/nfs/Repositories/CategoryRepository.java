package com.fathom.nfs.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {


    // Creating one instance
    private static CategoryRepository instance;

    private ArrayList<CategoryDataModel> mCategories = new ArrayList<>();


    public static CategoryRepository getInstance() {

        if (instance == null) {

            instance = new CategoryRepository();
        }


        return instance;
    }

    public MutableLiveData<List<CategoryDataModel>> getCategories() {

        // calling the webservice task of function
        setCategories();
        MutableLiveData<List<CategoryDataModel>> data = new MutableLiveData<>();
        data.setValue(mCategories);

        return data;
    }


    // Getting live data from webservice
    private  void setCategories () {


        if (mCategories.isEmpty()) {
            mCategories.add (
                    new CategoryDataModel(R.drawable.psychaitry,
                            "Psychiatry",
                            "Psychiatry is the medical specialty devoted to the diagnosis, prevention, and treatment of mental disorders.",
                            R.drawable.psychiatry_q));

            mCategories.add (
                    new CategoryDataModel(R.drawable.psychology,
                            "Psychology",
                            "Psychology is the science of behavior and mind which includes the study of conscious and unconscious phenomena.",
                            R.drawable.psychology_q));
            mCategories.add (
                    new CategoryDataModel(R.drawable.behavioral_therapy,
                            "Behavioral Therapy",
                            "Behavior therapy is a broad term referring to clinical psychotherapy that uses techniques derived from behaviorism",
                            R.drawable.behaviour_therapy_q));
            mCategories.add (
                    new CategoryDataModel(R.drawable.alternative_healing,
                            "Alternative Healing",
                            "Alternative therapy is a term that describes medical treatments that are used instead of traditional (mainstream) therapies.",
                            R.drawable.alternative_therapy_q));

            mCategories.add (
                    new CategoryDataModel(R.drawable.blog,
                            "Blog",
                            "This category will showcase the blogs in the app.",
                            R.drawable.psychiatry_q));

            mCategories.add (
                    new CategoryDataModel(R.drawable.shop_card,
                            "Shop",
                            "This category will direct you to the shop",
                            R.drawable.psychiatry_q));



        }
    }
}
