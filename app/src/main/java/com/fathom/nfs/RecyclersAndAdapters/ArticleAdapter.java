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

import static com.fathom.nfs.DataModels.BookmarkDataModel.articleItemsBookmarked;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    /**
     * @class Article adapter
     * @desription  list articles and tying the data to UI elements in article lists
     * @date 4 feb 2021
     */
    private static final String TAG = "Article Adapter";

    // Declare variables
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;
    private int actionId;
    private ArticleViewModel mArticleViewModel;


    // Constructor
    public ArticleAdapter (ArrayList<ArticleDataModel> ArticleDetails, Context context, NavController navController, int action, ArticleViewModel model) {
        mArticles = ArticleDetails;
        mContext = context;
        mNavController = navController;
        actionId = action;
        mArticleViewModel = model;

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
    public void onBindViewHolder(@NonNull final ArticleAdapter.ArticleHolder holder, final int position) {

        holder.articleImage.setImageBitmap(mArticles.get(position).getArticleImage());
        holder.articleTitle.setText(mArticles.get(position).getArticleTitle());

        // Clicking on card
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(actionId);
                mArticleViewModel.selectArticle(mArticles, position);
            }
        });
        // Clicking on bookmark
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mArticles.get(position).isBookmark()){
                    mArticleViewModel.unBookmarkItem(mArticles.get(position));
                    holder.bookmark.setImageResource(R.drawable.bookmark_image);
                    mArticleViewModel.saveBookmarkedArticles();


                } else if (!mArticles.get(position).isBookmark()){
                    mArticleViewModel.bookmarkItem(mArticles.get(position));
                    holder.bookmark.setImageResource(R.drawable.bookmarked);
                    mArticleViewModel.saveBookmarkedArticles();


                    Log.d("ARTICLE1", "bookmark have been set");

                }
            }
        });

        if(mArticles.get(position).isBookmark())    {

            holder.bookmark.setImageResource(R.drawable.bookmarked);
        }


    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    // Tying elements to the list item
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
