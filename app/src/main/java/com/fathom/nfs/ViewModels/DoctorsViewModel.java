package com.fathom.nfs.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.UserDataModel;
import com.fathom.nfs.Repositories.DoctorsRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.DoctorsDetails.doctorEmailId;
import static com.fathom.nfs.OnBoarding.userEmail;

public class DoctorsViewModel extends ViewModel {

    /**
     * @class doctor View model
     * @desription  setting doctors arrays as live data
     * @date 4 feb 2021
     */
    private MutableLiveData<List<DoctorDataModel>> mDoctors;
    private DoctorsRepository mRepository;
    private int positionOfItems;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<UserDataModel> User = new MutableLiveData<>();
    private ArrayList<DoctorDataModel> doctorsBookmarked = new ArrayList<>();



    // select the data list and pass the position of list item
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

    public void getAllDoctors() {
        mDoctors = mRepository.getDoctors();
    }

    // Getting the list
    // Live data that cant be changed
    public LiveData<List<DoctorDataModel>> getDoctors() {

        return mDoctors;
    }

    // Change the rating of the doctor
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

    // bookmark doctor
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
    // un-bookmark doctor
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

    // write bookmarked doctor to the backend
    public void saveBookmarkedDoctors() {

        if (!(userEmail.isEmpty())) {
            mRepository.saveBookmarkedDoctors(userEmail);
        }

    }





    // persist the doctor bookmarks
    public void getUserBookmarks(UserDataModel user, Context context) {

                ArrayList<String> bookmarkedDoctors = new ArrayList<>();
                bookmarkedDoctors = user.getBookmarkedDoctors();
                doctorsBookmarked = (ArrayList<DoctorDataModel>) mDoctors.getValue();
                if(bookmarkedDoctors != null) {
                    for (DoctorDataModel doctor : doctorsBookmarked) {
                        for (String id : bookmarkedDoctors) {

                            if (id.equals(doctor.getId())) {
                                doctor.setBookmark(true);
                                doctorItemsBookmarked.add(doctor);

                            }
                        }
                    }

                    mDoctors.setValue(doctorsBookmarked);
                }

    }


}
