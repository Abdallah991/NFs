package com.fathom.nfs.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.Repositories.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;

public class AppointmentViewModel extends ViewModel {

    /**
     * @class Appointment View model
     * @desription  setting appointments as live data
     * @date 4 feb 2021
     */
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

    // getting appointments from repo
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


    // Delete appointment from the backend
    // Remove the appointment from the live data
    public void deleteAppointment(AppointmentDataModel appointment) {
        Log.d("APPOINT","Delete appointment triggered!");
        mRepository.deleteAppointment(appointment);
        ArrayList<AppointmentDataModel> appointments = new ArrayList<>();
        for (AppointmentDataModel a: mAppointments.getValue()) {
            if (!a.getDocumentId().equals(appointment.getDocumentId())) {
                appointments.add(a);
            }
        }
        mAppointments.setValue(appointments);

    }
}
