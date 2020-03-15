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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.DoctorsViewModel;

import java.util.ArrayList;

import static com.fathom.nfs.DataModels.BookmarkDataModel.articleItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.doctorItemsBookmarked;
import static com.fathom.nfs.DataModels.BookmarkDataModel.setClickedFromBookmarks;
import static com.fathom.nfs.DataModels.BookmarkDataModel.setPositionOfBookMark;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorHolder> {

private static final String TAG = "Doctors Adapter";

// Declare variables
private ArrayList<DoctorDataModel> mDoctors;
private Context mContext;
private NavController mNavController;
// The navigation action Id constant
private int actionId;
// Injecting the View Model
    private DoctorsViewModel mModel;


// Constructor
public DoctorsAdapter(ArrayList<DoctorDataModel> DoctorsDetails,
                      Context context,
                      NavController navController,
                      int action,
                      DoctorsViewModel model) {
        mDoctors = DoctorsDetails;
        mContext = context;
        mNavController = navController;
        actionId = action;
        mModel = model;

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
    public void onBindViewHolder(@NonNull final DoctorHolder holder, final int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");
        holder.firstName.setText(mDoctors.get(position).getDoctorFirstName());
        holder.lastName.setText(mDoctors.get(position).getDoctorLastName());
        double rating = mDoctors.get(position).getRating();
        String mRating = Double.toString(rating);
        holder.rating.setText(mRating);
        // setting image bitmap
        holder.doctorImage.setImageBitmap(mDoctors.get(position).getDoctorImage());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mDoctors.get(position).isBookmark()) {
                    setClickedFromBookmarks(false);
                    mNavController.navigate(actionId);
                    mModel.selectDoctor(mDoctors, position);
                }
                else if(mDoctors.get(position).isBookmark()){
                    if (actionId == R.id.action_homeFragment_to_doctorsDetails) {
                        
                        mModel.selectDoctor(mDoctors, position);
                        setClickedFromBookmarks(true);
                        mNavController.navigate(actionId);
                    }
                    else {
                        setPositionOfBookMark(position);
                        setClickedFromBookmarks(true);
                        mNavController.navigate(actionId);
                    }
                }
            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDoctors.get(position).isBookmark()){
                    holder.bookmark.setImageResource(R.drawable.bookmark_image);
                    mDoctors.get(position).setBookmark(false);
                    doctorItemsBookmarked.remove(mDoctors.get(position));

                } else if (!mDoctors.get(position).isBookmark()){
                    holder.bookmark.setImageResource(R.drawable.bookmarked);
                    mDoctors.get(position).setBookmark(true);
                    doctorItemsBookmarked.add(mDoctors.get(position));
                }
            }
        });

        if(mDoctors.get(position).isBookmark()){

            holder.bookmark.setImageResource(R.drawable.bookmarked);
        }



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
