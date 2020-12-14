package com.fathom.nfs.RecyclersAndAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.QuestionDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;

public class FAQsSectionAdapter extends RecyclerView.Adapter<FAQsSectionAdapter.QuestionHolder> {

    private ArrayList<QuestionDataModel> mQuestions;
    private Context mContext;

    public FAQsSectionAdapter(ArrayList<QuestionDataModel> questions, Context context) {
        mQuestions = questions;
        mContext = context;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqs_list_item, parent, false);

        return new FAQsSectionAdapter.QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {

        holder.question.setText(mQuestions.get(position).getQuestion());
        holder.answer.setText(mQuestions.get(position).getAnswer());

    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {

        TextView question;
        TextView answer;

        public QuestionHolder(View itemView) {
            super(itemView);

            // binding the views with the list items
            answer = itemView.findViewById(R.id.answerFaqs);
            question = itemView.findViewById(R.id.questionFaqs);
        }


    }
}
