package com.fathom.nfs.Repositories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.ArticleDataModel;
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

public class ArticlesRepository {

    // Creating one instance
    private static ArticlesRepository instance;
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
    private String TAG = "Articles";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;



    public static ArticlesRepository getInstance() {

        if (instance == null) {

            instance = new ArticlesRepository();
        }


        return instance;
    }

    public MutableLiveData<List<ArticleDataModel>> getArticles() {

        // calling the webservice task of function
        setArticles();
        MutableLiveData<List<ArticleDataModel>> data = new MutableLiveData<>();
        data.setValue(mArticles);

        return data;
    }


    // Getting live data from webservice
    private  void setArticles () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        if (mArticles.isEmpty()) {


            Log.d(TAG," Loading the data is going to start");


            db.collection("Articles")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    if (mArticles.size() <= task.getResult().size()) {
                                        mArticles.add(document.toObject(ArticleDataModel.class));
                                    }

                                    Log.d(TAG, "Task size " + task.getResult().size());

                                    Log.d(TAG, "Articles array size " + mArticles.size());


                                    if (mArticles.size() == task.getResult().size()) {
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


    }

    private void getImage (QuerySnapshot query) {



        for (int position = 0; position < query.size(); position++) {

            // old way of getting the image
//            userImageRef = storageRef.child(mArticles.get(position).getArticleTitle()+".png");

            userImageRef = storageRef.child(mArticles.get(position).getImagePath());



            int articlePosition = position;
            userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mArticles.get(articlePosition).setArticleImage(bmp);

                    Log.d(TAG, " Loading the Article Image is DONE");

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
