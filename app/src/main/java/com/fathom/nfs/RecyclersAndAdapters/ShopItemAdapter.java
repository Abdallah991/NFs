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
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemHolder> {

    private static final String TAG = "ShopItem Adapter";

    // Declare variables
    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private Context mContext;

    // Constructor
    public ShopItemAdapter (ArrayList<ShopItemDataModel> ShopItemDetails, Context context) {
        mShopItems = ShopItemDetails;
        mContext = context;

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
    public void onBindViewHolder(@NonNull ShopItemAdapter.ShopItemHolder holder, int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");
        holder.shopItemImage.setImageResource(mShopItems.get(position).getImageUrl());
        holder.price.setText(mShopItems.get(position).getPrice());
        holder.shopItemDescription.setText(mShopItems.get(position).getItemDescription());


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