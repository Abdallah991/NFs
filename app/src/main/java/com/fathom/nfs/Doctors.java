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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
    private SearchView mSearchView;
    // Button statuses
    private boolean femaleStatus = false;
    private boolean maleStatus = false;
    private boolean psychiatryStatus = false;
    private boolean psychologyStatus = false;
    private boolean alternativeHealingStatus = false;
    private boolean behavioralTherapyStatus = false;
    private boolean counselorStatus = false;


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
        mSearchView = view.findViewById(R.id.searchDoctors);
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
                if (!psychologyStatus) {
                    psychiatryStatus = false;
                    psychologyStatus = true;
                    alternativeHealingStatus = true;
                    behavioralTherapyStatus = false;
                    counselorStatus = false;
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
                    if (!femaleStatus) {
                        female.setBackgroundColor(getResources().getColor(R.color.white));
                        female.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }

                    // filter the recycler to only psychologists

                } else {
                    psychologyStatus = false;
                    psychology.setBackgroundColor(getResources().getColor(R.color.white));
                    psychology.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                filterDoctors();

            }
        });

        psychiatry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!psychiatryStatus) {
                    psychiatryStatus = true;
                    psychologyStatus = false;
                    alternativeHealingStatus = false;
                    behavioralTherapyStatus = false;
                    counselorStatus = false;
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
                    if (!femaleStatus) {
                        female.setBackgroundColor(getResources().getColor(R.color.white));
                        female.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    // filter the recycler to only psychiatrists
                }

                else {
                    psychiatryStatus = false;
                    psychiatry.setBackgroundColor(getResources().getColor(R.color.white));
                    psychiatry.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

                filterDoctors();


            }
        });

        alternativeHealing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alternativeHealingStatus) {
                    psychiatryStatus = false;
                    psychologyStatus = false;
                    alternativeHealingStatus = true;
                    behavioralTherapyStatus = false;
                    counselorStatus = false;
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
                    if (!femaleStatus) {
                        female.setBackgroundColor(getResources().getColor(R.color.white));
                        female.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    // filter the recycler to only Alternative Healers
                }
                else {
                    alternativeHealingStatus = false;
                    alternativeHealing.setBackgroundColor(getResources().getColor(R.color.white));
                    alternativeHealing.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                filterDoctors();

            }
        });

        behavioralTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!behavioralTherapyStatus) {
                    psychiatryStatus = false;
                    psychologyStatus = false;
                    alternativeHealingStatus = false;
                    behavioralTherapyStatus = true;
                    counselorStatus = false;
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
                    if (!femaleStatus) {
                        female.setBackgroundColor(getResources().getColor(R.color.white));
                        female.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    // filter the recycler to only behavioral Therapist
                } else {
                    behavioralTherapyStatus = false;
                    behavioralTherapy.setBackgroundColor(getResources().getColor(R.color.white));
                    behavioralTherapy.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                filterDoctors();

            }
        });

        counselor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!counselorStatus) {
                    psychiatryStatus = false;
                    psychologyStatus = false;
                    alternativeHealingStatus = false;
                    behavioralTherapyStatus = false;
                    counselorStatus = true;
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
                    if (!femaleStatus) {
                        female.setBackgroundColor(getResources().getColor(R.color.white));
                        female.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    // filter the recycler to only counselor doctors

                } else {
                    counselorStatus = false;
                    counselor.setBackgroundColor(getResources().getColor(R.color.white));
                    counselor.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                filterDoctors();
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!maleStatus) {
                    maleStatus = true;
                    femaleStatus = false;
                    male.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    male.setTextColor(getResources().getColor(R.color.white));
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    maleStatus = false;
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

                // filter the recycler to only Male doctors
                filterDoctors();


            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!femaleStatus) {
                    maleStatus = false;
                    femaleStatus = true;
                    female.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    female.setTextColor(getResources().getColor(R.color.white));
                    male.setBackgroundColor(getResources().getColor(R.color.white));
                    male.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    femaleStatus = false;
                    female.setBackgroundColor(getResources().getColor(R.color.white));
                    female.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                // filter the recycler to only Female doctors
                filterDoctors();

            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                searchDoctors(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                searchDoctors(s);


                return false;
            }
        });

        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(doctorsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        // Calling Recycler
        initRecycler();
    }


    @Override
    public void onStart() {
        super.onStart();

        mDcotrosRecycler.setNestedScrollingEnabled(false);

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



    private void filterDoctors() {
//        if(!filteredDoctors.isEmpty()) {
            filteredDoctors = new ArrayList<>();
//        }

        if (maleStatus && psychiatryStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychiatry") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        if (maleStatus && psychologyStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychology") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (maleStatus && alternativeHealingStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Alternative Healing") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (maleStatus && behavioralTherapyStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Behavioral Therapy") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (maleStatus && counselorStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Counselor") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }

        else if (femaleStatus && psychiatryStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychiatry") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (femaleStatus && psychologyStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychology") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (femaleStatus && alternativeHealingStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Alternative Healing") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (femaleStatus && behavioralTherapyStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Behavioral Therapy") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (femaleStatus && counselorStatus) {
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Counselor") && doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if(maleStatus) {
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }
//            initRecycler();

        }
        else if (femaleStatus) {
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getGender().equals("female")) {
                    filteredDoctors.add(doctor);
                }
            }
            initRecycler();

        }
        else if (psychiatryStatus) {

            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Psychiatry")) {
                    filteredDoctors.add(doctor);
                }
            }

//            Toast.makeText(getContext(),"the number of doctors is"+ filteredDoctors, Toast.LENGTH_SHORT).show();

        }
        else if (psychologyStatus) {
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Psychology")) {
                    filteredDoctors.add(doctor);
                }
            }

        }

        else if (alternativeHealingStatus) {
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Alternative Healing")) {
                    filteredDoctors.add(doctor);
                }
            }

        }

        else if (behavioralTherapyStatus) {
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Behavioral Therapy")) {
                    filteredDoctors.add(doctor);
                }
            }

        }

        else if (counselorStatus) {
            for (DoctorDataModel doctor : mDoctors) {

                if (doctor.getSpecialty().equals("Counselor")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else if (maleStatus && psychiatryStatus) {
            filteredDoctors = new ArrayList<>();
            for (DoctorDataModel doctor : mDoctors) {
                if (doctor.getSpecialty().equals("Psychiatry") && doctor.getGender().equals("male")) {
                    filteredDoctors.add(doctor);
                }
            }

        }
        else {
            filteredDoctors.addAll(mDoctors);
            initRecycler();

        }

        if(filteredDoctors.isEmpty()) {
            initFilterRecycler();
        } else {
            initFilterRecycler();
            initRecycler();
        }


    }



    private void initFilterRecycler() {
        mDoctorsAdapter = new DoctorsAdapter(filteredDoctors, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDcotrosRecycler.setAdapter(mDoctorsAdapter);
        mDcotrosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        if (filteredDoctors.isEmpty()) {
            text.setVisibility(View.INVISIBLE);

        }
    }

    private void searchDoctors(String searchQuery) {

        filteredDoctors = new ArrayList<>();
        for (DoctorDataModel doctor : mDoctors) {
            if ((doctor.getDoctorFirstName().toLowerCase()
                    +doctor.getDoctorLastName().toLowerCase())
                    .contains(searchQuery.toLowerCase())) {
                filteredDoctors.add(doctor);
            }
        }
//        Toast.makeText(getContext(), filteredDoctors.toString(), Toast.LENGTH_SHORT ).show();
        initFilterRecycler();
    }

}
