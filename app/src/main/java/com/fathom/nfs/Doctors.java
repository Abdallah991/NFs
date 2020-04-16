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
import android.widget.ScrollView;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


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
    private NavController mNavController;
    private final int actionId= R.id.action_doctors_to_doctorsDetails;
    private DoctorsViewModel mDoctorsViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();






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

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        // Calling the View Model
        mDoctorsViewModel = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        mDoctorsViewModel.initDoctors();

        mDoctorsViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorDataModels) {

                Log.d("MVVM"," Adpter is being notified with any CHANGE");
                mDoctorsAdapter.notifyDataSetChanged();
                initRecycler();
            }
        });

        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(doctorsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        // Calling Recycler
        initRecycler();
    }

    private void initRecycler() {


        // setting the adapter to recycler
        mDoctors = (ArrayList<DoctorDataModel>) mDoctorsViewModel.getDoctors().getValue();
        mDoctorsAdapter = new DoctorsAdapter(mDoctors, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDcotrosRecycler.setAdapter(mDoctorsAdapter);
        mDcotrosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
