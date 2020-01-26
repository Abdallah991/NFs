package com.fathom.nfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    // declaring class variables
    private TextView appName;
    private TextView forgotPassword;
    private EditText userName;
    private EditText password;
    private Button signUp;
    private Button login;

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
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

}
