package com.fathom.nfs;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.ScrollView;

import com.fathom.nfs.DataModels.BookArrayDataModel;
import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.BookParentAdapter;
import com.fathom.nfs.RecyclersAndAdapters.ShopItemAdapter;
import com.fathom.nfs.ViewModels.BookArrayViewModel;
import com.fathom.nfs.ViewModels.ShopItemsViewModel;

import java.util.ArrayList;
import java.util.List;


public class Shop extends Fragment {


    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private ArrayList<BookRowDataModel> bookArray = new ArrayList<>();

    private ScrollView shopContent;

    private RecyclerView mShopRecycler;
    private RecyclerView mBookRecycler;

    private ShopItemAdapter mShopItemAdapter;
    private BookParentAdapter mBookParentAdapter;

    private NavController mNavController;
    private BookArrayViewModel mBookArrayViewModel;
    private ShopItemsViewModel mShopItemsViewModel;

    private int actionToDetailedShopItem = R.id.action_shopFragment_to_shopItemDetailed;
    private int actionToDetailedBook = R.id.action_shopFragment_to_bookItemDetailed;


    public Shop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopContent = view.findViewById(R.id.shopContent);

        mShopRecycler = view.findViewById(R.id.toysRecyclerView);
        mBookRecycler = view.findViewById(R.id.booksRecyclerView);

        mBookArrayViewModel = new ViewModelProvider(requireActivity()).get(BookArrayViewModel.class);
        mBookArrayViewModel.initBookArrays();
        mBookArrayViewModel.getBookArrays().observe(getViewLifecycleOwner(), new Observer<List<BookRowDataModel>>() {
            @Override
            public void onChanged(List<BookRowDataModel> bookRowDataModels) {
                mBookParentAdapter.notifyDataSetChanged();
            }
        });

        mShopItemsViewModel = new ViewModelProvider(requireActivity()).get(ShopItemsViewModel.class);
        mShopItemsViewModel.initShopItem();

        mShopItemsViewModel.getShopItems().observe(getViewLifecycleOwner(), new Observer<List<ShopItemDataModel>>() {
            @Override
            public void onChanged(List<ShopItemDataModel> shopItemDataModels) {

                mShopItemAdapter.notifyDataSetChanged();
            }
        });

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        ViewCompat.setLayoutDirection(shopContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecyclers();




    }

    private void initRecyclers() {


        // setting the adapter to recycler
        mShopItems = (ArrayList<ShopItemDataModel>) mShopItemsViewModel.getShopItems().getValue();
        mShopItemAdapter = new ShopItemAdapter(mShopItems, getContext(), mNavController,actionToDetailedShopItem, mShopItemsViewModel);
        mShopRecycler.setAdapter(mShopItemAdapter);
        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the adapter to recycler
        bookArray = (ArrayList<BookRowDataModel>) mBookArrayViewModel.getBookArrays().getValue();
        mBookParentAdapter = new BookParentAdapter(bookArray, getContext(), mNavController,actionToDetailedBook, mBookArrayViewModel);
        mBookRecycler.setAdapter(mBookParentAdapter);
        mBookRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
