package com.fathom.nfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    // init member variables
    private TextView appName;
    private TextView noAccount;
    private EditText email;
    private Button login;
    private Button sendPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // linking variable to view components
        appName = findViewById(R.id.appTitle);
        noAccount = findViewById(R.id.noAccount);
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        sendPassword = findViewById(R.id.sendPassword);

//        // setting the font
//        Helper.setTypeFace(this, appName);
//        Helper.setTypeFace(this, noAccount);
//        Helper.setTypeFace(this, email);
//        Helper.setTypeFace(this, login);
//        Helper.setTypeFace(this, sendPassword);


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

        // go to SignUp Activity
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SignUpActivity.class);
                // to close previous intents
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
            }
        });


    }
}
