package com.fathom.nfs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.fathom.nfs.SignUpActivity.USER;


public class LoginActivity extends AppCompatActivity {

    // declaring class variables
    private TextView appName;
    private TextView forgotPassword;
    private EditText userName;
    private EditText password;
    private Button signUp;
    private Button login;
    private FirebaseAuth mAuth;
    private final String TAG = "SIGN IN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DoctorDataModel doctor = new DoctorDataModel();
    private ShopItemDataModel shopItem = new ShopItemDataModel();
    private ArticleDataModel article = new ArticleDataModel();
    private ReviewDataModel review1 = new ReviewDataModel();
    private ReviewDataModel review2 = new ReviewDataModel();
    private ReviewDataModel review3 = new ReviewDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init member variables
        appName = findViewById(R.id.appTitle);
        forgotPassword = findViewById(R.id.forgotPassword);
        userName = findViewById(R.id.email);
        password = findViewById(R.id.lastName);
        signUp = findViewById(R.id.signUp);
        login = findViewById(R.id.login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Go to SignUp Activity
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SignUpActivity.class);
                startActivity(intent);

            }
        });

        // Go to Forget Password Activity
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignIn();
//                uploadDoctors();
//                uploadShopItems();
//                uploadArticles();
                uploadReviews();

            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e(TAG, "User is " +currentUser);

    }

    private void SignIn() {

        String username = userName.getText().toString();
        String userPassword = password.getText().toString();

        SharedPreferences userPrefs = getSharedPreferences(USER, 0);
        userPrefs.edit().putString("EMAIL", username).apply();


        if (username.isEmpty() || userPassword.isEmpty()) return;
            mAuth.signInWithEmailAndPassword(username, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String name = user.getDisplayName();
                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

    }

    // uploading the data to the backend

    private void uploadDoctors() {

        doctor.setEmail("paul.gastro@gmail.com");
        doctor.setAbout("Alternative healing specialist although I have 30 years experience, I worked in all around the world");
        doctor.setDoctorFirstName("Paul");
        doctor.setDoctorLastName("Gastro");
        doctor.setGender("male");
        doctor.setEducation("Phd in Alternative healing");
        doctor.setExperience("10 years experience all over the world");
        doctor.setSpecialty("Alternative Healing");
        db.collection("Doctors")
                .document(doctor.getEmail()).set(doctor);
    }

    private void uploadShopItems() {

        Log.d("ShopItem", "Shop Item method triggered");

        shopItem.setShopItemName("The Psychology Book");
        shopItem.setShopItemSubName("Learn more about psychology");
        shopItem.setItemDescription("This book is written by Someone");
        shopItem.setPrice("1.200");
        shopItem.setShopItemType("Book");
        db.collection("Shop Items")
                .document(shopItem.getShopItemName()).set(shopItem);
    }

    private void uploadArticles() {

        Log.d("Articles", "Articles method triggered");

        article.setArticleTitle("What will happen next");
        article.setArticleType("Community");
        article.setArticleContent("is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        article.setAuthorName("Dr. Khulood");
        article.setVideoUrl("https://www.youtube.com/watch?v=XHNHq1mC0VQ");
        db.collection("Articles")
                .document(article.getArticleTitle()).set(article);
    }

    private void uploadReviews() {

        Log.d("Reviews", "Reviews method triggered");

        review1.setRating(5f);
        review1.setReviewText("Best doctor ever");
        review1.setUserEmail("abdulla.alathamnah@gmail.com");

        review2.setRating(4f);
        review2.setReviewText("great doctor, but the appointment at the clinic was not accurate and had to wait for 3 minutes");
        review2.setUserEmail("ariel.cap@gmail.com");

        review3.setRating(1f);
        review3.setReviewText("The doctor didn't pay attention to what I said and was playing Clash of clans while I was talking, what a shitty doctor man");
        review3.setUserEmail("richard.chowne@gmail.com");

        doctor.setEmail("ahmed.ali@gmail.com");

        db.collection("Doctors")
                .document(doctor.getEmail()).collection("Reviews")
                .document(review3.getUserEmail()).set(review3);
    }

}
