package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.RecyclersAndAdapters.HelpLineRecycler;
import com.fathom.nfs.ViewModels.HelpLineViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpLines extends Fragment {

    // TAG for Debugging
    private static final String TAG = "HelpLines";

    // Declaring the variables
    private ArrayList<Integer> helpLineImages = new ArrayList<>();
    private ScrollView helpLinesContent;
    private RecyclerView mRecyclerView;
    private NavController mNavController;
    private HelpLineViewModel mHelpLineViewModel;


    public HelpLines() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_lines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Connecting the variables to the UI
        mRecyclerView = view.findViewById(R.id.helpLinesRecyclerView);
        helpLinesContent = view.findViewById(R.id.helpLines);

        mHelpLineViewModel = ViewModelProviders.of(this).get(HelpLineViewModel.class);

        mHelpLineViewModel.getHelpLines().observe(getViewLifecycleOwner(), new Observer<List<HelpLinesDataModel>>() {
            @Override
            public void onChanged(List<HelpLinesDataModel> helpLinesDataModels) {

            }
        });
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(helpLinesContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        // initializing the recycler and initialising it
        initHelpLines();


    }

    // Adding the image content of the views and calling the recycler
    private void initHelpLines () {

        Log.d(TAG, "initialising the list");

        if (helpLineImages.isEmpty()) {
            helpLineImages.add(R.drawable.suicide);
            helpLineImages.add(R.drawable.abuse);
            helpLineImages.add(R.drawable.depression);
        }

        initRecyclerView();

    }


    // Calling the recycler
    private void initRecyclerView (){

        Log.d(TAG, "calling the recycler view");

        // TODO: adjust the data model and recycler viewer.
        // setting the adapter to recycler
        HelpLineRecycler adapter = new HelpLineRecycler(helpLineImages, getContext(),mNavController);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
