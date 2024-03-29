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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.ArticleAdapter;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.RecyclersAndAdapters.ShopItemAdapter;
import com.fathom.nfs.ViewModels.ArticleViewModel;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.fathom.nfs.ViewModels.ShopItemsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.DataModels.BookmarkDataModel.articleItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.shopItemsBookmarked;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookMarks extends Fragment {


    private ScrollView bookmarksContent;
    private RecyclerView mDoctorsRecycler;
    private RecyclerView mArticlesRecycler;
    private RecyclerView mShopRecycler;
    private DoctorsAdapter mDoctorsAdapter;
    private ArticleAdapter mArticleAdapter;
    private ShopItemAdapter mShopItemAdapter;
    private NavController mNavController;
    private TextView bookmarkedTitle;
    private TextView doctorsTitleBookmark;
    private LinearLayout doctors;
    private Button allDoctors;
    private TextView bookmarkedTitle2;
    private TextView articlesBookmarkedTitle;
    private LinearLayout linearLayout3;
    private Button allArticles;
    private TextView bookmarkedTitle3;
    private TextView shopBookmarkTitle;
    private LinearLayout linearLayout4;
    private Button allShop;
    private Button filterDoctors;
    private Button filterShops;
    private Button filterArticles;
    private ArrayList<DoctorDataModel> mDoctors = new ArrayList<>();
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
//    private ArrayList<DoctorDataModel> mDoctorsBookmarked = new ArrayList<>();
    private ArticleViewModel mArticleViewModel;
    private DoctorsViewModel mDoctorsViewModel;
    private ShopItemsViewModel mShopItemsViewModel;
    private final int actionId = R.id.action_bookMarksFragment_to_doctorsDetails2;
    private int actionArticle = R.id.action_bookMarksFragment_to_articleDetailed2;
    private int actionToDetailedShopItem = R.id.action_homeFragment_to_shopItemDetailed;
    private int actionToAllDoctors = R.id.action_bookMarksFragment_to_doctors;
    private int actionToAllArticles = R.id.action_bookMarksFragment_to_articles;


    public BookMarks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_marks, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookmarksContent = view.findViewById(R.id.bookmarkContent);
        mDoctorsRecycler = view.findViewById(R.id.doctorsBookmarkRecyclerView);
        mArticlesRecycler = view.findViewById(R.id.articlesBookmarkRecyclerView);
        mShopRecycler = view.findViewById(R.id.shopBookmarkRecyclerView);
        mShopRecycler = view.findViewById(R.id.shopBookmarkRecyclerView);
        allDoctors = view.findViewById(R.id.viewAllDoctors);
        allArticles = view.findViewById(R.id.viewAllArticles);
        allShop = view.findViewById(R.id.viewAllShop);
        bookmarkedTitle = view.findViewById(R.id.bookmarkedTitle);
        doctorsTitleBookmark = view.findViewById(R.id.doctorsTitleBookmark);
        doctors = view.findViewById(R.id.linearLayout2);
        bookmarkedTitle2 = view.findViewById(R.id.bookmarkedTitle2);
        articlesBookmarkedTitle = view.findViewById(R.id.articlesBookmarkedTitle);
        linearLayout3 = view.findViewById(R.id.linearLayout3);

        bookmarkedTitle3 = view.findViewById(R.id.bookmarkedTitle3);
        shopBookmarkTitle = view.findViewById(R.id.shopBookmarkTitle);
        linearLayout4 = view.findViewById(R.id.linearLayout4);

        filterDoctors = view.findViewById(R.id.filterDoctors);
        filterArticles = view.findViewById(R.id.filterArticles);
        filterShops = view.findViewById(R.id.filterShopItems);


        filterDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDoctors.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                filterArticles.setBackgroundColor(getResources().getColor(R.color.white));
                filterShops.setBackgroundColor(getResources().getColor(R.color.white));
                filterDoctors.setTextColor(getResources().getColor(R.color.white));
                filterShops.setTextColor(getResources().getColor(R.color.colorPrimary));
                filterArticles.setTextColor(getResources().getColor(R.color.colorPrimary));

                bookmarkedTitle.setVisibility(View.VISIBLE);
                doctorsTitleBookmark.setVisibility(View.VISIBLE);
                doctors.setVisibility(View.VISIBLE);
                allDoctors.setVisibility(View.VISIBLE);

                bookmarkedTitle2.setVisibility(View.GONE);
                articlesBookmarkedTitle.setVisibility(View.GONE);
                linearLayout3.setVisibility(View.GONE);
                allArticles.setVisibility(View.GONE);

                bookmarkedTitle3.setVisibility(View.GONE);
                shopBookmarkTitle.setVisibility(View.GONE);
                linearLayout4.setVisibility(View.GONE);
                allShop.setVisibility(View.GONE);

                if (doctorItemsBookmarked.isEmpty()){

                    bookmarkedTitle.setVisibility(View.GONE);
                    doctorsTitleBookmark.setVisibility(View.GONE);
                    doctors.setVisibility(View.GONE);
                    allDoctors.setVisibility(View.GONE);

                }
            }
        });

        filterArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDoctors.setBackgroundColor(getResources().getColor(R.color.white));
                filterArticles.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                filterShops.setBackgroundColor(getResources().getColor(R.color.white));
                filterArticles.setTextColor(getResources().getColor(R.color.white));
                filterShops.setTextColor(getResources().getColor(R.color.colorPrimary));
                filterDoctors.setTextColor(getResources().getColor(R.color.colorPrimary));

                bookmarkedTitle.setVisibility(View.GONE);
                doctorsTitleBookmark.setVisibility(View.GONE);
                doctors.setVisibility(View.GONE);
                allDoctors.setVisibility(View.GONE);

                bookmarkedTitle2.setVisibility(View.VISIBLE);
                articlesBookmarkedTitle.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
                allArticles.setVisibility(View.VISIBLE);

                bookmarkedTitle3.setVisibility(View.GONE);
                shopBookmarkTitle.setVisibility(View.GONE);
                linearLayout4.setVisibility(View.GONE);
                allShop.setVisibility(View.GONE);

                if (articleItemsBookmarked.isEmpty()){

                    bookmarkedTitle2.setVisibility(View.GONE);
                    articlesBookmarkedTitle.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);
                    allArticles.setVisibility(View.GONE);

                }

            }
        });

        filterShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDoctors.setBackgroundColor(getResources().getColor(R.color.white));
                filterArticles.setBackgroundColor(getResources().getColor(R.color.white));
                filterShops.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                filterShops.setTextColor(getResources().getColor(R.color.white));
                filterDoctors.setTextColor(getResources().getColor(R.color.colorPrimary));
                filterArticles.setTextColor(getResources().getColor(R.color.colorPrimary));

                bookmarkedTitle.setVisibility(View.GONE);
                doctorsTitleBookmark.setVisibility(View.GONE);
                doctors.setVisibility(View.GONE);
                allDoctors.setVisibility(View.GONE);

                bookmarkedTitle2.setVisibility(View.GONE);
                articlesBookmarkedTitle.setVisibility(View.GONE);
                linearLayout3.setVisibility(View.GONE);
                allArticles.setVisibility(View.GONE);

                bookmarkedTitle3.setVisibility(View.VISIBLE);
                shopBookmarkTitle.setVisibility(View.VISIBLE);
                linearLayout4.setVisibility(View.VISIBLE);
                allShop.setVisibility(View.VISIBLE);

                if (shopItemsBookmarked.isEmpty()){

                    bookmarkedTitle3.setVisibility(View.GONE);
                    shopBookmarkTitle.setVisibility(View.GONE);
                    linearLayout4.setVisibility(View.GONE);
                    allShop.setVisibility(View.GONE);

                }
            }
        });





        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        allDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNavController.navigate(actionToAllDoctors);
            }
        });

        allArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNavController.navigate(actionToAllArticles);
            }
        });


        if (doctorItemsBookmarked.isEmpty()){

            bookmarkedTitle.setVisibility(View.GONE);
            doctorsTitleBookmark.setVisibility(View.GONE);
            doctors.setVisibility(View.GONE);
            allDoctors.setVisibility(View.GONE);

        }

        if (articleItemsBookmarked.isEmpty()){

            bookmarkedTitle2.setVisibility(View.GONE);
            articlesBookmarkedTitle.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.GONE);
            allArticles.setVisibility(View.GONE);

        }

        if (shopItemsBookmarked.isEmpty()){

            bookmarkedTitle3.setVisibility(View.GONE);
            shopBookmarkTitle.setVisibility(View.GONE);
            linearLayout4.setVisibility(View.GONE);
            allShop.setVisibility(View.GONE);

        }

        // Calling the View Model
        mDoctorsViewModel = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        mDoctorsViewModel.initDoctors();

        mDoctorsViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorDataModels) {

                Log.d("MVVM"," Adpter is being notified with any CHANGE");
                mDoctorsAdapter.notifyDataSetChanged();
                mDoctors = (ArrayList<DoctorDataModel>) doctorDataModels;
                if (doctorItemsBookmarked.isEmpty()) {
                    for (DoctorDataModel doctor : mDoctors) {
                        if (doctor.isBookmark()) {
                            doctorItemsBookmarked.add(doctor);
                        }
                    }
                }
                initRecycler();
            }
        });

        mArticleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        mArticleViewModel.initArticles();

        mArticleViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<List<ArticleDataModel>>() {
            @Override
            public void onChanged(List<ArticleDataModel> articleDataModels) {

                mArticles = (ArrayList<ArticleDataModel>) articleDataModels;
                mArticles = (ArrayList<ArticleDataModel>) mArticleViewModel.getArticles().getValue();
//        Toast.makeText(getContext(), mDoctors + "", Toast.LENGTH_SHORT).show();
                if (articleItemsBookmarked.isEmpty()) {
                    for (ArticleDataModel article : mArticles) {
                        if (article.isBookmark()) {
                            articleItemsBookmarked.add(article);
                        }
                    }
                }
                mArticleAdapter.notifyDataSetChanged();
//                initRecycler();
            }

        });


        ViewCompat.setLayoutDirection(bookmarksContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecycler();
    }

    @Override
    public void onStart() {
        super.onStart();

        mDoctorsRecycler.setNestedScrollingEnabled(false);
        mArticlesRecycler.setNestedScrollingEnabled(false);
        mShopRecycler.setNestedScrollingEnabled(false);

    }

    private void initRecycler() {


        // setting the adapter to recycler
//        mDoctorsBookmarked = new ArrayList<>();
//        mDoctors = (ArrayList<DoctorDataModel>) mDoctorsViewModel.getDoctors().getValue();
//        Toast.makeText(getContext(), mDoctors + "", Toast.LENGTH_SHORT).show();

//        articleItemsBookmarked = new ArrayList<>();
//        mArticles = (ArrayList<ArticleDataModel>) mArticleViewModel.getArticles().getValue();
////        Toast.makeText(getContext(), mDoctors + "", Toast.LENGTH_SHORT).show();
//        for (ArticleDataModel article : mArticles) {
//            if(article.isBookmark()) {
//                articleItemsBookmarked.add(article);
//            }
//        }
//        Toast.makeText(getContext(), mDoctorsBookmarked.size()+" ", Toast.LENGTH_SHORT).show();
        mDoctorsAdapter = new DoctorsAdapter(doctorItemsBookmarked, getContext(), mNavController, actionId, mDoctorsViewModel);
        mDoctorsRecycler.setAdapter(mDoctorsAdapter);
        mDoctorsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mArticleAdapter = new ArticleAdapter(articleItemsBookmarked, getContext(), mNavController, actionArticle, mArticleViewModel);
        mArticlesRecycler.setAdapter(mArticleAdapter);
        mArticlesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mShopItemAdapter = new ShopItemAdapter(shopItemsBookmarked, getContext(), mNavController, actionToDetailedShopItem, mShopItemsViewModel);
        mShopRecycler.setAdapter(mShopItemAdapter);
        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onResume() {
        super.onResume();
//        initRecycler();
    }
}
