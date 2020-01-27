package com.fathom.nfs.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.HelpLineViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Singleton pattern
 *
 * **/

public class HelpLineRepository {

    private static HelpLineRepository instance;

    private ArrayList<HelpLinesDataModel> mHelpLineItems = new ArrayList<>();


    public static HelpLineRepository getInstance() {
        if (instance == null) {

            Log.d("MVVM"," getting static instance of the Repo.");
            instance = new HelpLineRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the Repo.");

        return instance;
    }

    public MutableLiveData<List<HelpLinesDataModel>> getHelpLiens () {

        // calling the webservice task of function
        setHelpLineItems();
        MutableLiveData<List<HelpLinesDataModel>> data = new MutableLiveData<>();
        data.setValue(mHelpLineItems);

        return data;
    }


    // Getting live data from webservice
    private  void setHelpLineItems () {

        Log.d("MVVM"," Loading the data is going to start");

        if (mHelpLineItems.isEmpty()) {
        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.suicide, R.drawable.suicide_image, "suicide helpline")
        );
        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.abuse, R.drawable.suicide_image, "Abuse helpline")
        );
        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.depression, R.drawable.suicide_image,"Depression helpline")
        );

        Log.d("MVVM"," Loading the data is DONE");

        }
    }
}
