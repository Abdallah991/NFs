package com.fathom.nfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    // init member variables
    private TextView appName;
    private Button login;
    private Button signUp;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // linking the variables with the view components
        appName = findViewById(R.id.appTitle);
        login = findViewById(R.id.sendPassword);
        signUp = findViewById(R.id.login);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        // setting the type face to light poppins
//        Helper.setTypeFace(this, appName);
//        Helper.setTypeFace(this, login);
//        Helper.setTypeFace(this, signUp);
//        Helper.setTypeFace(this, firstName);
//        Helper.setTypeFace(this, lastName);
//        Helper.setTypeFace(this, email);
//        Helper.setTypeFace(this, password);

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

    }

    // TODO: change the color of Sign Up button to primary color when fields are properly fixed
}
