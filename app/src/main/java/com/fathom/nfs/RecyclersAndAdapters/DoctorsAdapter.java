package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorHolder> {

private static final String TAG = "Doctors Adapter";

// Declare variables
private ArrayList<DoctorDataModel> mDoctors = new ArrayList<>();
private Context mContext;

// Constructor
public DoctorsAdapter(ArrayList<DoctorDataModel> DoctorsDetails, Context context) {
        mDoctors = DoctorsDetails;
        mContext = context;

        }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_list_item,parent, false);

        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");
        holder.firstName.setText(mDoctors.get(position).getDoctorFirstName());
        holder.lastName.setText(mDoctors.get(position).getDoctorLastName());
        double rating = mDoctors.get(position).getRating();
        String mRating = Double.toString(rating);
        holder.rating.setText(mRating);
        holder.doctorImage.setImageResource(mDoctors.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    public class DoctorHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView firstName;
        TextView lastName;
        TextView rating;
        ImageView doctorImage;
        ImageView bookmark;

        public DoctorHolder (View itemView){
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.doctorsCard);
            firstName = itemView.findViewById(R.id.doctorFirstName);
            lastName = itemView.findViewById(R.id.doctorLastName);
            rating = itemView.findViewById(R.id.ratingValue);
            doctorImage = itemView.findViewById(R.id.doctorImage);
            bookmark = itemView.findViewById(R.id.bookmark);
        }



    }
}
