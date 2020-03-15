package com.fathom.nfs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignOutOptions;
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
    private ImageView sliderUserImage;
    private String TAG = "HOME";


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
        sliderUserImage = findViewById(R.id.sliderUserImage);

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


        // naming the path as email
        SharedPreferences prefs = getSharedPreferences(USER, MODE_PRIVATE);
        String docName = prefs.getString("EMAIL", "No name");

         FirebaseStorage storage;
        StorageReference storageRef;
        StorageReference userImageRef;

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        userImageRef = storageRef.child(docName);


        Log.d(TAG, "Persist image called");

        userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image

                Log.d(TAG, "Success");

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // TODO: Set a way where you save an image locally
//                sliderUserImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, sliderUserImage.getWidth(),
//                        sliderUserImage.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Log.d(TAG, "Failure");

                // Handle any errors
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





}


