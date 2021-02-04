package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.BookItemDetailed;
import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.BookArrayViewModel;

import java.util.ArrayList;

public class BookChildAdapter extends RecyclerView.Adapter<BookChildAdapter.BookChildHolder> {

    /**
     * @class book child adapter
     * @desription  list inside row books and tying the data to UI elements in book array
     * the shop is currently offline
     * @date 4 feb 2021
     */
    private ArrayList<ShopItemDataModel> mBook;
    private ArrayList<BookRowDataModel> mRow;
    private Context mContext;
    private NavController mNavController;
    private int actionId;
    private int rowPosition;
    private BookArrayViewModel mBookArrayViewModel;


    public BookChildAdapter(ArrayList<ShopItemDataModel> books,
                            Context context,
                            NavController navController,
                            int action,
                            BookArrayViewModel bookArrayViewModel,
                            int position,
                            ArrayList<BookRowDataModel> row) {

        this.mBook = books;
        this.mContext = context;
        mNavController = navController;
        actionId = action;
        mBookArrayViewModel = bookArrayViewModel;
        rowPosition = position;
        mRow = row;

    }

    public BookChildAdapter(
                            Context context,
                            NavController navController,
                            int action,
                            BookArrayViewModel bookArrayViewModel,
                            int position,
                            ArrayList<BookRowDataModel> row) {


        this.mContext = context;
        mNavController = navController;
        actionId = action;
        mBookArrayViewModel = bookArrayViewModel;
        rowPosition = position;
        mRow = row;

    }


    public BookChildAdapter(ArrayList<ShopItemDataModel> books,
                            Context context,
                            NavController navController,
                            int action) {

        this.mBook = books;
        this.mContext = context;
        mNavController = navController;
        actionId = action;

    }

    @NonNull
    @Override
    public BookChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new BookChildHolder(LayoutInflater.from(mContext).inflate(R.layout.book_child, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookChildHolder holder, final int position) {

        holder.bookImage.setImageBitmap(mBook.get(position).getShopItemImage());
        holder.price.setText(mBook.get(position).getPrice());
        holder.bookDescription.setText(mBook.get(position).getItemDescription());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookArrayViewModel.SelectRow(mRow, rowPosition);
                mBookArrayViewModel.SelectBook(mBook, position);
                BookItemDetailed.bookDetailed = mBook.get(position);
                mNavController.navigate(actionId);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }

    public class BookChildHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private ImageView bookImage;
        private TextView price;
        private TextView bookDescription;


        public BookChildHolder(View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.bookCard);
            bookImage = itemView.findViewById(R.id.bookImage);
            price = itemView.findViewById(R.id.bookPrice);
            bookDescription = itemView.findViewById(R.id.bookDescription);

        }


    }
}
