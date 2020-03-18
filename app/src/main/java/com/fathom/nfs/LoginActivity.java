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
import com.fathom.nfs.DataModels.DoctorDataModel;
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
                uploadShopItems();

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

        shopItem.setShopItemName("Swimie");
        shopItem.setShopItemSubName(" A toy for pool and the beach");
        shopItem.setItemDescription("This toy helps the child to interact with his/her emotions outside or at the pool of the beach");
        shopItem.setPrice("4.720 BHD");
        shopItem.setShopItemType("Toy");
        db.collection("Shop Items")
                .document(shopItem.getShopItemName()).set(shopItem);
    }

}
