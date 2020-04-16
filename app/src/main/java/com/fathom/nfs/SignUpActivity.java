package com.fathom.nfs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    // init member variables
    private TextView appName;
    private Button login;
    private Button signUp;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private final String TAG = "SIGN UP";
    private FirebaseAuth mAuth;
    private ActionCodeSettings actionCodeSettings;
    private UserDataModel user = new UserDataModel();
    public static final String USER = "User";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // linking the variables with the view components
        appName = findViewById(R.id.appTitle);
        login = findViewById(R.id.sendPassword);
        signUp = findViewById(R.id.signUp);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


//        actionCodeSettings =
//                ActionCodeSettings.newBuilder()
//                        // URL you want to redirect back to. The domain (www.example.com) for this
//                        // URL must be whitelisted in the Firebase Console.
//                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
//                        // This must be true
//                        .setHandleCodeInApp(true)
//                        .setAndroidPackageName(
//                                "com.fathom.nfs",
//                                true, /* installIfNotAvailable */
//                                "12"    /* minimumVersion */)
//                        .build();


        // go back to Login Activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                // to close previous intents
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences(USER ,Context.MODE_PRIVATE).edit();
                editor.putString("Email", email.getText().toString());
                editor.putString("FirstName", firstName.getText().toString());
                editor.putString("LastName", lastName.getText().toString());
                editor.putString("Password", password.getText().toString());
                editor.apply();

//                Toast.makeText(getApplicationContext(), email.getText().toString(), Toast.LENGTH_SHORT).show();

                if (isEmailValid(email.getText().toString())
                        && isPasswordValid(password.getText().toString())) {
                    SignUp();
                    uploadUser();
                } else
                    {
                        Toast.makeText(getApplicationContext(), "Email And/or password are invalid",
                                Toast.LENGTH_SHORT).show();

                    }
//                sendEmailVerification();

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e(TAG, "User is" +currentUser);
    }


    private void SignUp() {

        String username = email.getText().toString();
        String userPassword = password.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();

        mAuth.createUserWithEmailAndPassword(username, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName.getText().toString())
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                Intent intent = new Intent(getApplicationContext(),
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                                                Toast.makeText(getApplicationContext(), "You can Login now",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }

    private void uploadUser() {
        // Create a new user with a first and last name

        db.collection("Users")
                .document(user.getEmail()).set(user);
    }


//    private void sendEmailVerification() {
//
//        mAuth.sendSignInLinkToEmail(email.getText().toString(), actionCodeSettings)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                            Toast.makeText(getApplicationContext(), "Verification email sent.",
//                                    Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(),
//                                    LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });
//
//    }

    private boolean isEmailValid(String email) {

        if (email.contains("@")) {
            user.setEmail(email);
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setPassword(password.getText().toString());
        }

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        if (password.length() >6) {
            user.setPassword(password);
        }
        return password.length() > 6;
    }

    private void saveUserInfo() {
        String FirstName = firstName.getText().toString();
        String LastName = lastName.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
//        UserDataModel user = new UserDataModel();
//        user.setFirstName(FirstName);
//        user.setLastName(LastName);
//        user.setEmail(Email);
//        user.setPassword(Password);
        SharedPreferences userPrefs = getSharedPreferences(USER, 0);
        userPrefs.edit().putString("FIRST_NAME", FirstName).apply();
        userPrefs.edit().putString("LAST_NAME", LastName).apply();
        userPrefs.edit().putString("EMAIL", Email).apply();
        userPrefs.edit().putString("PASSWORD", Password).apply();


    }

    // TODO: change the color of Sign Up button to primary color when fields are properly fixed
}
