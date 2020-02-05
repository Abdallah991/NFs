package com.fathom.nfs.Repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.R;
import java.util.ArrayList;
import java.util.List;

public class DoctorsRepository {

    // Creating one instance
    private static DoctorsRepository instance;

    private ArrayList<DoctorDataModel> mDoctorsItems = new ArrayList<>();


    public static DoctorsRepository getInstance() {
        if (instance == null) {

            Log.d("MVVM"," getting static instance of the Repo.");
            instance = new DoctorsRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the Repo.");

        return instance;
    }

    public MutableLiveData<List<DoctorDataModel>> getDoctors() {

        // calling the webservice task of function
        setDoctorsItems();
        MutableLiveData<List<DoctorDataModel>> data = new MutableLiveData<>();
        data.setValue(mDoctorsItems);

        return data;
    }


    // Getting live data from webservice
    private  void setDoctorsItems () {

        Log.d("MVVM"," Loading the data is going to start");

        if (mDoctorsItems.isEmpty()) {
            mDoctorsItems.add (
                    new DoctorDataModel("Narjes", "Kazerooni", R.drawable.doctor1, 4.6)
            );
            mDoctorsItems.add (
                    new DoctorDataModel("Abdallah", "Alathamneh", R.drawable.user, 4.9)
            );
            mDoctorsItems.add (
                    new DoctorDataModel("Richard", "Chowne", R.drawable.doctor_s, 4.8)
            );

            Log.d("MVVM"," Loading the data is DONE");

        }
    }
}
