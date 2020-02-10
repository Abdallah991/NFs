package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
    private LinearLayout doctors;
    private LinearLayout articles;
    private LinearLayout shop;
    private Button allDoctors;
    private Button allArticles;
    private Button allShop;
    private ArticleViewModel mArticleViewModel;
    private DoctorsViewModel mDoctorsViewModel;
    private ShopItemsViewModel mShopItemsViewModel;
    private final int actionId = R.id.action_homeFragment_to_doctorsDetails;
    private int actionArticle = R.id.action_homeFragment_to_articleDetailed2;
    private int actionToDetailedShopItem = R.id.action_homeFragment_to_shopItemDetailed;


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


        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);



        ViewCompat.setLayoutDirection(bookmarksContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecycler();
    }

    private void initRecycler() {


        // setting the adapter to recycler
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


}
