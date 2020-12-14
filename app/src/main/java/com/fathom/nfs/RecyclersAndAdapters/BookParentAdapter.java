package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.BookArrayViewModel;

import java.util.ArrayList;

public class BookParentAdapter extends RecyclerView.Adapter<BookParentAdapter.BookHolder> {

    private Context mContext;
    private ArrayList<BookRowDataModel> mBooks;
    private BookChildAdapter mBookChildAdapter;
    private NavController mNavController;
    private int actionId;
    private BookArrayViewModel mBookArrayViewModel;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public BookParentAdapter(ArrayList<BookRowDataModel> books,
                             Context context,
                             NavController navController,
                             int action,
                             BookArrayViewModel bookArrayViewModel) {

        this.mBooks = books;
        this.mContext = context;
        mNavController = navController;
        actionId = action;
        mBookArrayViewModel= bookArrayViewModel;

    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View theView = LayoutInflater.from(mContext).inflate(R.layout.book_parent, parent, false);


        return new BookHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {


//        if (horizentalAdapter == null)
        Log.d("Book Parent Adapter", "onBindViewHolder() fired and position is "+ position);
        Log.d("Book Parent Adapter", "Array size " + mBooks.size());

        ArrayList<ShopItemDataModel> books = mBooks.get(position).getBooks();

        mBookChildAdapter = new BookChildAdapter(books,mContext, mNavController, actionId, mBookArrayViewModel, position, mBooks);

        holder.recyclerViewHorizontal.setAdapter(mBookChildAdapter);

//        holder.recyclerViewHorizontal.setRecycledViewPool(recycledViewPool);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    public class BookHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewHorizontal;

        private LinearLayoutManager horizontalManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);


        public BookHolder (View itemView) {
            super(itemView);

            recyclerViewHorizontal = itemView.findViewById(R.id.bookParentRecycler);
            recyclerViewHorizontal.setHasFixedSize(true);
            recyclerViewHorizontal.setNestedScrollingEnabled(false);
            recyclerViewHorizontal.setLayoutManager(horizontalManager);
            recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());



        }
    }
}
