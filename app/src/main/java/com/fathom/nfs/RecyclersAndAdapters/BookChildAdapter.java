package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.BookDataModel;
import com.fathom.nfs.R;

public class BookChildAdapter extends RecyclerView.Adapter<BookChildAdapter.BookChildHolder> {


    private BookDataModel mBook;
    private Context mContext;


    public BookChildAdapter(BookDataModel book, Context context) {

        this.mBook = book;
        this.mContext = context;

    }

    @NonNull
    @Override
    public BookChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new BookChildHolder(LayoutInflater.from(mContext).inflate(R.layout.book_child, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookChildHolder holder, int position) {

        holder.bookImage.setImageResource(mBook.getImageUrl());
        holder.price.setText(mBook.getPrice());
        holder.bookDescription.setText(mBook.getDescription());

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class BookChildHolder extends RecyclerView.ViewHolder {

        private ImageView bookImage;
        private TextView price;
        private TextView bookDescription;


        public BookChildHolder(View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.bookImage);
            price = itemView.findViewById(R.id.bookPrice);
            bookDescription = itemView.findViewById(R.id.bookDescription);

        }


    }
}
