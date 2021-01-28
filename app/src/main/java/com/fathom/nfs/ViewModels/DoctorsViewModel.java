package com.fathom.nfs.ViewModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.MainActivity;
import com.fathom.nfs.Repositories.DoctorsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.DoctorsDetails.doctorEmailId;

public class DoctorsViewModel extends ViewModel {

    private MutableLiveData<List<DoctorDataModel>> mDoctors;
    private DoctorsRepository mRepository;
    private int positionOfItems;

    // select the data list and pass the position of list item
    public void selectDoctor (List Items, int position) {

        mDoctors.setValue(Items);
        positionOfItems = position;
        // To hold the doctors to the original value
//        mDoctors = mRepository.getDoctors();
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

    public void getAllDoctors() {
        mDoctors = mRepository.getDoctors();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<DoctorDataModel>> getDoctors() {

        return mDoctors;
    }

    public void changeRating(double  newRating) {
        ArrayList<DoctorDataModel> doctors = new ArrayList<>();
        for (DoctorDataModel doctor: mDoctors.getValue()) {

            if (doctor.getEmail().equals(doctorEmailId)) {
                doctor.setRating(newRating);
            }

            doctors.add(doctor);
        }
        mDoctors.setValue(doctors);

    }

    public void bookmarkItem(DoctorDataModel doctor) {
        ArrayList<DoctorDataModel> doctors = new ArrayList<>();
        for (DoctorDataModel d: mDoctors.getValue()) {
            if ((doctor.getDoctorFirstName()+doctor.getDoctorLastName()).equals(d.getDoctorFirstName()+d.getDoctorLastName())) {
                doctor.setBookmark(true);
            }
            doctors.add(d);
        }
        mDoctors.setValue(doctors);
        Log.d("DOCTOR", "bookmark have been set");
        doctorItemsBookmarked.add(doctor);


    }

    public void unBookmarkItem(DoctorDataModel doctor) {
        ArrayList<DoctorDataModel> doctors = new ArrayList<>();
        for (DoctorDataModel d: mDoctors.getValue()) {
            if ((doctor.getDoctorFirstName()+doctor.getDoctorLastName()).equals(d.getDoctorFirstName()+d.getDoctorLastName())) {
                doctor.setBookmark(false);
            }
            doctors.add(d);
        }
        mDoctors.setValue(doctors);

        doctorItemsBookmarked.remove(doctor);


        Log.d("DOCTOR", "bookmark have been removed");

    }


    public void saveBookmarkedDoctors(Context context) {
        SharedPreferences shared = context.getSharedPreferences("doctorBookmarked", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        Gson gson = new Gson();
        String json = gson.toJson(doctorItemsBookmarked);
        editor.putString("doctors", json);
//        doctorsFromShared = loadBookmarkedDoctors(context) ;
        String json1 = shared.getString("doctors", null);
        Type type = new TypeToken<ArrayList<DoctorDataModel>>() {}.getType();
        ArrayList<DoctorDataModel> doctorsFromShared = new ArrayList<>();
        doctorsFromShared = gson.fromJson(json1, type);
        Toast.makeText(context, doctorsFromShared.toString()+"", Toast.LENGTH_SHORT).show();

    }

    public void loadBookmarkedDoctors(Context context) {
        SharedPreferences shared = context.getSharedPreferences("doctorBookmarked", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = shared.getString("doctors", null);
        Type type = new TypeToken<ArrayList<DoctorDataModel>>() {}.getType();
        doctorItemsBookmarked = gson.fromJson(json, type);
        if(doctorItemsBookmarked == null) {
            doctorItemsBookmarked = new ArrayList<>();
        }
//        return doctorItemsBookmarked;
    }

//    public ArrayList<DoctorDataModel> getBookmarkedDoctors() {
//
//        ArrayList<DoctorDataModel> doctors = new ArrayList<>();
//
//        for (DoctorDataModel d: mDoctors.getValue()) {
//            if (d.isBookmark()) {
//                doctors.add(d);
//            }
//        }
////        bookmarkedDoctors.setValue(doctors);
//
//        return doctors;
//    }

}
