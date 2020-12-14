package com.fathom.nfs;

import androidx.annotation.NonNull;
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
import com.amazonaws.mobile.client.results.ForgotPasswordResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

                if (NewPassword.equals(NewPasswordConfirmation)) {


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updatePassword(NewPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                        Toast.makeText(getApplicationContext(), "The passwords has been updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),
                                                LoginActivity.class);
                                        startActivity(intent);


                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(), "The passwords don't match", Toast.LENGTH_SHORT).show();
                }
//
            }
        });
    }
}
