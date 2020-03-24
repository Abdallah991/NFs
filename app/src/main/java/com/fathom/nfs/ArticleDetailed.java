package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.ViewModels.ArticleViewModel;

import org.w3c.dom.Text;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleDetailed extends Fragment {

    private ImageView articleImage;
    private TextView articleTitle;
    private TextView author;
    private TextView articleContent;
    private ScrollView articleDetailedContent;
    private int position;


    public ArticleDetailed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_detailed, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articleImage = view.findViewById(R.id.articleDetailedImage);
        articleTitle = view.findViewById(R.id.articleDetailedTitle);
        articleContent = view.findViewById(R.id.articleContent);
        author = view.findViewById(R.id.articleDetailedAuthor);
        articleDetailedContent = view.findViewById(R.id.articleDetailed);

        ArticleViewModel model = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        model.initArticles();
        position = model.getPositionOfArticle();
        model.getArticles().observe(getViewLifecycleOwner(), new Observer<List<ArticleDataModel>>() {
            @Override
            public void onChanged(List<ArticleDataModel> articleDataModels) {
                ArticleDataModel article = articleDataModels.get(position);

                articleImage.setImageBitmap(article.getArticleImage());
                articleTitle.setText(article.getArticleTitle());
                author.setText(article.getAuthorName());
                articleContent.setText((article.getArticleContent()));
            }
        });

        ViewCompat.setLayoutDirection(articleDetailedContent, ViewCompat.LAYOUT_DIRECTION_LTR);


    }
}
