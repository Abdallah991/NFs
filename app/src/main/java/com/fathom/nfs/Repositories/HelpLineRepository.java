package com.fathom.nfs.Repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.R;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Singleton pattern
 *
 **/

public class HelpLineRepository {

    // Creating one instance
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
                new HelpLinesDataModel(R.drawable.abuse, R.drawable.abuse_image, "Abuse helpline", "+97366710901")
        );
//        mHelpLineItems.add (
//                new HelpLinesDataModel(R.drawable.depression, R.drawable.depression_image,"Depression helpline", "+4355664427")
//        );

        Log.d("MVVM"," Loading the data is DONE");

        }
    }
}
