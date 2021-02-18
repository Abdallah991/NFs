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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.fathom.nfs.DataModels.UserDataModel;
import com.fathom.nfs.ViewModels.UserViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
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
    private TextView userName;
    private TextView accountType;
    private String TAG = "HOME";
    private UserDataModel user = new UserDataModel();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // offline support
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
//                .build();
//        db.setFirestoreSettings(settings);


        // Tying views
        toolbar = findViewById(R.id.app_bar);
        View view = findViewById(R.id.app_bar);
        // Hiding the cart
//        ImageView cart = view.findViewById(R.id.cartIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        logOut = findViewById(R.id.logOut);
        closeDrawerButton = findViewById(R.id.close_btn);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        userName =  header.findViewById(R.id.userName);
        accountType =  header.findViewById(R.id.accountType);
        sliderUserImage = header.findViewById(R.id.sliderUserImage);



        Log.d("User Image", " " + sliderUserImage);

        Log.d("User Image", " " + sliderUserImage);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
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

        // Hiding the cart

//        cart.setScaleX(0.75f);
//        cart.setScaleY(0.75f);



        setSupportActionBar(toolbar);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                logout();


            }
        });


        closeDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);


//                     Forcing crash
//                    throw new RuntimeException("Test Crash");


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
            try {
            userName.setText(userDataModel.getFirstName());
            } catch (Exception e) {
                logout();


            }
            accountType.setText("");
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

      getImage();
    }

    private void getImage() {

        SharedPreferences prefs = getSharedPreferences(USER, MODE_PRIVATE);
        String userEmail = prefs.getString("Email", "");

//        Toast.makeText(getApplicationContext(), userEmail, Toast.LENGTH_SHORT).show();
        userImageRef = storageRef.child(userEmail+"ProfileImage.jpeg");


        userImageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image

                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                user.setUserImage(bmp);
                sliderUserImage.setImageBitmap(bmp);

                Log.d(TAG, " Loading the Image is DONE");



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, " Loading the Image Failed" + exception.getMessage());

                // Handle any errors
            }
        });

    }

    private void logout() {


        FirebaseAuth.getInstance().signOut();


        Intent intent = new Intent(getApplicationContext(),
                LoginActivity.class);
        startActivity(intent);
        finish();
    }





}


