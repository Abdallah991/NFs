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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.FAQsDataModel;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.RecyclersAndAdapters.FAQsAdapter;
import com.fathom.nfs.ViewModels.CategoryViewModel;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.fathom.nfs.ViewModels.FAQsViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQs extends Fragment {


    private ArrayList<FAQsDataModel> mFAQs = new ArrayList<>();
    private RecyclerView mFAQsSectionsRecycler;
    private FAQsAdapter mFAQsAdapter;
    private ScrollView FAQsContent;
    private NavController mNavController;
    private FAQsViewModel mFAQsViewModel;
    private int actionBackToHome;
    private int position;
    private ImageButton backButton;

    public FAQs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faqs, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FAQsContent = view.findViewById(R.id.faqsContent);
        mFAQsSectionsRecycler = view.findViewById(R.id.FAQRecyclerView);
        backButton = view.findViewById(R.id.backButtonToHome);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        mFAQsViewModel = new ViewModelProvider(requireActivity()).get(FAQsViewModel.class);
        mFAQsViewModel.initFAQs();
        position = mFAQsViewModel.getPositionOfFAQ();

        mFAQsViewModel.getFAQs().observe(getViewLifecycleOwner(), new Observer<List<FAQsDataModel>>() {
            @Override
            public void onChanged(List<FAQsDataModel> faQsDataModels) {
                mFAQsAdapter.notifyDataSetChanged();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigateUp();
            }
        });


        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(FAQsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecycler();


    }

    @Override
    public void onStart() {
        super.onStart();

        mFAQsSectionsRecycler.setNestedScrollingEnabled(false);

    }

    private void initRecycler() {

        mFAQs = (ArrayList<FAQsDataModel>) mFAQsViewModel.getFAQs().getValue();
        mFAQsAdapter = new FAQsAdapter(mFAQs, getContext(), mNavController, mFAQsViewModel);
        mFAQsSectionsRecycler.setAdapter(mFAQsAdapter);
        mFAQsSectionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
