package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.Repositories.HelpLineRepository;

import java.util.List;

public class HelpLineViewModel extends ViewModel {

    // Live data that can be changed
    private MutableLiveData<List<HelpLinesDataModel>> mHelpLines;
    private HelpLineRepository mRepository;
    private int positionOfItems;

    // Passing the array and the position of one element using the adapter
    public void select (List Items, int position) {

        mHelpLines.setValue(Items);
        positionOfItems = position;
    }


    // Getting the list
    public LiveData<List<HelpLinesDataModel>> getmHelpLines() {
        return mHelpLines;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfItems() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void init(){

        Log.d("MVVM"," init in ViewModel called.");
        if (mHelpLines != null){
            Log.d("MVVM","Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = HelpLineRepository.getInstance();
        mHelpLines = mRepository.getHelpLiens();
    }
    // Live data that cant be changed
    public LiveData<List<HelpLinesDataModel>> getHelpLines() {
        return mHelpLines;
    }
}
