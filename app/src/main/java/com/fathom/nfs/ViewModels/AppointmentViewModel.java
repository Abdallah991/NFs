package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.Repositories.AppointmentRepository;

import java.util.List;

public class AppointmentViewModel extends ViewModel {

    private MutableLiveData<List<AppointmentDataModel>> mAppointments;
    private AppointmentRepository mRepository;
    private int positionOfItems;

    public void selectAppointment (List Items, int position) {

        mAppointments.setValue(Items);
        positionOfItems = position;
    }

    public int getPositionOfAppointment() {
        return positionOfItems;
    }

    public void initAppointments(String email){

        Log.d("MVVM"," init in ViewModel called.");
        if (mAppointments != null){
            Log.d("MVVM","Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d("MVVM"," Mutable data is empty and going to be loaded");

        mRepository = AppointmentRepository.getInstance();
        mAppointments = mRepository.getAppointments(email);
    }

    public LiveData<List<AppointmentDataModel>> getAppointments() {

        return mAppointments;
    }

}
