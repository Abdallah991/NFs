package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.Articles;
import com.fathom.nfs.DataModels.ArticleCategoryDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.ArticleCategoryViewModel;

import java.util.ArrayList;

import static com.fathom.nfs.Articles.showArticles;
import static com.fathom.nfs.Articles.showBlog;
import static com.fathom.nfs.Articles.showCommunity;
import static com.fathom.nfs.Articles.showVideos;

public class HoizontalArticleAdapter extends RecyclerView.Adapter<HoizontalArticleAdapter.ArticleCardsHolder> {

    /**
     * @class Horizontal article adapter
     * @desription  list article categories and tying the data to UI elements in articles section
     * @date 4 feb 2021
     */
    private ArrayList<ArticleCategoryDataModel> mArticleCategories;
    private Context mContext;
    private int actionArticleId;
    private NavController mNavController;
    private ArticleCategoryViewModel mModel;


    public HoizontalArticleAdapter(
            ArrayList<ArticleCategoryDataModel> articleCategories,
            Context context,
            int actionId,
            NavController navController,
            ArticleCategoryViewModel model) {

        mArticleCategories = articleCategories;
        mContext = context;
        actionArticleId = actionId;
        mNavController = navController;
        mModel = model;


    }

    @NonNull
    @Override
    public ArticleCardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item,parent, false);
        return new ArticleCardsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleCardsHolder holder, int position) {

        holder.image.setImageResource(mArticleCategories.get(position).getCategoryImage());

        holder.card.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (position == 0){
                    showArticles();
                }
                if (position == 1) {
                    showVideos();

                }

                if (position == 2) {
                    showBlog();

                }

                if (position == 3) {
                    showCommunity();

                }
            }
        });






    }

    @Override
    public int getItemCount() {
        return mArticleCategories.size();
    }

    // Tying the the variables to list item UI elements
    public class ArticleCardsHolder extends RecyclerView.ViewHolder{


        ImageView image;
        CardView card;

        public ArticleCardsHolder (View itemView){
            super(itemView);

            // binding the view with list item
            image = itemView.findViewById(R.id.cardImage);
            card = itemView.findViewById(R.id.cardView);


        }

    }
}
