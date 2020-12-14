package com.fathom.nfs;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.BookArrayDataModel;
import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.BookParentAdapter;
import com.fathom.nfs.RecyclersAndAdapters.ShopItemAdapter;
import com.fathom.nfs.ViewModels.BookArrayViewModel;
import com.fathom.nfs.ViewModels.ShopItemsViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.ShopItemDetailed.mShopItemDataModel;


public class Shop extends Fragment {


    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private ArrayList<ShopItemDataModel> limitedShopItems = new ArrayList<>();
    private ArrayList<BookRowDataModel> bookArray = new ArrayList<>();
    private static ShopItemDataModel featured;

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

    private CardView featuredCard;
    private ImageView featuredImage;
    private TextView featuredTitle;
    private TextView featuredSubTitle;
    private TextView featuredPrice;

    private Button toys;
    private Button viewToys;
    private Button books;
    private Button viewBooks;
    private Button merchandise;

    private TextView newInToys;
    private TextView newInBooks;
    private TextView toysText;
    private TextView booksText;

    private String TAG2 = "Books Array";


    public Shop( ) {


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

        featuredCard = view.findViewById(R.id.shopItemCard);
        featuredImage = view.findViewById(R.id.toyImage);
        featuredTitle = view.findViewById(R.id.toyTitle);
        featuredSubTitle = view.findViewById(R.id.toySubtitle);
        featuredPrice = view.findViewById(R.id.toyPrice);

        toys = view.findViewById(R.id.toysFilter);
        viewToys = view.findViewById(R.id.viewToys);
        books = view.findViewById(R.id.booksFilter);
        viewBooks = view.findViewById(R.id.viewBooks);
        merchandise = view.findViewById(R.id.merchandiseFilter);

        newInToys = view.findViewById(R.id.newInToys);
        newInBooks = view.findViewById(R.id.newInBooks);
        toysText = view.findViewById(R.id.toys);
        booksText = view.findViewById(R.id.books);


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


        featuredCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // so Detailed shop will display featured
                mShopItemDataModel = featured;
                mNavController.navigate(actionToDetailedShopItem);
            }
        });

        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toys.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                toys.setTextColor(getResources().getColor(R.color.white));
                books.setBackgroundColor(getResources().getColor(R.color.white));
                books.setTextColor(getResources().getColor(R.color.colorPrimary));
                merchandise.setBackgroundColor(getResources().getColor(R.color.white));
                merchandise.setTextColor(getResources().getColor(R.color.colorPrimary));

                mShopRecycler.setVisibility(View.VISIBLE);
                featuredCard.setVisibility(View.VISIBLE);
                mBookRecycler.setVisibility(View.GONE);
                viewBooks.setVisibility(View.GONE);
                newInBooks.setVisibility(View.GONE);
                booksText.setVisibility(View.GONE);
                newInToys.setVisibility(View.VISIBLE);
                toysText.setVisibility(View.VISIBLE);
                viewToys.setVisibility(View.VISIBLE);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                books.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                books.setTextColor(getResources().getColor(R.color.white));
                toys.setBackgroundColor(getResources().getColor(R.color.white));
                toys.setTextColor(getResources().getColor(R.color.colorPrimary));
                merchandise.setBackgroundColor(getResources().getColor(R.color.white));
                merchandise.setTextColor(getResources().getColor(R.color.colorPrimary));

                mShopRecycler.setVisibility(View.GONE);
                featuredCard.setVisibility(View.GONE);
                mBookRecycler.setVisibility(View.VISIBLE);
                viewBooks.setVisibility(View.VISIBLE);
                newInBooks.setVisibility(View.VISIBLE);
                booksText.setVisibility(View.VISIBLE);
                newInToys.setVisibility(View.GONE);
                toysText.setVisibility(View.GONE);
                viewToys.setVisibility(View.GONE);

            }
        });

        merchandise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                merchandise.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                merchandise.setTextColor(getResources().getColor(R.color.white));
                books.setBackgroundColor(getResources().getColor(R.color.white));
                books.setTextColor(getResources().getColor(R.color.colorPrimary));
                toys.setBackgroundColor(getResources().getColor(R.color.white));
                toys.setTextColor(getResources().getColor(R.color.colorPrimary));

                mShopRecycler.setVisibility(View.GONE);
                mBookRecycler.setVisibility(View.GONE);
                featuredCard.setVisibility(View.GONE);
                viewBooks.setVisibility(View.GONE);
                newInBooks.setVisibility(View.GONE);
                booksText.setVisibility(View.GONE);
                newInToys.setVisibility(View.GONE);
                toysText.setVisibility(View.GONE);
                viewToys.setVisibility(View.GONE);


            }
        });

        loadingRecycler();


    }

    private void initRecyclers() {


        // setting the adapter to recycler
        mShopItems = (ArrayList<ShopItemDataModel>) mShopItemsViewModel.getShopItems().getValue();
        mShopItemAdapter = new ShopItemAdapter(limitedShopItems, getContext(), mNavController,actionToDetailedShopItem, mShopItemsViewModel);
        mShopRecycler.setAdapter(mShopItemAdapter);
        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the adapter to recycler
        bookArray = (ArrayList<BookRowDataModel>) mBookArrayViewModel.getBookArrays().getValue();
        mBookParentAdapter = new BookParentAdapter(bookArray, getContext(), mNavController,actionToDetailedBook, mBookArrayViewModel);
        mBookRecycler.setAdapter(mBookParentAdapter);
        mBookRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        int i = 0;

        if (!mShopItems.isEmpty()) {
            i =  mShopItems.size();

            if(limitedShopItems.isEmpty()) {
                for (ShopItemDataModel shopItem : mShopItems) {
                    if (shopItem.getPrice().equals("3.99000")) {
                        featured = shopItem;
//                        Toast.makeText(getContext(), featured.getShopItemName() + "   "+ featured.getShopItemImage(), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        limitedShopItems.add(shopItem);
                    }

                }

                if( limitedShopItems.size()> 3) {
                limitedShopItems.subList(3, i).clear();
                }

            }

//            Toast.makeText(getContext(), featured.getShopItemName(), Toast.LENGTH_SHORT).show();
            featuredImage.setImageBitmap(featured.getShopItemImage());
            featuredTitle.setText(featured.getShopItemName());
            featuredSubTitle.setText(featured.getShopItemSubName());
            featuredPrice.setText(featured.getPrice());


        }

        mBookParentAdapter.notifyDataSetChanged();
        initRecyclers();
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    private void loadingRecycler() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 6000;
        myHandler = new Handler();

        Log.d(TAG2, "Books Recycler been called");

        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                initRecyclers();
            }
        }, SPLASH_TIME_OUT);
    }
}
