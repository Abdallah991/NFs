package com.fathom.nfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.SubscriptionType;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.core.StreamListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.SignUpActivity.USER;

public class OnBoarding extends AppCompatActivity {

    // init member variables
    private ViewPager mViewPager;
    private OnBoardingAdapter mOnBoardingAdapter;
    private ImageButton btnNext;
    private TextView skipView;
    private int position = 0;
    public static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        // linking variables to views
        btnNext = findViewById(R.id.next);
        skipView = findViewById(R.id.skip);

        // fill List for OnBoarding
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(R.drawable.monster1, R.string.question1, R.string.description1));
        mList.add(new ScreenItem(R.drawable.monster2, R.string.question2, R.string.description2));
        mList.add(new ScreenItem(R.drawable.monster3, R.string.question3, R.string.description3));


        //setup ViewPager
        mViewPager = findViewById(R.id.viewPager);
        mOnBoardingAdapter = new OnBoardingAdapter(this, mList);
        mViewPager.setAdapter(mOnBoardingAdapter);

        // next button click Listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // navigating index through view pagers
                position = mViewPager.getCurrentItem() ;
                if(position < mList.size()){
                    position++;
                    mViewPager.setCurrentItem(position);


                }
                // if index equals the last item is the list then head to Login
                if (position == mList.size()) {
                    goToLogin();

                }

            }
        });

        skipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(USER, MODE_PRIVATE);
        userEmail = prefs.getString("Email", "");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String name = user.getDisplayName();
            boolean emailVerified = user.isEmailVerified();




//            Toast.makeText(getApplicationContext(), name +" "+ emailVerified, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(intent);
        }



    }

    private void goToLogin() {
        // TODO: destroy the the current activity and go to Login
//        Toast.makeText(this, "Go to Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),
                LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String name = user.getDisplayName();
            boolean emailVerified = user.isEmailVerified();


//            Toast.makeText(getApplicationContext(), name +" "+ emailVerified, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
        }
    }
}
