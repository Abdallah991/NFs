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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.ViewModels.ArticleViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleDetailed extends Fragment {

    private ImageView articleImage;
    private TextView articleTitle;
    private TextView author;
    private TextView articleAuthor;
    private TextView articleContent;
    private TextView authorEducation;
    private ScrollView articleDetailedContent;
    private int position;
    private ImageButton backButton;
    private ArrayList<ArticleDataModel> articles = new ArrayList<>();
    private Button nextArticle;


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
        author = view.findViewById(R.id.authorDetailedName);
        articleAuthor = view.findViewById(R.id.articleAuthor);
        authorEducation = view.findViewById(R.id.authorDetailedSpeciality);
        articleDetailedContent = view.findViewById(R.id.articleDetailed);
        backButton = view.findViewById(R.id.backButtonToArticlesFromDetailedArticle);
        nextArticle = view.findViewById(R.id.nextArticle);

        ArticleViewModel model = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        model.initArticles();
        position = model.getPositionOfArticle();
        model.getArticles().observe(getViewLifecycleOwner(), new Observer<List<ArticleDataModel>>() {
            @Override
            public void onChanged(List<ArticleDataModel> articleDataModels) {
                articles = (ArrayList<ArticleDataModel>) articleDataModels;
                ArticleDataModel article = articleDataModels.get(position);

                articleImage.setImageBitmap(article.getArticleImage());
                articleTitle.setText(article.getArticleTitle());
                author.setText(article.getAuthorName());
                articleAuthor.setText(article.getAuthorName());
                articleContent.setText((article.getArticleContent()));
                authorEducation.setText(article.getAuthorEducation()+" ");
            }
        });

        model.getAllArticles();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });

        nextArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int arrayLength = articles.size();
                if(position < arrayLength-1) {
                    position++;
                } else {
                    position = 0;
                }
                ArticleDataModel article = articles.get(position);
                articleImage.setImageBitmap(article.getArticleImage());
                articleTitle.setText(article.getArticleTitle());
                author.setText(article.getAuthorName());
                articleAuthor.setText(article.getAuthorName());
                articleContent.setText((article.getArticleContent()));
                authorEducation.setText(article.getAuthorEducation()+" ");
            }
        });

        ViewCompat.setLayoutDirection(articleDetailedContent, ViewCompat.LAYOUT_DIRECTION_LTR);

    }


}
