package com.fathom.nfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {

    // init member variables
    private ViewPager mViewPager;
    private OnBoardingAdapter mOnBoardingAdapter;
    private ImageButton btnNext;
    private TextView skipView;
    private TextView appName;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        // linking variables to views
        btnNext = findViewById(R.id.next);
        skipView = findViewById(R.id.skip);
        appName = findViewById(R.id.appTitle);

        // fill List for OnBoarding
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(R.drawable.nfs_monsters03, R.string.question1, R.string.description1));
        mList.add(new ScreenItem(R.drawable.nfs_monsters07, R.string.question2, R.string.description2));
        mList.add(new ScreenItem(R.drawable.nfs_monsters08, R.string.question3, R.string.description3));


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

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());

                        if(AWSMobileClient.getInstance().isSignedIn()) {
                            Intent intent = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );


    }

    private void goToLogin() {
        // TODO: destroy the the current activity and go to Login
        Toast.makeText(this, "Go to Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),
                LoginActivity.class);
        startActivity(intent);
        finish();

    }


}
