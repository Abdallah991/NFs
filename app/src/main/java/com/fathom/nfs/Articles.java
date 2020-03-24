package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.ArticleCategoryDataModel;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.RecyclersAndAdapters.ArticleAdapter;
import com.fathom.nfs.RecyclersAndAdapters.HoizontalArticleAdapter;
import com.fathom.nfs.RecyclersAndAdapters.HorizontalRecyclerView;
import com.fathom.nfs.RecyclersAndAdapters.VideoAdapter;
import com.fathom.nfs.ViewModels.ArticleCategoryViewModel;
import com.fathom.nfs.ViewModels.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Articles extends Fragment {

    private ArrayList<ArticleCategoryDataModel> mArticleCategories = new ArrayList<>();
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
    private ArrayList<ArticleDataModel> mVideos = new ArrayList<>();
    private ArrayList<ArticleDataModel> mBlogs = new ArrayList<>();
    private ArrayList<ArticleDataModel> mCommunities = new ArrayList<>();
    private ArticleCategoryViewModel mArticleCategoryViewModel;
    private ArticleViewModel mArticleViewModel;
    private HoizontalArticleAdapter horizontalAdapter;
    private ArticleAdapter mArticleAdapter;
    private ArticleAdapter mBlogAdapter;
    private ArticleAdapter mCommunityAdapter;
    private VideoAdapter mVideoAdapter;
    private RecyclerView mArticlesRecycler;
    private RecyclerView mVideosRecycler;
    private RecyclerView mBlogRecycler;
    private RecyclerView mCommunityRecycler;
    private NavController mNavController;
    private ScrollView articlesContent;
    private int actionArticle = R.id.action_articles_to_articleDetailed2;
    private int actionId = R.id.action_articles_to_articleDetailed2;
    private int actionVideo = R.id.action_articles_to_videoDetailed2;
    private static RelativeLayout articlesLayout;
    private static RelativeLayout videosLayout;
    private static RelativeLayout blogLayout;
    private static RelativeLayout communityLayout;



    public Articles() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        articlesContent = view.findViewById(R.id.articlesContent);
        mArticlesRecycler = view.findViewById(R.id.articlesRecycler);
        mVideosRecycler = view.findViewById(R.id.videoRecycler);
        mBlogRecycler = view.findViewById(R.id.blogsRecycler);
        mCommunityRecycler = view.findViewById(R.id.communityRecycler);
        articlesLayout = view.findViewById(R.id.articlesLayout);
        videosLayout = view.findViewById(R.id.videoLayout);
        blogLayout = view.findViewById(R.id.blogsLayout);
        communityLayout = view.findViewById(R.id.communityLayout);

        mArticleCategoryViewModel = new ViewModelProvider(requireActivity()).get(ArticleCategoryViewModel.class);
        mArticleCategoryViewModel.initArticleCategories();
        mArticleCategoryViewModel.getArticleCategories().observe(getViewLifecycleOwner(), new Observer<List<ArticleCategoryDataModel>>() {
            @Override
            public void onChanged(List<ArticleCategoryDataModel> articleCategoryDataModels) {

                mArticleCategories = (ArrayList<ArticleCategoryDataModel>) articleCategoryDataModels;
                horizontalAdapter.notifyDataSetChanged();
//                Toast.makeText( getContext(), "size of article Categories is "+ mArticleCategories.size() , Toast.LENGTH_SHORT).show();
            }
        });

        mArticleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        mArticleViewModel.initArticles();

        mArticleViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<List<ArticleDataModel>>() {
            @Override
            public void onChanged(List<ArticleDataModel> articleDataModels) {

                mArticleAdapter.notifyDataSetChanged();
                mVideoAdapter.notifyDataSetChanged();
            }
        });

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        ViewCompat.setLayoutDirection(articlesContent, ViewCompat.LAYOUT_DIRECTION_LTR);



        showArticles();
        initRecyclerView();

    }


    private void initRecyclerView() {


        // setting the adapter and adjusting to horizontal view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.articleCategoryCards);
        recyclerView.setLayoutManager(layoutManager);
        mArticleCategories = (ArrayList<ArticleCategoryDataModel>) mArticleCategoryViewModel.getArticleCategories().getValue();
        horizontalAdapter = new HoizontalArticleAdapter(mArticleCategories, getContext(), actionId, mNavController, mArticleCategoryViewModel);
        recyclerView.setAdapter(horizontalAdapter);


        // setting the articles adapter to recycler
        mArticles = mArticleCategories.get(0).getArticles();
        mArticleAdapter = new ArticleAdapter(mArticles, getContext(), mNavController, actionArticle, mArticleViewModel);
        mArticlesRecycler.setAdapter(mArticleAdapter);
        mArticlesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the video adapter to recycler
        mVideos = mArticleCategories.get(1).getArticles();
        mVideoAdapter = new VideoAdapter(mVideos, getContext(), mNavController, actionVideo, mArticleViewModel);
        mVideosRecycler.setAdapter(mVideoAdapter);
        mVideosRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the blog adapter to recycler
        mBlogs = mArticleCategories.get(2).getArticles();
        mBlogAdapter = new ArticleAdapter(mBlogs, getContext(), mNavController, actionArticle, mArticleViewModel);
        mBlogRecycler.setAdapter(mBlogAdapter);
        mBlogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the community adapter to recycler
        mCommunities = mArticleCategories.get(3).getArticles();
        mCommunityAdapter = new ArticleAdapter(mCommunities, getContext(), mNavController, actionArticle, mArticleViewModel);
        mCommunityRecycler.setAdapter(mCommunityAdapter);
        mCommunityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    public static void showArticles() {

        articlesLayout.setVisibility(View.VISIBLE);
        videosLayout.setVisibility(View.GONE);
        blogLayout.setVisibility(View.GONE);
        communityLayout.setVisibility(View.GONE);


    }

    public static void showVideos() {

        articlesLayout.setVisibility(View.GONE);
        videosLayout.setVisibility(View.VISIBLE);
        blogLayout.setVisibility(View.GONE);
        communityLayout.setVisibility(View.GONE);

    }
    public static void showBlog() {

        articlesLayout.setVisibility(View.GONE);
        videosLayout.setVisibility(View.GONE);
        blogLayout.setVisibility(View.VISIBLE);
        communityLayout.setVisibility(View.GONE);

    }
    public static void showCommunity() {

        articlesLayout.setVisibility(View.GONE);
        videosLayout.setVisibility(View.GONE);
        blogLayout.setVisibility(View.GONE);
        communityLayout.setVisibility(View.VISIBLE);

    }
}
