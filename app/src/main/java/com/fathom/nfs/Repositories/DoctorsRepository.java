package com.fathom.nfs.Repositories;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.OnBoarding.userEmail;
import static com.fathom.nfs.SignUpActivity.USER;


public class DoctorsRepository {
    /**
     * @class doctor Repository
     * @desription  fetching doctors from the backend
     * @date 4 feb 2021
     */
    // Creating one instance
    private static DoctorsRepository instance;
    private static String TAG = "MVVMDoctors";
    private ArrayList<DoctorDataModel> mDoctorsItems = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;



    // Getting an instance of the Repo
    public static DoctorsRepository getInstance() {
        if (instance == null) {

            Log.d(TAG, " getting static instance of the Repo.");
            instance = new DoctorsRepository();
        }

        Log.d(TAG, " returning the existing static instance of the Repo.");

        return instance;
    }

    // Getting doctors from backend
    public MutableLiveData<List<DoctorDataModel>> getDoctors() {
        getUser();
        setDoctorsItems();
        MutableLiveData<List<DoctorDataModel>> data = new MutableLiveData<>();
        data.setValue(mDoctorsItems);

        return data;
    }


    // Getting live data from backend
    private void setDoctorsItems() {


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(TAG, " Loading the data is going to start");

        db.collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "call is successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (mDoctorsItems.size() < task.getResult().size()) {
                                    DoctorDataModel doctor = document.toObject(DoctorDataModel.class);
                                    doctor.setId(document.getId());
                                    mDoctorsItems.add(doctor);

                                }

                                Log.d(TAG, "Doctor array size " + mDoctorsItems.size());

                                if (mDoctorsItems.size() == task.getResult().size()) {
                                    getImage(task.getResult());
                                }


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(TAG, " Loading the data is DONE");




    }


    // Getting image for each doctor
    private void getImage(QuerySnapshot query) {


        for (int position = 0; position < query.size(); position++) {

//            Log.d(TAG, " Loading the Image starting");

            validatingImageReference(position);


            int doctorPosition = position;
            userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mDoctorsItems.get(doctorPosition).setDoctorImage(bmp);

                    Log.d(TAG, " Loading the Image is DONE");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, " Loading the Image Failed" + exception.getMessage());
                }
            });
        }


    }

    // validating image path
    private void validatingImageReference(int position) {

        if (mDoctorsItems.get(position).getImagePath() == null || mDoctorsItems.get(position).getImagePath().equals("")) {
            userImageRef = storageRef.child(mDoctorsItems.get(position).getEmail() + ".jpg");
        } else {
            userImageRef = storageRef.child(mDoctorsItems.get(position).getImagePath());
        }
    }

    // bookmark and un-bookmark doctors
    public void saveBookmarkedDoctors(String email) {
        List<String> doctorIDs = new ArrayList<String>();
        for (DoctorDataModel doctor: doctorItemsBookmarked) {
            doctorIDs.add(doctor.getId());
        }
        db.collection("Users").document(email).update("bookmarkedDoctors",doctorIDs );
    }


    private void getUser() {



    }

}

