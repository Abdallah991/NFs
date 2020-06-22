package com.fathom.nfs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    private ScrollView doctorsDetailsContent;
    private Button overviewButton;
    private Button overviewUnderlineButton;
    private Button contactButton;
    private Button contactUnderlineButton;
    private Button reviewButton;
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
    private int positionOfReview;
    private int positionOfBookmarked;
    private TextView aboutTitle;
    private TextView aboutContent;
    private TextView educationTitle;
    private TextView educationDegree1;
    private TextView educationDegree1Description;
    private TextView educationDegree2;
    private TextView educationDegree2Description;
    private TextView experienceTitle;
    private TextView experienceContent;
    private Button doctorLocation;
    private Button doctorChat;
    private Button doctorEmail;
    private Button callButton;
    private Button writeReview;
    private Button bookAppointment;
    private String phone;
    public static String doctorEmailId;
    public static String gender;
    public static String doctorFullName;
    public static String appointmentSpeciality;
    private NavController mNavController;
    private ImageButton backButton;
    private Dialog mDialog;
    private ReviewViewModel mReviewViewModel;
    private ReviewDataModel review = new ReviewDataModel();
    private DoctorDataModel doctor = new DoctorDataModel();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView doctorName;
    private ImageView cancel;
    private RatingBar reviewRating;
    private EditText reviewText;
    private Button postReview;
    private TextView speciality;
    private static ProgressDialog myProgressDialog;
    private static DecimalFormat df = new DecimalFormat("0.0");








    public DoctorsDetails() {
        // Required empty public constructor
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
        doctorsDetailsContent = view.findViewById(R.id.doctors_details);
        overviewButton = view.findViewById(R.id.overview);
        overviewUnderlineButton = view.findViewById(R.id.overviewUnderline);
        contactButton = view.findViewById(R.id.contact);
        contactUnderlineButton = view.findViewById(R.id.contactUnderline);
        reviewButton = view.findViewById(R.id.reviews);
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
        educationDegree2 = view.findViewById(R.id.educationDegree2);
        educationDegree2Description = view.findViewById(R.id.educationDegree2Description);
        experienceTitle = view.findViewById(R.id.experienceTitle);
        experienceContent = view.findViewById(R.id.experienceContent);

        doctorLocation = view.findViewById(R.id.locationButton);
        doctorChat = view.findViewById(R.id.chatButton);
        doctorEmail = view.findViewById(R.id.emailButton);
        callButton = view.findViewById(R.id.callButton);

        writeReview = view.findViewById(R.id.writeReview);

        bookAppointment = view.findViewById(R.id.bookAppointment);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        backButton = view.findViewById(R.id.backButtonToDoctors);

        mDialog = new Dialog(getContext());



        ViewCompat.setLayoutDirection(doctorsDetailsContent, ViewCompat.LAYOUT_DIRECTION_LTR);


        // Calling the view model
        DoctorsViewModel model = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        // Getting the position of the array object
        position = model.getPositionOfDoctor();
        // Observing the data and updating the UI
        model.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorsList) {

                // Retrieving the data model item from the list and updating the data
                if (!isClickedFromBookmarks()) {
                    DoctorDataModel doctor = doctorsList.get(position);

//                    int reviewsNumber = doctor.getReviews().size();
//
//                    Toast.makeText(getContext(), "This doctor have "+reviewsNumber + " reviews", Toast.LENGTH_SHORT).show();
                    // Updating the UI of the Doctors details
                    firstName.setText(doctor.getDoctorFirstName());
                    lastName.setText(doctor.getDoctorLastName());
                    rating.setText(df.format(doctor.getRating()));
                    doctorImage.setImageBitmap(doctor.getDoctorImage());
                    aboutContent.setText(doctor.getAbout());
                    experienceContent.setText(doctor.getExperience());
                    educationDegree1.setText(doctor.getEducation());
                    speciality.setText(doctor.getSpecialty()+" ");
                    educationDegree1Description.setVisibility(View.GONE);
                    educationDegree2.setVisibility(View.GONE);
                    educationDegree2Description.setVisibility(View.GONE);
                    phone = doctor.getPhone();
                    doctorEmailId = doctor.getEmail();
                    gender = doctor.getGender();
                    doctorFullName = "Dr. " +doctor.getDoctorFirstName()+ " " + doctor.getDoctorLastName();
                    appointmentSpeciality = doctor.getSpecialty();

                    initRecycler();
                }

                // TODO: persist the book marks
                if (isClickedFromBookmarks()) {
                    positionOfBookmarked = getPositionOfBookMark();
                    firstName.setText(doctorItemsBookmarked.get(positionOfBookmarked).getDoctorFirstName());
                    lastName.setText(doctorItemsBookmarked.get(positionOfBookmarked).getDoctorLastName());
                    rating.setText(Double.toString(doctorItemsBookmarked.get(positionOfBookmarked).getRating()));
                    doctorImage.setImageBitmap(doctorItemsBookmarked.get(positionOfBookmarked).getDoctorImage());
                    doctorEmailId = doctorItemsBookmarked.get(positionOfBookmarked).getEmail();



                }


            }
        });

        mReviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);


        // TODO: delay the call for the Reviews
        mReviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);

