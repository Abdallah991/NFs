package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.FAQsDataModel;
import com.fathom.nfs.R;
import com.fathom.nfs.ViewModels.FAQsViewModel;

import java.util.ArrayList;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.FAQHolder> {

    private ArrayList<FAQsDataModel> mFAQs = new ArrayList<>();
    private Context mContext;
    private NavController mNavController;
    private int actionToFragmentDetailed = R.id.action_FAQsFragment_to_FAQsDetailed;
    private FAQsViewModel mFAQsViewModel;


    public FAQsAdapter(ArrayList<FAQsDataModel> FAQs, Context context, NavController navController, FAQsViewModel faQsViewModel) {
        mFAQs = FAQs;
        mContext = context;
        mNavController = navController;
        mFAQsViewModel = faQsViewModel;
    }

    @NonNull
    @Override
    public FAQHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqs_sections, parent, false);

        return new FAQsAdapter.FAQHolder(view);

    }

        @Override
        public void onBindViewHolder (@NonNull FAQHolder holder, final int position){

        holder.question.setText("What is "+(mFAQs.get(position).getFAQCategory()) + " ?");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFAQsViewModel.selectFAQs(mFAQs, position);
                mNavController.navigate(actionToFragmentDetailed);
            }
        });

        }

        @Override
        public int getItemCount () {
            return mFAQs.size();
        }

        public class FAQHolder extends RecyclerView.ViewHolder {
            CardView card;
            TextView question;

            public FAQHolder(View itemView) {
                super(itemView);

                // binding the views with the list items
                card = itemView.findViewById(R.id.FAQsSectionCard);
                question = itemView.findViewById(R.id.questionSectionText);
            }


        }

    }


