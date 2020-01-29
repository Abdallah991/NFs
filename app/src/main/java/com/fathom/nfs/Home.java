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
import android.widget.ScrollView;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.ArticleAdapter;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.RecyclersAndAdapters.HorizontalRecyclerView;
import com.fathom.nfs.RecyclersAndAdapters.ShopItemAdapter;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.fathom.nfs.ViewModels.HelpLineViewModel;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    // TAG for Debugging
    private static final String TAG = "Recycler View";
    private static final String TAG2 = "Doctors";
    private static final String TAG3 = "Articles";
    private static final String TAG4 = "ShopItems";
    // declaring member variables
    private ArrayList<DoctorDataModel> mDoctors = new ArrayList<>();
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private RecyclerView mDcotrosRecycler;
    private RecyclerView mArticlesRecycler;
    private RecyclerView mShopRecycler;
    private DoctorsAdapter mDoctorsAdapter;
    private ArticleAdapter mArticleAdapter;
    private ShopItemAdapter mShopItemAdapter;
    private ArrayList <Integer> mImageUrls = new ArrayList<>();
    private ScrollView content;
    private NavController mNavController;
    private DoctorsViewModel mDoctorsViewModel;

    private Button viewAllDoctors;

    private final int actionId = R.id.action_homeFragment_to_doctorsDetails;

    public Home() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         content = view.findViewById(R.id.content);
         mDcotrosRecycler = view.findViewById(R.id.doctorsRecyclerView);
         mArticlesRecycler = view.findViewById(R.id.articlesRecyclerView);
         mShopRecycler = view.findViewById(R.id.shopRecyclerView);

         // Calling the View Model
        mDoctorsViewModel = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        mDoctorsViewModel.initDoctors();

        mDoctorsViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorDataModels) {

                Log.d("MVVM"," Adpter is being notified with any CHANGE");
                mDoctorsAdapter.notifyDataSetChanged();
            }
        });


        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        viewAllDoctors = view.findViewById(R.id.viewDoctors);
        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(content, ViewCompat.LAYOUT_DIRECTION_LTR);

        // Setting click listeners
        viewAllDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_homeFragment_to_doctors);
            }
        });

            // initializing the recycler
            getImages();




    }

    // adding the images
    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

//        DoctorDataModel doctor1 = new DoctorDataModel
//                ("Narjes", "Kazerooni", R.drawable.doctor2, false, 4.8 ,"Psychaitry","best doctor ever", "6 years experience");

//        if (mDoctors.isEmpty()) {
//            DoctorDataModel doctor1 = new DoctorDataModel("Narjes", "Kazerooni", R.drawable.doctor2, 4.6);
//            DoctorDataModel doctor2 = new DoctorDataModel("Abdallah", "Alathamneh", R.drawable.user, 4.9);
//            DoctorDataModel doctor3 = new DoctorDataModel("Richard", "Chowne", R.drawable.doctor5, 4.8);
//
//            mDoctors.add(doctor1);
//            mDoctors.add(doctor2);
//            mDoctors.add(doctor3);
//        }

        if (mArticles.isEmpty()) {
            ArticleDataModel article1 = new ArticleDataModel(R.drawable.autism_article, "When to Test your Child for Autism", "Dr MUNEERA");
            ArticleDataModel article2 = new ArticleDataModel(R.drawable.abuse_article, "Suicid Awareness in the Middle East", "Dr EMAD");
            ArticleDataModel article3 = new ArticleDataModel(R.drawable.suicide_article, "5 Steps to Deal with Domestic Abuse", "Dr KHULOOD");

            mArticles.add(article1);
            mArticles.add(article2);
            mArticles.add(article3);
        }

        if (mShopItems.isEmpty()) {
            ShopItemDataModel item1 = new ShopItemDataModel(R.drawable.shop_item_1, "BHD 6.000", "Meebie - For Play & Emotional Expression");
            ShopItemDataModel item2 = new ShopItemDataModel(R.drawable.shop_item_2, "BHD 7.375", "Kimochis Mixed Feelings Pack…");
            ShopItemDataModel item3 = new ShopItemDataModel(R.drawable.shop_item_3, "BHD 4.720", "Meebie - For Play & Emotional Expression");

            mShopItems.add(item1);
            mShopItems.add(item2);
            mShopItems.add(item3);
        }

        if (mImageUrls.isEmpty() ) {
            mImageUrls.add(R.drawable.psychaitry);

            mImageUrls.add(R.drawable.psychology);


            mImageUrls.add(R.drawable.behavioral_therapy);


            mImageUrls.add(R.drawable.alternative_healing);


            mImageUrls.add(R.drawable.blog);

            mImageUrls.add(R.drawable.shop_card);

        }




        // calling the horizontal recycler
        initRecyclerView();


    }

    private void initRecyclerView(){

        Log.d(TAG, "initRecyclerView: init recyclerview");

        // setting the adapter and adjusting to horizontal view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.specialties);
        recyclerView.setLayoutManager(layoutManager);
        HorizontalRecyclerView adapter = new HorizontalRecyclerView( mImageUrls, getContext());
        recyclerView.setAdapter(adapter);

        // setting the adapter to recycler
        mDoctors = (ArrayList<DoctorDataModel>) mDoctorsViewModel.getDoctors().getValue();
        mDoctorsAdapter = new DoctorsAdapter(mDoctors, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDcotrosRecycler.setAdapter(mDoctorsAdapter);
        mDcotrosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the adapter to recycler
        mArticleAdapter = new ArticleAdapter(mArticles, getContext());
        mArticlesRecycler.setAdapter(mArticleAdapter);
        mArticlesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the adapter to recycler
        mShopItemAdapter = new ShopItemAdapter(mShopItems, getContext());
        mShopRecycler.setAdapter(mShopItemAdapter);
        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
