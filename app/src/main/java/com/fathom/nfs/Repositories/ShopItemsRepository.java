package com.fathom.nfs.Repositories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
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

public class ShopItemsRepository {

    // Creating one instance
    private static ShopItemsRepository instance;
    private String TAG = "MVVM";
    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;


    public static ShopItemsRepository getInstance() {
        if (instance == null) {

            instance = new ShopItemsRepository();
        }

        return instance;
    }

    public MutableLiveData<List<ShopItemDataModel>> getShopItems() {

        // calling the webservice task of function
        setShopItems();
        MutableLiveData<List<ShopItemDataModel>> data = new MutableLiveData<>();
        data.setValue(mShopItems);

        return data;
    }


    // Getting live data from webservice
    private  void setShopItems () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Log.d("MVVM"," Loading the data is going to start");


            db.collection("Shop Items")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    if (mShopItems.size() <= task.getResult().size()) {
                                        mShopItems.add(document.toObject(ShopItemDataModel.class));
                                    }

                                    Log.d(TAG, "Task size " + task.getResult().size());

                                    Log.d(TAG, "Doctor array size " + mShopItems.size());


                                    if (mShopItems.size() == task.getResult().size()) {
                                        getImage(task.getResult());
                                    }


                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

            Log.d(TAG," Loading the data is DONE");


        }


    private void getImage (QuerySnapshot query) {



        for (int position = 0; position < query.size(); position++) {

            userImageRef = storageRef.child(mShopItems.get(position).getShopItemName()+".png");


            int shopItemPosition = position;
            userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mShopItems.get(shopItemPosition).setShopItemImage(bmp);

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
}
