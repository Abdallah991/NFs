package com.fathom.nfs.Repositories;

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

            instance = new HelpLineRepository();
        }

        return instance;
    }

    public MutableLiveData<List<HelpLinesDataModel>> getHelpLiens () {

        setHelpLineItems();
        MutableLiveData<List<HelpLinesDataModel>> data = new MutableLiveData<>();
        data.setValue(mHelpLineItems);

        return data;
    }


    // Getting live data from webservice
    private  void setHelpLineItems () {

        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.suicide, "suicide helpline")
        );
        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.abuse, "Abuse helpline")
        );
        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.depression, "Depression helpline")
        );
    }
}
