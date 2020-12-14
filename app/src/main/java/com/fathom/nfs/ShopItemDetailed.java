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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.BookChildAdapter;
import com.fathom.nfs.ViewModels.ShopItemsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fathom.nfs.DataModels.CartDataModel.shoppingCartItems;


public class ShopItemDetailed extends Fragment {

    private LinearLayout shopItemDetailedContent;
    private ImageView shopItemImage;
    private TextView price;
    private TextView shopItemDescription;
    private Button addToCart;
    private int position;
    private ArrayList<ShopItemDataModel> mShopItems;
    private ShopItemDataModel shopItem;
    private BookChildAdapter mShopItemAdapter;
    private RecyclerView horizontalShopItemRecycler;
    private ShopItemsViewModel model;
    private NavController mNavController;
    private int actionId = R.id.action_shopItemDetailed_self;
    private int actionToCart = R.id.action_shopItemDetailed_to_cart;
    public static ShopItemDataModel mShopItemDataModel;
    private ImageButton backButton;


//    public ShopItemDetailed( ShopItemDataModel shopItem) {
//        // Required empty public constructor
//        mShopItemDataModel = shopItem;
//    }

    public ShopItemDetailed( ) {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_item_detailed, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        shopItemDetailedContent = view.findViewById(R.id.shopItemDetailedContent);
        shopItemImage = view.findViewById(R.id.shopItemDetailedImage);
        price = view.findViewById(R.id.shopItemDetailedPrice);
        shopItemDescription = view.findViewById(R.id.ShopItemDetailedDescription);
        horizontalShopItemRecycler = view.findViewById(R.id.ShopItemDetailedRecyclerView);
        backButton = view.findViewById(R.id.backButtonToShop);
        addToCart = view.findViewById(R.id.addToCart);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        model = new ViewModelProvider(requireActivity()).get(ShopItemsViewModel.class);
        position = model.getPositionOfShopItem();

        model.getShopItems().observe(getViewLifecycleOwner(), new Observer<List<ShopItemDataModel>>() {
            @Override
            public void onChanged(List<ShopItemDataModel> shopItemDataModels) {

                mShopItemAdapter.notifyDataSetChanged();

                if (mShopItemDataModel == null) {
                    shopItem = shopItemDataModels.get(position);

                    shopItemImage.setImageBitmap(shopItem.getShopItemImage());
                    price.setText(shopItem.getPrice());
                    shopItemDescription.setText(shopItem.getItemDescription());
                }
                else {

                    shopItemImage.setImageBitmap(mShopItemDataModel.getShopItemImage());
                    price.setText(mShopItemDataModel.getPrice());
                    shopItemDescription.setText(mShopItemDataModel.getItemDescription());
                }
            }
        });


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNavController.navigate(actionToCart);
                if (mShopItemDataModel == null) {
                    shoppingCartItems.add(shopItem);
                }
                else {
                    shoppingCartItems.add(mShopItemDataModel);
                }
                Toast.makeText(getContext(), "Items in cart "+ shoppingCartItems.size() , Toast.LENGTH_SHORT).show();


            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigateUp();
            }
        });


        ViewCompat.setLayoutDirection(shopItemDetailedContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecycler();
    }

    private void initRecycler() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalShopItemRecycler.setLayoutManager(layoutManager);
        mShopItems = (ArrayList<ShopItemDataModel>)  model.getShopItems().getValue();
        Toast.makeText(getContext(), "lenght of Array " + mShopItems.size(),Toast.LENGTH_SHORT).show();
        mShopItemAdapter = new BookChildAdapter(mShopItems, getContext(), mNavController,actionId);
        horizontalShopItemRecycler.setAdapter(mShopItemAdapter);

    }
}
