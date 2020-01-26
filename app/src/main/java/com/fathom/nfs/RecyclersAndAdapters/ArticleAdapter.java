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
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    private static final String TAG = "Article Adapter";

    // Declare variables
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
    private Context mContext;

    // Constructor
    public ArticleAdapter (ArrayList<ArticleDataModel> ArticleDetails, Context context) {
        mArticles = ArticleDetails;
        mContext = context;

    }

    @NonNull
    @Override
    public ArticleAdapter.ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item,parent, false);

        return new ArticleAdapter.ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleHolder holder, int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");
        holder.articleImage.setImageResource(mArticles.get(position).getImageUrl());
        holder.authorName.setText(mArticles.get(position).getAuthorName());
        holder.articleTitle.setText(mArticles.get(position).getArticleTitle());


    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ArticleHolder extends RecyclerView.ViewHolder{
        CardView card;
        ImageView articleImage;
        TextView authorName;
        TextView articleTitle;
        ImageView bookmark;

        public ArticleHolder (View itemView){
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.articleCard);
            authorName = itemView.findViewById(R.id.authorName);
            articleTitle = itemView.findViewById(R.id.articleHeadline);
            articleImage = itemView.findViewById(R.id.articleImage);
            bookmark = itemView.findViewById(R.id.bookmarkArticle);
        }



    }
}
