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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.ViewModels.CategoryViewModel;
import com.fathom.nfs.ViewModels.DoctorsViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsSpecialities extends Fragment {


    // TAG for Debugging
    private static final String TAG = "Doctors";

    // declaring member variables
    private ArrayList<DoctorDataModel> mDoctors = new ArrayList<>();
    private RecyclerView mDoctorsSpecialityRecycler;
    private DoctorsAdapter mDoctorsAdapter;
    private ScrollView doctorsSpecialityContent;
    private NavController mNavController;
    private DoctorsViewModel mDoctorsViewModel;
    private CategoryViewModel mCategoryViewModel;
    private final int actionId= R.id.action_doctorsSpecialities_to_doctorsDetails;
    private TextView specialityQ;
    private ImageView specialityImage;
    private TextView SpecialityText;
    private int position;
    private String categoryName;
    private ImageButton backButton;



    public DoctorsSpecialities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors_specialities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        doctorsSpecialityContent = view.findViewById(R.id.doctors_specialities);
        mDoctorsSpecialityRecycler = view.findViewById(R.id.doctorsSpecialityRecyclerView);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        specialityQ = view.findViewById(R.id.specialityQuestion);
        specialityImage = view.findViewById(R.id.quesionImage);
        SpecialityText = view.findViewById(R.id.doctorsSpecialityText);
        backButton = view.findViewById(R.id.backButtonToDoctors);

        // Calling the View Model
        mDoctorsViewModel = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        mDoctorsViewModel.initDoctors();

        mDoctorsViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorDataModels) {

                Log.d("MVVM"," Adpter is being notified with any CHANGE");
                mDoctorsAdapter.notifyDataSetChanged();
                initRecycler(categoryName);

            }
        });

        mCategoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        mCategoryViewModel.initCategories();
        position = mCategoryViewModel.getPositionOfCategory();
        mCategoryViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<CategoryDataModel>>() {
            @Override
            public void onChanged(List<CategoryDataModel> categoryDataModels) {

                 CategoryDataModel category = categoryDataModels.get(position);
                 categoryName = category.getCategory();
                 specialityQ.setText("What is "+ categoryName + " ?");
                 specialityImage.setImageResource(category.getCategoryMonster());
                SpecialityText.setText(category.getCategoryDescription());



                initRecycler(categoryName);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigateUp();
            }
        });

        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(doctorsSpecialityContent, ViewCompat.LAYOUT_DIRECTION_LTR);

//        while (categoryName.equals("")) {
//        }
        initRecycler(categoryName);


    }



    private void initRecycler(String category) {

        ArrayList<DoctorDataModel> filteredDoctors = new ArrayList<>();

//        Toast.makeText(getContext(), "the array size is :"+ mDoctors.size(), Toast.LENGTH_SHORT).show();

        // setting the adapter to recycler
        mDoctors = (ArrayList<DoctorDataModel>) mDoctorsViewModel.getDoctors().getValue();
        for (DoctorDataModel doctor : mDoctors) {
            if (doctor.getSpecialty().equals(category)) {
                filteredDoctors.add(doctor);
            }
        }
        mDoctorsAdapter = new DoctorsAdapter(filteredDoctors, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDoctorsSpecialityRecycler.setAdapter(mDoctorsAdapter);
        mDoctorsSpecialityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
