package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.Repositories.DoctorsRepository;

import java.util.List;

public class DoctorsViewModel extends ViewModel {

    private MutableLiveData<List<DoctorDataModel>> mDoctors;
    private DoctorsRepository mRepository;
    private int positionOfItems;

    public void selectDoctor (List Items, int position) {

        mDoctors.setValue(Items);
        positionOfItems = position;
    }

    // Getting the position of the item in the list selected
    public int getPositionOfDoctor() {
        return positionOfItems;
    }

    // Getting the data from the Repository
    public void initDoctors(){

        Log.d("MVVM"," init in ViewModel called.");
        if (mDoctors != null){
            Log.d("MVVM","Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = DoctorsRepository.getInstance();
        mDoctors = mRepository.getDoctors();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<DoctorDataModel>> getDoctors() {
        return mDoctors;
    }

}
