package com.fathom.nfs;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.RecyclersAndAdapters.AppointmentAdapter;

import java.util.ArrayList;


public class Appointments extends Fragment {

    // Tag for debugging
    private static final String TAG = "Appointments";
    // declaring member variables
    private ArrayList<AppointmentDataModel> appointments = new ArrayList<>();
    private ScrollView appointmentsContent;
    private RecyclerView mRecyclerView;
    private AppointmentAdapter mAppointmentAdapter;

    public Appointments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Connecting the variables to the UI
        mRecyclerView = view.findViewById(R.id.appointmentRecyclerView);
        appointmentsContent = view.findViewById(R.id.appointments_Fragment);

        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(appointmentsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        // initializing the recycler and initialising it
        initRecyclerView ();

    }

    // Calling the recycler
    private void initRecyclerView (){

        Log.d(TAG, "initialising the list");

        // adding data into the data model
        AppointmentDataModel appontment1 = new AppointmentDataModel();
        appontment1.setDay("18");
        appontment1.setMonth("MAR");
        appontment1.setDoctorName("Richard Chowne");
        appontment1.setSpecialty("Shadow Therapist");
        appontment1.setTiming("6:00PM to 9:00PM");
        AppointmentDataModel appontment2 = new AppointmentDataModel(
                "19", "APR", "Arnel Rynolds",
                "Psychologist", "7:00PM to 11:00PM");
        AppointmentDataModel appontment3 = new AppointmentDataModel(
                "3", "JAN", "Abdallah Alathamneh",
                "Psychiatrist", "8:00AM to 12:00PM");
        AppointmentDataModel appontment4 = new AppointmentDataModel(
                "24", "OCT", "Dr. Norah",
                "Behavior Therapist", "6:00PM to 9:00PM");
        AppointmentDataModel appontment5 = new AppointmentDataModel(
                "7", "JUN", "Dr. Rakan",
                "Alternative Healer", "10:00AM to 1:00PM");

        // adding the members of the array list
        appointments.add(appontment1);
        appointments.add(appontment2);
        appointments.add(appontment3);
        appointments.add(appontment4);
        appointments.add(appontment5);
        // setting the adapter to recycler
        mAppointmentAdapter = new AppointmentAdapter(appointments, getContext());
        mRecyclerView.setAdapter(mAppointmentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
