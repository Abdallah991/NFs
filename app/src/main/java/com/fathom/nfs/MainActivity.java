package com.fathom.nfs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignOutOptions;
import com.fathom.nfs.DataModels.UserDataModel;
import com.fathom.nfs.ViewModels.UserViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.fathom.nfs.SignUpActivity.USER;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Initializing toolbar and navigation
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Button logOut;
    private ImageView closeDrawerButton;
    private UserViewModel mUserViewModel;
    private ImageView sliderUserImage;
    private String TAG = "HOME";
    private UserDataModel user = new UserDataModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Tying views
        toolbar = findViewById(R.id.app_bar);
        View view = findViewById(R.id.app_bar);
        ImageView image = view.findViewById(R.id.cartIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        logOut = findViewById(R.id.logOut);
        closeDrawerButton = findViewById(R.id.close_btn);

        LayoutInflater inflater = getLayoutInflater();
//        View slider = inflater.from(getApplicationContext()).inflate(R.layout.drawer_header, null);
        ViewGroup viewRoot = (ViewGroup) inflater.inflate(R.layout.drawer_header,null);

        Log.d("User Image", " " + sliderUserImage);
        sliderUserImage = viewRoot.findViewById(R.id.sliderUserImage);

        Log.d("User Image", " " + sliderUserImage);
        // Shifting the Action bar, Drawer Layout and Navigation view to the right side
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(drawerLayout, ViewCompat.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(navigationView, ViewCompat.LAYOUT_DIRECTION_LTR);
        ViewCompat.setLayoutDirection(bottomNavigationView, ViewCompat.LAYOUT_DIRECTION_LTR);
//        navigationView.setItemHorizontalPadding(100);


        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if(toolbar.getChildAt(i) instanceof ImageButton){
                toolbar.getChildAt(i).setScaleX(0.75f);
                toolbar.getChildAt(i).setScaleY(0.75f);
            }
        }

        image.setScaleX(0.75f);
        image.setScaleY(0.75f);



        setSupportActionBar(toolbar);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth.getInstance().signOut();


                Intent intent = new Intent(getApplicationContext(),
                                LoginActivity.class);
                        startActivity(intent);
                        finish();




            }
        });


        closeDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);


                }

            }
        });





        setupNavigation();


    }

    private void persistImage() {


        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.initUser(getApplicationContext());

        mUserViewModel.getUser(getApplicationContext()).observe(this, new Observer<UserDataModel>() {
            @Override
            public void onChanged(UserDataModel userDataModel) {
                user = userDataModel;

//                sliderUserImage.setImageBitmap(user.getUserImage());
                loadingImage(user);

            }
        });
    }

    // Setting Up One Time Navigation
    private void setupNavigation() {


        // Setting up the drawer navigation
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        // Setting up the bottom navigation
        NavigationUI.setupWithNavController(bottomNavigationView,
                navController);

        // Setting the title in the start to empty
        getSupportActionBar().setTitle("");

        persistImage();



    }

    @Override
    public boolean onSupportNavigateUp() {
        // reference the navigation controller when you want to navigate up
        navController.popBackStack(R.id.homeFragment, false);



        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onBackPressed() {

        // When the drawer is open and user press back, the drawer well close
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // when an menu item is selected from menu, close the drawer
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.accountSettings:
//                NavOptions navOptions = new NavOptions.Builder()
//                        .setPopUpTo(R.id.appointmentsFragment, true)
//                        .build();
                navController.navigate(R.id.accountSettingsFragment);
                break;

            case R.id.faqs:
                navController.navigate(R.id.FAQsFragment);
                break;

            case R.id.bookmarks:
                navController.navigate(R.id.bookMarksFragment);
                break;

            case R.id.aboutNfs:
                navController.navigate(R.id.aboutNfsFragment);
                break;


            case R.id.appointmentsFragment:
                navController.navigate(R.id.appointmentsFragment);
                break;


        }


        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private void loadingImage(UserDataModel userDataModel) {

        Handler myHandler;
        int SPLASH_TIME_OUT = 8000;
        myHandler = new Handler();


        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                user = userDataModel;

                // TODO: Persist the image on the slider
                sliderUserImage.setImageBitmap(user.getUserImage());
                sliderUserImage.setImageResource(R.drawable.user);


//                Toast.makeText(getApplicationContext(), "the user is " +user.getUserImage(), Toast.LENGTH_SHORT).show();



            }
        }, SPLASH_TIME_OUT);
    }



}


