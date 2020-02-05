package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.ShopItemsViewModel;

import java.util.ArrayList;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemHolder> {

    private static final String TAG = "ShopItem Adapter";

    // Declare variables
    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;
    private int mShopItemId;
    private ShopItemsViewModel mShopItemsViewModel;

    // Constructor
    public ShopItemAdapter (ArrayList<ShopItemDataModel> ShopItemDetails,
                            Context context,
                            NavController navController,
                            int actionId,
                            ShopItemsViewModel shopItemsViewModel) {
        mShopItems = ShopItemDetails;
        mContext = context;
        mNavController = navController;
        mShopItemId = actionId;
        mShopItemsViewModel = shopItemsViewModel;

    }

    @NonNull
    @Override
    public ShopItemAdapter.ShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item,parent, false);

        return new ShopItemAdapter.ShopItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemAdapter.ShopItemHolder holder, final int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");
        holder.shopItemImage.setImageResource(mShopItems.get(position).getImageUrl());
        holder.price.setText(mShopItems.get(position).getPrice());
        holder.shopItemDescription.setText(mShopItems.get(position).getItemDescription());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(mShopItemId);
                mShopItemsViewModel.selectShopItems(mShopItems, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mShopItems.size();
    }

    public class ShopItemHolder extends RecyclerView.ViewHolder{
        CardView card;
        ImageView shopItemImage;
        TextView price;
        TextView shopItemDescription;
        ImageView bookmark;

        public ShopItemHolder (View itemView){
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.shopItemCard);
            price = itemView.findViewById(R.id.itemPrice);
            shopItemDescription = itemView.findViewById(R.id.itemDescription);
            shopItemImage = itemView.findViewById(R.id.shopItemImage);
            bookmark = itemView.findViewById(R.id.bookmarkArticle);
        }



    }
}