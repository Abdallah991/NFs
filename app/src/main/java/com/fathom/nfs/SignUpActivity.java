package com.fathom.nfs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    // init member variables
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
        login = findViewById(R.id.sendPassword);
        signUp = findViewById(R.id.signUp);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();



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
//                    uploadUser();
//                    signUp.setEnabled(false);

                } else
                    {
                        Toast.makeText(getApplicationContext(), "Email And/or password are invalid",
                                Toast.LENGTH_SHORT).show();

                    }

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e(TAG, "User is" +currentUser);
//        signUp.setEnabled(true);
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
                                                sendVerificationEmail();
                                                Intent intent = new Intent(getApplicationContext(),
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                                                Toast.makeText(getApplicationContext(), "Verify your email to login. If you didn't find it in you inbox, check your spam email please!",
                                                        Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Oops! the email is already registered!",
                                    Toast.LENGTH_SHORT).show();
                            Dialog mDialog;
                            mDialog = new Dialog(SignUpActivity.this);
                            mDialog.setCancelable(true);
                            mDialog.setTitle("The email already registered!");
                            mDialog.show();

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



    private boolean isEmailValid(String email) {

        if (email.contains("@")) {
            user.setEmail(email);
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
//            user.setPassword(password.getText().toString());
        }

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        if (password.length() >6) {
            user.setPassword(password);
        }
        return password.length() > 6;
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "We sent you a verification Email", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {


                        }
                    }
                });

    }


    // TODO: change the color of Sign Up button to primary color when fields are properly fixed
}
