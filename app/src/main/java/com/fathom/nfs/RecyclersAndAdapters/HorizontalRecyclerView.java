package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.R;
import java.util.ArrayList;


public class HorizontalRecyclerView extends RecyclerView.Adapter<HorizontalRecyclerView.ViewHolder> {


    private static final String TAG = "Recycler View";

    // Declaring variables
    private ArrayList <Integer> mImageUrls = new ArrayList<>();
    private Context mContext;

    // Constructor
    public HorizontalRecyclerView(ArrayList<Integer> images, Context context) {
        mImageUrls = images;
        mContext = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder Called");

        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        // Tying the UI elements of the list item with their content
        holder.image.setImageResource(mImageUrls.get(position));

        // Setting the list item on click listener
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mImageUrls.get(position));
            }
        } );

    }


    // Setting the size of the list
    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    // Tying the the variables to list item UI elements
    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView image;
        CardView card;

        public ViewHolder (View itemView){
            super(itemView);

            // binding the view with list item
            image = itemView.findViewById(R.id.cardImage);
            card = itemView.findViewById(R.id.cardView);
        }

    }
}
