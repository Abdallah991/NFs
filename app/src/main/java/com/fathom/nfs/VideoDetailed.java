package com.fathom.nfs;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.ViewModels.ArticleViewModel;

import java.util.List;


public class VideoDetailed extends Fragment {


    private LinearLayout videoDetailedContent;
    private ImageView videoImage;
    private ImageView playVideo;
    private TextView videoTitle;
    private TextView videoUploader;
    private int position;
    private String youtubeLink;
    private ImageButton backButton;



    public VideoDetailed() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videoDetailedContent = view.findViewById(R.id.videoDetailed);
        videoImage = view.findViewById(R.id.videoDetailedImage);
        playVideo = view.findViewById(R.id.playImageDetailed);
        videoTitle = view.findViewById(R.id.videoDetailedTitle);
        videoUploader = view.findViewById(R.id.videoDetailedAuthor);
        backButton = view.findViewById(R.id.backButtonToArticles);

        ArticleViewModel model = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        model.initArticles();
        position = model.getPositionOfArticle();
        model.getArticles().observe(getViewLifecycleOwner(), new Observer<List<ArticleDataModel>>() {
            @Override
            public void onChanged(List<ArticleDataModel> articleDataModels) {

                ArticleDataModel article = articleDataModels.get(position);

                videoImage.setImageResource(article.getImageUrl());
                videoTitle.setText(article.getArticleTitle());
                videoUploader.setText(article.getAuthorName());
                youtubeLink = article.getVideoUrl();
                youtubeLink = "https://www.youtube.com/watch?v=XHNHq1mC0VQ";
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });


        ViewCompat.setLayoutDirection(videoDetailedContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(youtubeLink));
                startActivity(webIntent);
            }
        });

    }
}
