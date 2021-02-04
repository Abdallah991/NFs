package com.fathom.nfs.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.SignUpActivity.USER;

public class UserRepository {

    /**
     * @class User Repository
     * @desription  fetching reviews from the backend
     * @date 4 feb 2021
     */
    // Creating one instance
    private static UserRepository instance;
    private String TAG = "MVVM";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<UserDataModel> User = new MutableLiveData<>();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;
    private Context mContext;

    public static UserRepository getInstance() {
        if (instance == null) {

            instance = new UserRepository();
        }

        return instance;
    }

    public MutableLiveData<UserDataModel> getUser(Context context) {

        // calling the webservice task of function
        mContext =context;
        setUser();
       MutableLiveData<UserDataModel> data;
        data = User;

        return data;
    }

    // Getting live data from webservice
    private  void setUser () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d(TAG," Loading the data is going to start");


        SharedPreferences prefs = mContext.getSharedPreferences(USER, MODE_PRIVATE);
        String docName = prefs.getString("Email", "No name");

        DocumentReference docRef = db.collection("Users").document(docName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User.setValue(documentSnapshot.toObject(UserDataModel.class));
                Log.d(TAG," Loading the data is going to start");

                getImage(User);




            }
        });

        Log.d(TAG," Loading the data is DONE");


    }

    // getting the user image
    private void getImage (MutableLiveData<UserDataModel> user) {

        SharedPreferences preferences = mContext.getSharedPreferences(USER, 0);
        String email = preferences.getString("Email", "");

            userImageRef = storageRef.child(email+"ProfileImage.jpeg");


            userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    User.getValue().setUserImage(bmp);

                    Log.d(TAG, " Loading the Image is DONE");



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, " Loading the Image Failed" + exception.getMessage());

                    // Handle any errors
                }
            });



    }


}
