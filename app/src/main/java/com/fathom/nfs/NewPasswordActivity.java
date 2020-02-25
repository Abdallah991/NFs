package com.fathom.nfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.ForgotPasswordResult;

public class NewPasswordActivity extends AppCompatActivity {

    private String TAG = "SIGN-Forget Password";
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button sendPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmationCodePassword);
        sendPassword = findViewById(R.id.changePassword);

        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String NewPassword = newPassword.getText().toString();
                final String NewPasswordConfirmation = confirmNewPassword.getText().toString();


                AWSMobileClient.getInstance().confirmForgotPassword(NewPassword, NewPasswordConfirmation, new Callback<ForgotPasswordResult>() {
                    @Override
                    public void onResult(final ForgotPasswordResult result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "forgot password state: " + result.getState());
                                switch (result.getState()) {
                                    case DONE:
                                        Log.e(TAG,"Password changed successfully");
                                        Intent intent = new Intent(getApplicationContext(),
                                                LoginActivity.class);
                                        startActivity(intent);
                                        break;
                                    default:
                                        Log.e(TAG, "un-supported forgot password state");
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "forgot password error", e);
                    }
                });
            }
        });
    }
}
