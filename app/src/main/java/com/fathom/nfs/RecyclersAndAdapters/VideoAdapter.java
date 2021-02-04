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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.ArticleViewModel;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    /**
     * @class video adapter
     * @desription  list video items in video section in articles screen and tying the data to UI elements
     * @date 4 feb 2021
     */
    private static final String TAG = "Article Adapter";

    // Declare variables
    private ArrayList<ArticleDataModel> mVideos = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;
    private int actionId;
    private ArticleViewModel mArticleViewModel;


    // Constructor
    public VideoAdapter(ArrayList<ArticleDataModel> ArticleDetails, Context context, NavController navController, int action, ArticleViewModel model) {
        mVideos = ArticleDetails;
        mContext = context;
        mNavController = navController;
        actionId = action;
        mArticleViewModel = model;

    }

    @NonNull
    @Override
    public VideoAdapter.VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);

        return new VideoAdapter.VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoHolder holder, final int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");
        holder.videoImage.setImageBitmap(mVideos.get(position).getArticleImage());
        holder.uploaderName.setText(mVideos.get(position).getAuthorName());
        holder.videoTitle.setText(mVideos.get(position).getArticleTitle());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionId);
                mArticleViewModel.selectArticle(mVideos, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView videoImage;
        TextView uploaderName;
        TextView videoTitle;
        ImageView bookmark;
        ImageView playButton;

        public VideoHolder(View itemView) {
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.videoCard);
            uploaderName = itemView.findViewById(R.id.uploaderName);
            videoTitle = itemView.findViewById(R.id.videoHeadline);
            videoImage = itemView.findViewById(R.id.videoImage);
            bookmark = itemView.findViewById(R.id.bookmarkArticle);
            playButton = itemView.findViewById(R.id.playImage);
        }


    }
}