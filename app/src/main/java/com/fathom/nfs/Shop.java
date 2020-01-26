package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.fathom.nfs.DataModels.BookArrayDataModel;
import com.fathom.nfs.DataModels.BookDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.BookParentAdapter;
import com.fathom.nfs.RecyclersAndAdapters.ShopItemAdapter;

import java.util.ArrayList;


public class Shop extends Fragment {


    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private ArrayList<BookDataModel> mBooks = new ArrayList<>();
    private ArrayList<BookArrayDataModel> booksArray = new ArrayList<>();

    private ScrollView shopContent;

    private RecyclerView mShopRecycler;
    private RecyclerView mBookRecycler;

    private ShopItemAdapter mShopItemAdapter;
    private BookParentAdapter mBookParentAdapter;


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

        ViewCompat.setLayoutDirection(shopContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecyclers();

    }

    private void initRecyclers() {

        ShopItemDataModel item1 = new ShopItemDataModel(R.drawable.shop_item_1, "BHD 6.000", "Meebie - For Play & Emotional Expression");
        ShopItemDataModel item2 = new ShopItemDataModel(R.drawable.shop_item_2, "BHD 7.375", "Kimochis Mixed Feelings Pack…");
        ShopItemDataModel item3 = new ShopItemDataModel(R.drawable.shop_item_3, "BHD 4.720", "Meebie - For Play & Emotional Expression");

        mShopItems.add(item1);
        mShopItems.add(item2);
        mShopItems.add(item3);


        BookDataModel book1 = new BookDataModel(R.drawable.book3, "BHD 7.500", "This book is great");
        BookDataModel book2 = new BookDataModel(R.drawable.book1, "BHD 9.765", "Meebie - For Play And Emotional Expression");
        BookDataModel book3 = new BookDataModel(R.drawable.book2, "BHD 5.243", "This book is About scaling your business");

        mBooks.add(book1);
        mBooks.add(book2);
        mBooks.add(book3);

        // setting the adapter to recycler
        mShopItemAdapter = new ShopItemAdapter(mShopItems, getContext());
        mShopRecycler.setAdapter(mShopItemAdapter);
        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the adapter to recycler
        mBookParentAdapter = new BookParentAdapter(mBooks, getContext());
        mBookRecycler.setAdapter(mBookParentAdapter);
        mBookRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
