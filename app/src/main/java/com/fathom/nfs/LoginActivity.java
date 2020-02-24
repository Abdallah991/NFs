package com.fathom.nfs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;


public class LoginActivity extends AppCompatActivity {

    // declaring class variables
    private TextView appName;
    private TextView forgotPassword;
    private EditText userName;
    private EditText password;
    private Button signUp;
    private Button login;
    private final String TAG = "SIGN IN";

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

        // initialising login
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );
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


                AWSMobileClient.getInstance().signIn(userName.getText().toString(), password.getText().toString(), null, new Callback<SignInResult>() {
                    @Override
                    public void onResult(final SignInResult signInResult) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                                switch (signInResult.getSignInState()) {
                                    case DONE:
                                        Intent intent = new Intent(getApplicationContext(),
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    case SMS_MFA:
                                        Log.e(TAG ,"Please confirm sign in with SMS");
                                        break;
                                    case NEW_PASSWORD_REQUIRED:
                                        Log.e(TAG ,"Please confirm sign-in with new password.");
                                        break;
                                    default:
                                        Log.e(TAG ,"Unsupported sign-in confirmation: " + signInResult.getSignInState());
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Sign-in error", e);
                    }
                });

                

            }
        });



    }

}
