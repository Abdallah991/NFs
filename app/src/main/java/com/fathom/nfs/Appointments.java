package com.fathom.nfs;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.RecyclersAndAdapters.AppointmentAdapter;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.Repositories.AppointmentRepository;
import com.fathom.nfs.ViewModels.AppointmentViewModel;
import com.fathom.nfs.ViewModels.DoctorsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.SignUpActivity.USER;


public class Appointments extends Fragment {

    // Tag for debugging
    private static final String TAG = "Appointments";
    // declaring member variables
    private ArrayList<AppointmentDataModel> appointments = new ArrayList<>();
    private ScrollView appointmentsContent;
    private RecyclerView mRecyclerView;
    private AppointmentAdapter mAppointmentAdapter;
    private AppointmentViewModel mAppointmentViewModel;
    public static String userEmail;

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

        SharedPreferences preferences = getActivity().getSharedPreferences(USER, 0);
        String email = preferences.getString("Email", "");
//        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
        userEmail = email;
        // Connecting the variables to the UI
        mRecyclerView = view.findViewById(R.id.appointmentRecyclerView);
        appointmentsContent = view.findViewById(R.id.appointments_Fragment);

        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(appointmentsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        mAppointmentViewModel =  new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
        mAppointmentViewModel.initAppointments(userEmail);
        mAppointmentViewModel.getAppointments().observe(getViewLifecycleOwner(), new Observer<List<AppointmentDataModel>>() {
            @Override
            public void onChanged(List<AppointmentDataModel> appointmentDataModels) {
                mAppointmentAdapter.notifyDataSetChanged();
                initRecyclerView();
            }
        });

        // initializing the recycler and initialising it
        initRecyclerView ();

    }

    // Calling the recycler
    private void initRecyclerView (){

        Log.d(TAG, "initialising the list");

        appointments = (ArrayList<AppointmentDataModel>) mAppointmentViewModel.getAppointments().getValue();
        mAppointmentAdapter = new AppointmentAdapter(appointments, getContext());
        mRecyclerView.setAdapter(mAppointmentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onResume() {
        super.onResume();

        AppointmentRepository instance = AppointmentRepository.getInstance();
        instance.getAppointments(userEmail);
        mAppointmentViewModel.initAppointments(userEmail);
        mAppointmentViewModel.getAppointments().observe(getViewLifecycleOwner(), new Observer<List<AppointmentDataModel>>() {
            @Override
            public void onChanged(List<AppointmentDataModel> appointmentDataModels) {
                mAppointmentAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();

    }

}
