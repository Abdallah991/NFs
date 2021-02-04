package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    /**
     * @class Review adapter
     * @desription  list reviews in doctor profile (doctor-detailed) screen and tying the data to UI elements
     * @date 4 feb 2021
     */
    private ArrayList<ReviewDataModel> mReviews = new ArrayList<>();
    private Context mContext;


    public ReviewAdapter(ArrayList<ReviewDataModel> reviews, Context context) {
        mReviews = reviews;
        mContext = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item,parent, false);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

        float rating =  mReviews.get(position).getRating();
        int ratingToInt = (int) rating;
        String ratingToString = Integer.toString(ratingToInt);

        holder.rating.setText(ratingToString);
        holder.reviewText.setText(mReviews.get(position).getReviewText());

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }


    public class ReviewHolder extends RecyclerView.ViewHolder{

        TextView rating;
        TextView reviewText;


        public ReviewHolder (View itemView){
            super(itemView);

            // binding the views with the list items
            rating = itemView.findViewById(R.id.ratingValue);
            reviewText = itemView.findViewById(R.id.reviewText);

        }



    }
}
