package com.fathom.nfs;


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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.RecyclersAndAdapters.ReviewAdapter;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.fathom.nfs.ViewModels.HelpLineViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.getPositionOfBookMark;
import static com.fathom.nfs.DataModels.BookmarkDataModel.isClickedFromBookmarks;


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
    private int positionOfBookmarked;
    private Button doctorLocation;
    private Button doctorChat;
    private Button doctorEmail;
    private NavController mNavController;



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
        doctorLocation = view.findViewById(R.id.doctorLocation);
        doctorChat = view.findViewById(R.id.chatButton);
        doctorEmail = view.findViewById(R.id.emailButton);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

//        doctorLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });



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

                    // Updating the UI of the Doctors details
                    firstName.setText(doctor.getDoctorFirstName());
                    lastName.setText(doctor.getDoctorLastName());
                    rating.setText(Double.toString(doctor.getRating()));
                    doctorImage.setImageResource(doctor.getImageUrl());
                }

                if (isClickedFromBookmarks()) {
                    positionOfBookmarked = getPositionOfBookMark();
                    firstName.setText(doctorItemsBookmarked.get(positionOfBookmarked).getDoctorFirstName());
                    lastName.setText(doctorItemsBookmarked.get(positionOfBookmarked).getDoctorLastName());
                    rating.setText(Double.toString(doctorItemsBookmarked.get(positionOfBookmarked).getRating()));
                    doctorImage.setImageResource(doctorItemsBookmarked.get(positionOfBookmarked).getImageUrl());
                }


            }
        });




        // Adjusting the UI to display only the overview when the user access it
        contactContent.setVisibility(View.GONE);
        reviewContent.setVisibility(View.GONE);


        // updating the UI when you click on each tab of the doctor details
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                overviewContent.setVisibility(View.GONE);
                reviewContent.setVisibility(View.GONE);
                contactContent.setVisibility(View.VISIBLE);



                contactContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mNavController.navigate(R.id.action_doctorsDetails_to_doctorLocation);
                    }
                });



            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                overviewContent.setVisibility(View.VISIBLE);
                contactContent.setVisibility(View.GONE);
                reviewContent.setVisibility(View.GONE);




            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button));
                contactUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                overviewUnderlineButton.setBackground(getResources().getDrawable(R.drawable.button_light));
                reviewContent.setVisibility(View.VISIBLE);
                contactContent.setVisibility(View.GONE);
                overviewContent.setVisibility(View.GONE);
            }
        });



        initRecycler();



    }


    // initializing the review recycler
    private void initRecycler() {

        ReviewDataModel review1 = new ReviewDataModel(4,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna.");
        ReviewDataModel review2 = new ReviewDataModel(3,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna.");
        ReviewDataModel review3 = new ReviewDataModel(5,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna.");

        mReviews.add(review1);
        mReviews.add(review2);
        mReviews.add(review3);

        mReviewAdapter = new ReviewAdapter(mReviews, getContext());
        mReviewRecycler.setAdapter(mReviewAdapter);
        mReviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));




    }


}
