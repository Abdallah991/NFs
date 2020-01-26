package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.R;

import java.util.ArrayList;

public class HelpLineRecycler extends RecyclerView.Adapter<HelpLineRecycler.ItemHolder> {

    private static final String TAG = "HelpLineRecycler";

    // Declare variables
    private ArrayList<Integer> helpLineImages = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;

    // Constructor
    public HelpLineRecycler(ArrayList<Integer> images, Context context, NavController navController) {
        helpLineImages = images;
        mContext = context;
        mNavController = navController;

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_line_list_item,parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");

        // Tying the UI elements of the list item with their content
        holder.image.setImageResource(helpLineImages.get(position));

        // Setting the list item on click listener
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked on: " + helpLineImages.get(position));
                Toast.makeText(mContext, "clicked "+ helpLineImages.get(position) , Toast.LENGTH_SHORT).show();

                mNavController.navigate(R.id.action_helpLinesFragment_to_helpLineDescription);


            }
        });


    }

    // Setting the size of the list
    @Override
    public int getItemCount() {
        return helpLineImages.size();
    }


    // Tying the the variables to list item UI elements
    public class ItemHolder extends RecyclerView.ViewHolder{
        ImageView image;
        CardView card;

        public ItemHolder (View itemView){
            super(itemView);

            // binding the views with list item
            image = itemView.findViewById(R.id.helpImage);
            card = itemView.findViewById(R.id.helpCard);
        }



    }
}
