package com.fathom.nfs.Repositories;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.SignUpActivity.USER;

public class DoctorsRepository {

    // Creating one instance
    private static DoctorsRepository instance;
    private String TAG = "MVVM";
    private ArrayList<DoctorDataModel> mDoctorsItems = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;


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

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(TAG," Loading the data is going to start");

//        if (mDoctorsItems.isEmpty()) {
//            mDoctorsItems.add (
//                    new DoctorDataModel("Narjes", "Kazerooni", R.drawable.doctor1, 4.6)
//            );
//            mDoctorsItems.add (
//                    new DoctorDataModel("Abdallah", "Alathamneh", R.drawable.user, 4.9)
//            );
//            mDoctorsItems.add (
//                    new DoctorDataModel("Richard", "Chowne", R.drawable.doctor_s, 4.8)
//            );

        db.collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                mDoctorsItems.add(document.toObject(DoctorDataModel.class));
                                int position = 0;

                                getImage(mDoctorsItems.get(position).getEmail(), position);

                                position++;

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

            Log.d(TAG," Loading the data is DONE");

        }


    private void getImage (String email, int position) {


        userImageRef = storageRef.child("ahmed.ali@gmail.com.jpeg");


        userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mDoctorsItems.get(position).setDoctorImage(bmp);
                Log.d(TAG," Loading the Image is DONE");


//                        setImageBitmap(Bitmap.createScaledBitmap(bmp, userImage.getWidth(),
//                                userImage.getHeight(), false)));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG," Loading the Image Failed");

                // Handle any errors
            }
        });


    }
    }

