package com.fathom.nfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;

public class SignUpConfirmActivity extends AppCompatActivity {

    private String TAG = "Confirm SIGN UP";
    private EditText email;
    private EditText confirmationCode;
    private Button confirmCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_confirm);


        email = findViewById(R.id.emailInConfirmation);
        confirmationCode = findViewById(R.id.confirmationCode);
        confirmCode = findViewById(R.id.confirmSignUp);


        confirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = email.getText().toString();
                final String code = confirmationCode.getText().toString();
                AWSMobileClient.getInstance().confirmSignUp(username, code, new Callback<SignUpResult>() {
                    @Override
                    public void onResult(final SignUpResult signUpResult) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                                if (!signUpResult.getConfirmationState()) {
                                    final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                                    Log.e(TAG,"Confirm sign-up with: " + details.getDestination());
                                    Toast.makeText(getApplicationContext(), "Wrong Confirmation code", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e(TAG,"Sign-up done.");
                                    Intent intent = new Intent(getApplicationContext(),
                                            LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Confirm sign-up error", e);
                    }
                });
            }
        });


    }
}
