package com.fathom.nfs.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.ReviewDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.DoctorsDetails.doctorEmailId;

public class ReviewRepository {

    // Creating one instance
    private static ReviewRepository instance;
    private ArrayList<ReviewDataModel> mReviews = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;
    private String TAG = "MVVM Reviews";


    public static ReviewRepository getInstance() {

        if (instance == null) {

            instance = new ReviewRepository();
        }


        return instance;
    }

    public MutableLiveData<List<ReviewDataModel>> getReviews(String email) {

        // calling the webservice task of function
        setReviews(email);
        MutableLiveData<List<ReviewDataModel>> data = new MutableLiveData<>();
        data.setValue(mReviews);

        return data;
    }


    // Getting live data from webservice
    private  void setReviews (String email ) {


        mReviews.clear();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(TAG," Loading the data is going to start");


        db.collection("Reviews").whereEqualTo("doctorEmail", doctorEmailId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (mReviews.size() <= task.getResult().size()) {

                                    mReviews.add(document.toObject(ReviewDataModel.class));

                                }

                                Log.d(TAG, "Task size " + task.getResult().size());

                                Log.d(TAG, "Review array size " + mReviews.size());

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(TAG," Loading the data is DONE");

    }
}
