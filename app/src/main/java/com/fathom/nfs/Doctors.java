package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Doctors extends Fragment {

    // TAG for Debugging
    private static final String TAG = "Doctors";

    // declaring member variables
    private ArrayList<DoctorDataModel> mDoctors = new ArrayList<>();
    private RecyclerView mDcotrosRecycler;
    private DoctorsAdapter mDoctorsAdapter;
    private ScrollView doctorsContent;





    public Doctors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorsContent = view.findViewById(R.id.doctorsContent);
        mDcotrosRecycler = view.findViewById(R.id.allDoctorsRecyclerView);
        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(doctorsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        // Calling Recycler
        initRecycler();
    }

    private void initRecycler() {


        DoctorDataModel doctor1 = new DoctorDataModel("Narjes", "Kazerooni", R.drawable.doctor2, 4.6);
        DoctorDataModel doctor2 = new DoctorDataModel("Abdallah", "Alathamneh", R.drawable.user, 4.9);
        DoctorDataModel doctor3 = new DoctorDataModel("Richard", "Chowne", R.drawable.doctor5, 4.8);

        mDoctors.add(doctor1);
        mDoctors.add(doctor2);
        mDoctors.add(doctor3);

        // setting the adapter to recycler
        mDoctorsAdapter = new DoctorsAdapter(mDoctors, getContext());
        mDcotrosRecycler.setAdapter(mDoctorsAdapter);
        mDcotrosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
