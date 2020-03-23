package com.fathom.nfs.RecyclersAndAdapters;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;
import java.util.ArrayList;

import static com.fathom.nfs.DataModels.CartDataModel.shoppingCartItems;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    // Declare variables
    private ArrayList<ShopItemDataModel> mCartItems = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;
    public CartAdapter(ArrayList<ShopItemDataModel> mCartItems, Context mContext, NavController navController) {
        this.mCartItems = mCartItems;
        this.mContext = mContext;
        mNavController = navController;
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent, false);
        return new CartAdapter.CartHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, final int position) {
        holder.price.setText(mCartItems.get(position).getPrice());
        holder.shopItemImage.setImageBitmap(mCartItems.get(position).getShopItemImage());
        holder.description.setText(mCartItems.get(position).getItemDescription());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked "+ mCartItems.get(position) , Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this Item?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                shoppingCartItems.remove(position);
                                mNavController.navigate(R.id.action_cart_self);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
    }
    @Override
    public int getItemCount() {
        return mCartItems.size();
    }
    public class CartHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView price;
        ImageView shopItemImage;
        TextView description;
        ImageView deleteCartItem;
        public CartHolder (View itemView){
            super(itemView);
            // binding the views with the list items
            card = itemView.findViewById(R.id.cartCard);
            price = itemView.findViewById(R.id.cartItemPrice);
            shopItemImage = itemView.findViewById(R.id.shopImage);
            description = itemView.findViewById(R.id.cartItemDescription);
            deleteCartItem = itemView.findViewById(R.id.deleteShopItem);
        }
    }
}