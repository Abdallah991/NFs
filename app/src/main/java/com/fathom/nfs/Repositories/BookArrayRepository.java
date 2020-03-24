package com.fathom.nfs.Repositories;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.BookRowDataModel;
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

public class BookArrayRepository {

    // Creating one instance
    private static BookArrayRepository instance;

    private ArrayList<BookRowDataModel> mBookRows = new ArrayList<>();
    private ArrayList<ShopItemDataModel> mBooks = new ArrayList<>();
    private ArrayList<ShopItemDataModel> row1 = new ArrayList<>();
    private ArrayList<ShopItemDataModel> row2 = new ArrayList<>();
    private ArrayList<ShopItemDataModel> row3 = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;
    private String TAG = "MVVM";
    private String TAG2 = "Books Array";


    public static BookArrayRepository getInstance() {
        if (instance == null) {

            instance = new BookArrayRepository();
        }


        return instance;
    }

    public MutableLiveData<List<BookRowDataModel>> getBookRows() {

        // calling the webservice task of function
        setBookRows();
        MutableLiveData<List<BookRowDataModel>> data = new MutableLiveData<>();
        data.setValue(mBookRows);

        return data;
    }


    // Getting live data from webservice
    private  void setBookRows () {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        Log.d(TAG," Loading the data is going to start");


        db.collection("Shop Items").whereEqualTo("shopItemType","Book")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (mBooks.size() <= task.getResult().size()) {
                                    mBooks.add(document.toObject(ShopItemDataModel.class));
                                }

                                Log.d(TAG, "Task size " + task.getResult().size());

                                Log.d(TAG, "Book array size " + mBooks.size());


                                if (mBooks.size() == task.getResult().size()) {
//                                    getImage(task.getResult());
                                    Log.d(TAG, "Book array size is now " + mBooks.size());
                                    getImage(task.getResult());
                                }


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(TAG," Loading the data is DONE");
        if (mBookRows.isEmpty()) {
            loadingBooks();
//        ShopItemDataModel book1 = new ShopItemDataModel(R.drawable.book3, "BHD 7.500", "This book is great");
//        ShopItemDataModel book2 = new ShopItemDataModel(R.drawable.book1, "BHD 9.765", "Meebie - For Play And Emotional Expression");
//        ShopItemDataModel book3 = new ShopItemDataModel(R.drawable.book2, "BHD 5.243", "This book is About scaling your business");
//
//        row1.add(book3);
//        row1.add(book1);
//        row2.add(book2);
//        row2.add(book3);
//        row3.add(book1);
//        row3.add(book2);

//        BookRowDataModel booksRow1 = new BookRowDataModel(row1);
//        BookRowDataModel booksRow2 = new BookRowDataModel(row2);
//        BookRowDataModel booksRow3 = new BookRowDataModel(row3);
//
//
//
//            mBookRows.add (booksRow1);
//            mBookRows.add (booksRow2);
//            mBookRows.add (booksRow3);

//            for (int i = 0; i < 6; i++) {
//                if (i == 0 || i == 1) {
//                    row1.add(mBooks.get(i));
//                }
//                if (i == 2 || i == 3) {
//                    row2.add(mBooks.get(i));
//                }
//                if (i == 4 || i == 5) {
//                    row3.add(mBooks.get(i));
//                }
//            }
//
//            Log.d(TAG2, "row 1 have " + row1.size());
//            Log.d(TAG2, "row 2have " + row2.size());
//            Log.d(TAG2, "row 3 have " + row3.size());
//            BookRowDataModel booksRow1 = new BookRowDataModel(row1);
//            BookRowDataModel booksRow2 = new BookRowDataModel(row2);
//            BookRowDataModel booksRow3 = new BookRowDataModel(row3);
//
//            mBookRows.add (booksRow1);
//            mBookRows.add (booksRow2);
//            mBookRows.add (booksRow3);




        }
    }

    private void loadingBooks() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 5000;
        myHandler = new Handler();

        Log.d(TAG2, "loading Recycler been called");

        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {



                for (int i = 0; i < 6; i++) {
                    if (i == 0 || i == 1) {
                        row1.add(mBooks.get(i));
                    }
                    if (i == 2 || i == 3) {
                        row2.add(mBooks.get(i));
                    }
                    if (i == 4 || i == 5) {
                        row3.add(mBooks.get(i));
                    }
                }

                Log.d(TAG2, "row 1 have " + row1.size());
                Log.d(TAG2, "row 2 have " + row2.size());
                Log.d(TAG2, "row 3 have " + row3.size());
                BookRowDataModel booksRow1 = new BookRowDataModel(row1);
                BookRowDataModel booksRow2 = new BookRowDataModel(row2);
                BookRowDataModel booksRow3 = new BookRowDataModel(row3);

                mBookRows.add (booksRow1);
                mBookRows.add (booksRow2);
                mBookRows.add (booksRow3);



            }
        }, SPLASH_TIME_OUT);
    }

    private void getImage (QuerySnapshot query) {



        for (int position = 0; position < query.size(); position++) {

            userImageRef = storageRef.child(mBooks.get(position).getShopItemName()+".png");


            int shopItemPosition = position;
            userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mBooks.get(shopItemPosition).setShopItemImage(bmp);

                    Log.d(TAG2, " Loading the Image is DONE");



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG2, " Loading the Image Failed" + exception.getMessage());

                    // Handle any errors
                }
            });
        }


    }
}
