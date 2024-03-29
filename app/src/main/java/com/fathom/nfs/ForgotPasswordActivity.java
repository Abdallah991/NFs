package com.fathom.nfs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.ForgotPasswordResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    // init member variables

    private TextView noAccount;
    private EditText email;
    private Button login;
    private Button sendPassword;
    private String TAG = "SIGN-Forget Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // linking variable to view components
        noAccount = findViewById(R.id.noAccount);
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        sendPassword = findViewById(R.id.sendPassword);

        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = email.getText().toString();

                FirebaseAuth.getInstance().sendPasswordResetEmail(username)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(getApplicationContext(), "You will receive an email to reset your password", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),
                                                LoginActivity.class);
                                        startActivity(intent);

                                }
                            }
                        });

            }
        });


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
