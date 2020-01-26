package com.fathom.nfs.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.HelpLinesDataModel;

import java.util.List;

public class HelpLineViewModel extends ViewModel {

    // Live data that can be changed
    private MutableLiveData<List<HelpLinesDataModel>> mHelpLines;

    // Live data that cant be changed
    public LiveData<List<HelpLinesDataModel>> getHelpLines() {
        return mHelpLines;
    }
}