//        Toast.makeText(getContext(), doctorEmailId, Toast.LENGTH_SHORT).show();

        mReviewViewModel.initReviews(doctorEmailId);
        positionOfReview = mReviewViewModel.getPositionOfReview();

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
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));

                                overviewContent.setVisibility(View.GONE);
                aboutTitle.setVisibility(View.GONE);
                aboutContent.setVisibility(View.GONE);
                educationTitle.setVisibility(View.GONE);
                educationDegree1.setVisibility(View.GONE);
                educationDegree1Description.setVisibility(View.GONE);
                educationDegree2.setVisibility(View.GONE);
                educationDegree2Description.setVisibility(View.GONE);
                experienceTitle.setVisibility(View.GONE);
                experienceContent.setVisibility(View.GONE);

                reviewContent.setVisibility(View.GONE);
                writeReview.setVisibility(View.GONE);
                mReviewRecycler.setVisibility(View.GONE);



                contactContent.setVisibility(View.VISIBLE);
                doctorLocation.setVisibility(View.VISIBLE);
                doctorChat.setVisibility(View.VISIBLE);
                doctorEmail.setVisibility(View.VISIBLE);
                callButton.setVisibility(View.VISIBLE);


//
//                contactContent.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    mNavController.navigate(R.id.action_doctorsDetails_to_doctorLocation);
//
//                    }
//                });


            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));

                overviewContent.setVisibility(View.VISIBLE);
                aboutTitle.setVisibility(View.VISIBLE);
                aboutContent.setVisibility(View.VISIBLE);
                educationTitle.setVisibility(View.VISIBLE);
                educationDegree1.setVisibility(View.VISIBLE);
                educationDegree1Description.setVisibility(View.VISIBLE);
                educationDegree2.setVisibility(View.VISIBLE);
                educationDegree2Description.setVisibility(View.VISIBLE);
                experienceTitle.setVisibility(View.VISIBLE);
                experienceContent.setVisibility(View.VISIBLE);

                                contactContent.setVisibility(View.GONE);
                doctorLocation.setVisibility(View.GONE);
                doctorChat.setVisibility(View.GONE);
                doctorEmail.setVisibility(View.GONE);
                callButton.setVisibility(View.GONE);


                                reviewContent.setVisibility(View.GONE);
                writeReview.setVisibility(View.GONE);
                mReviewRecycler.setVisibility(View.GONE);




            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));

                                reviewContent.setVisibility(View.VISIBLE);
                writeReview.setVisibility(View.VISIBLE);
                mReviewRecycler.setVisibility(View.VISIBLE);

                                contactContent.setVisibility(View.GONE);
                doctorLocation.setVisibility(View.GONE);
                doctorChat.setVisibility(View.GONE);
                doctorEmail.setVisibility(View.GONE);
                callButton.setVisibility(View.GONE);

                overviewContent.setVisibility(View.GONE);
                aboutTitle.setVisibility(View.GONE);
                aboutContent.setVisibility(View.GONE);
                educationTitle.setVisibility(View.GONE);
                educationDegree1.setVisibility(View.GONE);
                educationDegree1Description.setVisibility(View.GONE);
                educationDegree2.setVisibility(View.GONE);
                educationDegree2Description.setVisibility(View.GONE);
                experienceTitle.setVisibility(View.GONE);
                experienceContent.setVisibility(View.GONE);
                calculateDoctorsReview();
            }
        });

        doctorLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigate(R.id.action_doctorsDetails_to_doctorLocation);
            }
        });

        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNavController.navigate(R.id.action_doctorsDetails_to_setAppointment);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigateUp();
            }
        });

        doctorChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mNavController.navigate(R.id.action_doctorsDetails_to_chat);

                PackageManager pm = getContext().getPackageManager();
                try {


                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "YOUR TEXT HERE";

                    PackageInfo info =pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        doctorEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Email button pressed", Toast.LENGTH_SHORT).show();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ doctorEmailId});
                email.putExtra(Intent.EXTRA_SUBJECT, "Inquirey");
                email.putExtra(Intent.EXTRA_TEXT, "I want to ask you about");
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                startActivity(intent);
            }
        });


        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();

            }
        });


