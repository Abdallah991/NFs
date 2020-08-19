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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private Button psychiatry;
    private Button psychology;
    private Button alternativeHealing;
    private Button behavioralTherapy;
    private Button counselor;
    private Button male;
    private Button female;
    private ArrayList<DoctorDataModel> filteredDoctors = new ArrayList<>();
    private ImageView monster;
    private TextView text;
    // Button statuses
    private boolean femaleStatus = false;
    private boolean maleStatus = false;
//    private boolean psychiatryStatus = false;
//    private boolean psychologyStatus = false;
//    private boolean femaleStatus = false;
//    private boolean femaleStatus = false;


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
        psychiatry = view.findViewById(R.id.psychiatryFilter);
        psychology = view.findViewById(R.id.psychologyFilter);
        alternativeHealing = view.findViewById(R.id.alternativeHealingFilter);
        behavioralTherapy = view.findViewById(R.id.behavioralTherapyFilter);
        counselor = view.findViewById(R.id.counselorFilter);
        male = view.findViewById(R.id.maleFilter);
        female = view.findViewById(R.id.femaleFilter);
        monster = view.findViewById(R.id.doctorsMonster);
        text = view.findViewById(R.id.newIn3);
        text.setVisibility(View.INVISIBLE);

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

        psychology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psychology.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                psychology.setTextColor(getResources().getColor(R.color.white));
                psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                counselor.setBackgroundColor(getResources().getColor(R.color.white));
                counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (!maleStatus) {
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                if(!femaleStatus) {
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

                // filter the recycler to only psychologists
                filterPsychology();
            }
        });

        psychiatry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psychiatry.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                psychiatry.setTextColor(getResources().getColor(R.color.white));
                psychology.setBackgroundColor(getResources().getColor(R.color.white));
                psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                counselor.setBackgroundColor(getResources().getColor(R.color.white));
                counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (!maleStatus) {
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                if(!femaleStatus) {
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                // filter the recycler to only psychiatrists
                filterPsychiatry();
            }
        });

        alternativeHealing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setTextColor(getResources().getColor(R.color.white));
                psychology.setBackgroundColor(getResources().getColor(R.color.white));
                psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                counselor.setBackgroundColor(getResources().getColor(R.color.white));
                counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (!maleStatus) {
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                if(!femaleStatus) {
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                // filter the recycler to only Alternative Healers
                filterAlternativeHealing();
            }
        });

        behavioralTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.white));
                psychology.setBackgroundColor(getResources().getColor(R.color.white));
                psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                counselor.setBackgroundColor(getResources().getColor(R.color.white));
                counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (!maleStatus) {
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                if(!femaleStatus) {
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                // filter the recycler to only behavioral Therapist
                filterBehavioralTherapy();
            }
        });

        counselor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counselor.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                counselor.setTextColor(getResources().getColor(R.color.white));
                psychology.setBackgroundColor(getResources().getColor(R.color.white));
                psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (!maleStatus) {
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                if(!femaleStatus) {
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                // filter the recycler to only counselor doctors
                filterCounselor();
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                male.setTextColor(getResources().getColor(R.color.white));
                psychology.setBackgroundColor(getResources().getColor(R.color.white));
                psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                counselor.setBackgroundColor(getResources().getColor(R.color.white));
                counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                female.setBackgroundColor(getResources().getColor(R.color.white));
                female.setTextColor(getResources().getColor(R.color.colorPrimary));

                // filter the recycler to only Male doctors
                maleStatus = true;
                femaleStatus = false;
                filterMale();


            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                female.setTextColor(getResources().getColor(R.color.white));
                psychology.setBackgroundColor(getResources().getColor(R.color.white));
                psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                counselor.setBackgroundColor(getResources().getColor(R.color.white));
                counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                male.setBackgroundColor(getResources().getColor(R.color.white));
                male.setTextColor(getResources().getColor(R.color.colorPrimary));
                maleStatus = false;
                femaleStatus = true;
                // filter the recycler to only Female doctors
                filterFemale();

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
        if(filteredDoctors.isEmpty()) {
            filteredDoctors = mDoctors;
        }

        mDoctorsAdapter = new DoctorsAdapter(filteredDoctors, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDcotrosRecycler.setAdapter(mDoctorsAdapter);
        mDcotrosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void filterPsychiatry() {

            if (!filteredDoctors.isEmpty()) {
                filteredDoctors = new ArrayList<>();
            }
            if (!maleStatus || !femaleStatus) {
                filteredDoctors = new ArrayList<>();
                for (DoctorDataModel doctor : mDoctors) {

                    if (doctor.getSpecialty().equals("Psychiatry")) {
                        filteredDoctors.add(doctor);
                    }
                }
            }

        if (maleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychiatry") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        if (femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Psychiatry") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        initFilterRecycler();
    }

    // filter code
    private void filterPsychology() {
        if (!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
        }
        if (!maleStatus || !femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Psychology")) {
                    filteredDoctors.add(doctor);
                }
            }
        }

        if (maleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychology") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        if (femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Psychology") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
       initFilterRecycler();
    }

    private void filterAlternativeHealing() {
        if (!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
        }
        if (!maleStatus || !femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Alternative Healing")) {
                    filteredDoctors.add(doctor);
                }
            }
        }

        if (maleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Alternative Healing") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        if (femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Alternative Healing") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        initFilterRecycler();

    }

    private void filterBehavioralTherapy() {
        if (!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
        }
        if (!maleStatus || !femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Behavioral Therapy")) {
                    filteredDoctors.add(doctor);
                }
            }
        }

        if (maleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Behavioral Therapy") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        if (femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Behavioral Therapy") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        initFilterRecycler();
    }

    private void filterCounselor() {
        if (!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
        }
        if (!maleStatus || !femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Counselor")) {
                    filteredDoctors.add(doctor);
                }
            }
        }

        if (maleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Counselor") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        if (femaleStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Counselor") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        initFilterRecycler();
    }

    private void filterFemale() {
        if(!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
        }
        for (DoctorDataModel doctor : mDoctors) {

            if (doctor.getGender().equals("female")) {
                filteredDoctors.add(doctor);
            }
        }
        initRecycler();
    }

    private void filterMale() {
        if(!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
        }
        for (DoctorDataModel doctor : mDoctors) {

            if (doctor.getGender().equals("male")) {
                filteredDoctors.add(doctor);
            }
        }
        initRecycler();
    }

    private void initFilterRecycler() {
        mDoctorsAdapter = new DoctorsAdapter(filteredDoctors, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDcotrosRecycler.setAdapter(mDoctorsAdapter);
        mDcotrosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        if (filteredDoctors.isEmpty()) {
            text.setVisibility(View.INVISIBLE);

        }
    }
}
