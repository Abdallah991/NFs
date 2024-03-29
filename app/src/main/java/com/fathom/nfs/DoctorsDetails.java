package com.fathom.nfs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.ReviewAdapter;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.fathom.nfs.ViewModels.ReviewViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.getPositionOfBookMark;
import static com.fathom.nfs.DataModels.BookmarkDataModel.isClickedFromBookmarks;
import static com.fathom.nfs.SignUpActivity.USER;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsDetails extends Fragment {

    private Button overviewUnderlineButton;
    private Button contactUnderlineButton;
    private Button reviewUnderlineButton;
    private TextView firstName;
    private TextView lastName;
    private TextView rating;
    private ImageView doctorImage;
    private LinearLayout overviewContent;
    private LinearLayout contactContent;
    private LinearLayout reviewContent;
    private RecyclerView mReviewRecycler;
    private ReviewAdapter mReviewAdapter;
    private ArrayList<ReviewDataModel> mReviews = new ArrayList<>();
    private int position;
    private int positionOfBookmarked;
    private TextView aboutTitle;
    private TextView aboutContent;
    private TextView educationTitle;
    private TextView educationDegree1;
    private TextView educationDegree1Description;
    private TextView experienceTitle;
    private TextView experienceContent;
    private Button doctorLocation;
    private Button doctorChat;
    private Button doctorEmail;
    private Button callButton;
    private Button writeReview;
    private String phone;
    public static String doctorEmailId;
    public static String doctorId;
    public static String gender;
    public static String doctorFullName;
    public static String appointmentSpeciality;
    private NavController mNavController;
    private Dialog mDialog;
    private ReviewViewModel mReviewViewModel;
    private ReviewDataModel review = new ReviewDataModel();
    private DoctorDataModel doctor = new DoctorDataModel();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RatingBar reviewRating;
    private EditText reviewText;
    private TextView speciality;
    private static ProgressDialog myProgressDialog;
    private static DecimalFormat df = new DecimalFormat("0.0");
    public static double lat;
    public static double longt;
    private DoctorsViewModel mDoctorsViewModel;


    public DoctorsDetails() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Trying with UI elements
        ScrollView doctorsDetailsContent = view.findViewById(R.id.doctors_details);
        Button overviewButton = view.findViewById(R.id.overview);
        overviewUnderlineButton = view.findViewById(R.id.overviewUnderline);
        Button contactButton = view.findViewById(R.id.contact);
        contactUnderlineButton = view.findViewById(R.id.contactUnderline);
        Button reviewButton = view.findViewById(R.id.reviews);
        reviewUnderlineButton = view.findViewById(R.id.reviewsUnderline);
        overviewContent = view.findViewById(R.id.overviewContent);
        contactContent = view.findViewById(R.id.contactContent);
        reviewContent = view.findViewById(R.id.reviewsContent);
        mReviewRecycler = view.findViewById(R.id.reviewsRecyclerView);
        firstName = view.findViewById(R.id.doctorFirstName);
        lastName = view.findViewById(R.id.doctorLastName);
        rating = view.findViewById(R.id.ratingValueDetailedDoctor);
        doctorImage = view.findViewById(R.id.DoctorImage);
        speciality = view.findViewById(R.id.specialty);
        aboutTitle = view.findViewById(R.id.aboutTitle);
        aboutContent = view.findViewById(R.id.aboutContent);
        educationTitle = view.findViewById(R.id.educationTitle);
        educationDegree1 = view.findViewById(R.id.educationDegree1);
        educationDegree1Description = view.findViewById(R.id.educationDegree1Description);
        experienceTitle = view.findViewById(R.id.experienceTitle);
        experienceContent = view.findViewById(R.id.experienceContent);
        doctorLocation = view.findViewById(R.id.locationButton);
        doctorChat = view.findViewById(R.id.chatButton);
        doctorEmail = view.findViewById(R.id.emailButton);
        callButton = view.findViewById(R.id.callButton);
        writeReview = view.findViewById(R.id.writeReview);
        Button bookAppointment = view.findViewById(R.id.bookAppointment);
        ImageButton backButton = view.findViewById(R.id.backButtonToDoctors);


        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mDialog = new Dialog(getContext());

        ViewCompat.setLayoutDirection(doctorsDetailsContent, ViewCompat.LAYOUT_DIRECTION_LTR);


        // Calling the view model
        mDoctorsViewModel = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        // Getting the position of the array object
        position = mDoctorsViewModel.getPositionOfDoctor();
        // Observing the data and updating the UI
        mDoctorsViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorsList) {
                // Retrieving the data model item from the list and updating the data
//                if (!isClickedFromBookmarks()) {
                DoctorDataModel doctor = doctorsList.get(position);
                // Updating the UI of the Doctors details
                firstName.setText(doctor.getDoctorFirstName());
                lastName.setText(doctor.getDoctorLastName());
                rating.setText(df.format(doctor.getRating()));
                doctorImage.setImageBitmap(doctor.getDoctorImage());
                aboutContent.setText(doctor.getAbout());
                experienceContent.setText(doctor.getExperience());
                educationDegree1.setText(doctor.getEducation());
                speciality.setText(doctor.getSpecialty() + " ");
                educationDegree1Description.setVisibility(View.GONE);
                // Setting local variables
                phone = doctor.getPhone();
                longt = doctor.getLongt();
                lat = doctor.getLat();
                doctorEmailId = doctor.getEmail();
                doctorId = doctor.getId();
                gender = doctor.getGender();
                doctorFullName = doctor.getDoctorFirstName() + " " + doctor.getDoctorLastName();
                appointmentSpeciality = doctor.getSpecialty();


                mDoctorsViewModel.getAllDoctors();
                initRecycler();

            }
        });

        mReviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        mReviewViewModel.initReviews(doctorEmailId);
        mReviewViewModel.getReviews().observe(getViewLifecycleOwner(), new Observer<List<ReviewDataModel>>() {
            @Override
            public void onChanged(List<ReviewDataModel> reviewDataModels) {

                mReviewAdapter.notifyDataSetChanged();
                initRecycler();

            }
        });


        // Adjusting the UI to display only the overview when the user access it
        contactContent.setVisibility(View.GONE);
        doctorLocation.setVisibility(View.GONE);
        doctorChat.setVisibility(View.GONE);
        doctorEmail.setVisibility(View.GONE);
        callButton.setVisibility(View.GONE);

        reviewContent.setVisibility(View.GONE);
        writeReview.setVisibility(View.GONE);
        mReviewRecycler.setVisibility(View.GONE);


        // updating the UI when you click on each tab of the doctor details
        // updating the UI for Contact
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tabs UI
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                // overview UI
                overviewContent.setVisibility(View.GONE);
                aboutTitle.setVisibility(View.GONE);
                aboutContent.setVisibility(View.GONE);
                educationTitle.setVisibility(View.GONE);
                educationDegree1.setVisibility(View.GONE);
                educationDegree1Description.setVisibility(View.GONE);
                experienceTitle.setVisibility(View.GONE);
                experienceContent.setVisibility(View.GONE);
                // review UI
                reviewContent.setVisibility(View.GONE);
                writeReview.setVisibility(View.GONE);
                mReviewRecycler.setVisibility(View.GONE);
                // contact UI
                contactContent.setVisibility(View.VISIBLE);
                doctorLocation.setVisibility(View.VISIBLE);
                doctorChat.setVisibility(View.VISIBLE);
                doctorEmail.setVisibility(View.VISIBLE);
                callButton.setVisibility(View.VISIBLE);


            }
        });
        // updating the UI for overview
        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tabs UI
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                // overview UI
                overviewContent.setVisibility(View.VISIBLE);
                aboutTitle.setVisibility(View.VISIBLE);
                aboutContent.setVisibility(View.VISIBLE);
                educationTitle.setVisibility(View.VISIBLE);
                educationDegree1.setVisibility(View.VISIBLE);
                educationDegree1Description.setVisibility(View.VISIBLE);
                experienceTitle.setVisibility(View.VISIBLE);
                experienceContent.setVisibility(View.VISIBLE);
                // contact UI
                contactContent.setVisibility(View.GONE);
                doctorLocation.setVisibility(View.GONE);
                doctorChat.setVisibility(View.GONE);
                doctorEmail.setVisibility(View.GONE);
                callButton.setVisibility(View.GONE);
                // review UI
                reviewContent.setVisibility(View.GONE);
                writeReview.setVisibility(View.GONE);
                mReviewRecycler.setVisibility(View.GONE);


            }
        });
        // updating the UI for review
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tabs UI
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                // review UI
                reviewContent.setVisibility(View.VISIBLE);
                writeReview.setVisibility(View.VISIBLE);
                mReviewRecycler.setVisibility(View.VISIBLE);
                // contact UI
                contactContent.setVisibility(View.GONE);
                doctorLocation.setVisibility(View.GONE);
                doctorChat.setVisibility(View.GONE);
                doctorEmail.setVisibility(View.GONE);
                callButton.setVisibility(View.GONE);
                // overview UI
                overviewContent.setVisibility(View.GONE);
                aboutTitle.setVisibility(View.GONE);
                aboutContent.setVisibility(View.GONE);
                educationTitle.setVisibility(View.GONE);
                educationDegree1.setVisibility(View.GONE);
                educationDegree1Description.setVisibility(View.GONE);
                experienceTitle.setVisibility(View.GONE);
                experienceContent.setVisibility(View.GONE);
                calculateDoctorsReview();
            }
        });

        // doctor location button
        doctorLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (longt != 0 || lat != 0) {
                    mNavController.navigate(R.id.action_doctorsDetails_to_doctorLocation);
                }
            }
        });
        // book appointment button
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNavController.navigate(R.id.action_doctorsDetails_to_setAppointment);

            }
        });
        // back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigateUp();
            }
        });
        // chat button
        doctorChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone != null && phone.length() > 11) {
                    PackageManager pm = getContext().getPackageManager();
                    String url = "https://api.whatsapp.com/send?phone=" + phone;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
            }
        });
        // email button
        doctorEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{doctorEmailId});
                email.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
                email.putExtra(Intent.EXTRA_TEXT, "I want to ask you about");
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });
        // call button
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone != null && (phone.length()) > 11) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }

            }
        });

        // write review button
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        // progress dialog
        myProgressDialog = new ProgressDialog(getContext());
        myProgressDialog.setProgressStyle(R.style.MyAlertDialogStyle);
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Please Wait ...");
        myProgressDialog.show();
        loadingReviews();


    }


    // initializing the review recycler
    private void initRecycler() {

        mReviews = (ArrayList<ReviewDataModel>) mReviewViewModel.getReviews().getValue();
        mReviewAdapter = new ReviewAdapter(mReviews, getContext());
        mReviewRecycler.setAdapter(mReviewAdapter);
        mReviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


    }


    // Dialog open
    private void openDialog() {

        mDialog.setContentView(R.layout.write_review_dialoug);
        TextView doctorName = mDialog.findViewById(R.id.doctorNameInDialogue);
        ImageView cancel = mDialog.findViewById(R.id.cancelReview);
        reviewRating = mDialog.findViewById(R.id.rating);
        reviewText = mDialog.findViewById(R.id.reviewText);
        Button postReview = mDialog.findViewById(R.id.postReview);

        reviewRating.setRating(3.0f);
        doctorName.setText(firstName.getText() + " " + lastName.getText());

        // post review button
        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "The Review have no content ", Toast.LENGTH_SHORT).show();
                } else {
                    uploadReviews();
                }
            }
        });

        // cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();


    }

    // Loading reviews
    private void loadingReviews() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 1000;
        myHandler = new Handler();

        Log.d("Reviews", "testing if the variable will keep the eamil value");

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mReviewViewModel.initReviews(doctorEmailId);

                mReviews = (ArrayList<ReviewDataModel>) mReviewViewModel.getReviews().getValue();
                mReviewViewModel.getReviews().observe(getViewLifecycleOwner(), new Observer<List<ReviewDataModel>>() {
                    @Override
                    public void onChanged(List<ReviewDataModel> reviewDataModels) {

                        mReviewAdapter.notifyDataSetChanged();

                        initRecycler();
                        myProgressDialog.dismiss();

                        if (longt == 0 || lat == 0) {
                            doctorLocation.setBackgroundResource(R.drawable.button_shadow);
                        } else {
                            doctorLocation.setBackgroundResource(R.drawable.button_gradient);

                        }

                        if (phone == null || (phone.length()) < 8) {

                            callButton.setBackgroundResource(R.drawable.button_shadow);
                            doctorChat.setBackgroundResource(R.drawable.button_shadow);
                        } else {
                            callButton.setBackgroundResource(R.drawable.button_gradient);
                            doctorChat.setBackgroundResource(R.drawable.button_gradient);
                        }


                    }
                });

            }
        }, SPLASH_TIME_OUT);
    }

    // upload review of doctor
    //TODO: make the call from view model
    private void uploadReviews() {

        Log.d("Reviews", "Reviews method triggered");
        SharedPreferences prefs = getActivity().getSharedPreferences(USER, MODE_PRIVATE);
        String userEmail = prefs.getString("Email", "");
        review.setRating((int) reviewRating.getRating());
        review.setReviewText(reviewText.getText().toString());
        review.setDoctorEmail(doctorEmailId);
        review.setUserEmail(userEmail);

        mReviewViewModel.addReview(review);

        // uploading review
        db.collection("Reviews")
                .document(userEmail + review.getDoctorEmail()).set(review);
        mDialog.dismiss();

    }

    // calculate and update doctor reviews
    //TODO: make the call from view model
    private void calculateDoctorsReview() {

        double numberOfReview = mReviews.size();
        double reviewSum = 0;
        double averageReview;

        for (ReviewDataModel review : mReviews) {
            reviewSum += (int) review.getRating();
        }
        averageReview = (reviewSum / numberOfReview);
        db.collection("Doctors").document(doctorId).update("rating", averageReview);
        mDoctorsViewModel.changeRating(averageReview);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