//        initRecycler();
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


    private void openDialog() {

        mDialog.setContentView(R.layout.write_review_dialoug);
        doctorName = mDialog.findViewById(R.id.doctorNameInDialogue);
        cancel = mDialog.findViewById(R.id.cancelReview);
        reviewRating = mDialog.findViewById(R.id.rating);
        reviewText = mDialog.findViewById(R.id.reviewText);
        postReview = mDialog.findViewById(R.id.postReview);

        reviewRating.setRating(3.0f);
        doctorName.setText("Dr. " + firstName.getText()+" "+ lastName.getText());

        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (reviewText.getText().toString().equals("")) {

                Toast.makeText(getContext(), "The Review have no content " , Toast.LENGTH_SHORT).show();

                }
                else {

                    uploadReviews();

                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();



            }
        });

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();


    }

    private void reviews(String email) {}

    private void loadingReviews() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 1000;
        myHandler = new Handler();

        Log.d("Reviews", "testing if the variable will keep the eamil value");

        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getContext(), "Doctor email is  " + doctorEmailId , Toast.LENGTH_SHORT).show();


                mReviewViewModel.initReviews(doctorEmailId);
                positionOfReview = mReviewViewModel.getPositionOfReview();
                mReviews = (ArrayList<ReviewDataModel>) mReviewViewModel.getReviews().getValue();
                mReviewViewModel.getReviews().observe(getViewLifecycleOwner(), new Observer<List<ReviewDataModel>>() {
                    @Override
                    public void onChanged(List<ReviewDataModel> reviewDataModels) {

                        mReviewAdapter.notifyDataSetChanged();

                initRecycler();
                myProgressDialog.dismiss();


                    }
                });

            }
        }, SPLASH_TIME_OUT);
    }


    private void uploadReviews() {

        Log.d("Reviews", "Reviews method triggered");

        SharedPreferences prefs = getActivity().getSharedPreferences(USER, MODE_PRIVATE);
        String userEmail = prefs.getString("EMAIL", "");

        review.setRating((int) reviewRating.getRating());
        review.setReviewText(reviewText.getText().toString());
        review.setDoctorEmail(doctorEmailId);
        review.setUserEmail(userEmail);

        db.collection("Reviews")
                .document(userEmail+review.getDoctorEmail()).set(review);

        mDialog.dismiss();

        Toast.makeText(getContext(), "Your Review been submitted and awaiting approval " , Toast.LENGTH_SHORT).show();

//        calculateDoctorsReview();

    }

    private void calculateDoctorsReview() {

        double numberOfReview = mReviews.size();
        double reviewSum = 0;
        double averageReview;

        for (ReviewDataModel review : mReviews) {
             reviewSum += (int)  review.getRating();
            }
        averageReview = ( reviewSum /numberOfReview );

        doctor.setDoctorFirstName((String) firstName.getText());
        doctor.setDoctorLastName((String) lastName.getText());
        doctor.setAbout((String) aboutContent.getText());
        doctor.setBookmark(false);
        doctor.setEducation((String) educationDegree1.getText());
        doctor.setEmail(doctorEmailId);
        doctor.setExperience((String) experienceContent.getText());
        doctor.setGender(gender);
        doctor.setRating(averageReview);
        doctor.setSpecialty((String) speciality.getText());
        db.collection("Doctors")
                .document(doctorEmailId).set(doctor);
        Toast.makeText(getContext(), "the Doctor average Review equals " + averageReview , Toast.LENGTH_SHORT).show();





    }






}
