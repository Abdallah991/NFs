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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.DataModels.FAQsDataModel;
import com.fathom.nfs.DataModels.QuestionDataModel;
import com.fathom.nfs.RecyclersAndAdapters.FAQsAdapter;
import com.fathom.nfs.RecyclersAndAdapters.FAQsSectionAdapter;
import com.fathom.nfs.ViewModels.FAQsViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQsDetailed extends Fragment {

    private ArrayList<QuestionDataModel> mFAQs = new ArrayList<>();
    private ArrayList<FAQsDataModel> mFAQsSection = new ArrayList<>();
    private RecyclerView mFAQsRecycler;
    private FAQsSectionAdapter mFAQsAdapter;
    private ScrollView FAQsDetailedContent;
    private NavController mNavController;
    private FAQsViewModel mFAQsViewModel;
    private int actionBackToHome;
    private int position;
    private TextView title;
    private TextView detailedSectionText;
    private ImageView monsterImage;
    private ImageButton backButton;

    public FAQsDetailed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faqs_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FAQsDetailedContent = view.findViewById(R.id.faqsDetailedContent);
        mFAQsRecycler = view.findViewById(R.id.faqsDetailedRecyclerView);
        title = view.findViewById(R.id.faqsDetailedTitle);
        detailedSectionText = view.findViewById(R.id.faqsDetailedText);
        monsterImage = view.findViewById(R.id.faqsDetailedMonster);
        backButton = view.findViewById(R.id.backButtonToFaqs);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);



        mFAQsViewModel = new ViewModelProvider(requireActivity()).get(FAQsViewModel.class);
        position = mFAQsViewModel.getPositionOfFAQ();

        mFAQsViewModel.getFAQs().observe(getViewLifecycleOwner(), new Observer<List<FAQsDataModel>>() {
            @Override
            public void onChanged(List<FAQsDataModel> faQsDataModels) {

                mFAQsAdapter.notifyDataSetChanged();


                FAQsDataModel FAQsSection = faQsDataModels.get(position);
                mFAQs = FAQsSection.getFAQs();

                title.setText("What is "+(FAQsSection.getFAQCategory()) + " ?");
                monsterImage.setImageResource(FAQsSection.getCategoryName().getCategoryMonster());


            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavController.navigateUp();
            }
        });


        ViewCompat.setLayoutDirection(FAQsDetailedContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        initRecycler();


    }

    private void initRecycler() {

        mFAQs = mFAQsViewModel.getFAQs().getValue().get(position).getFAQs();
//        Toast.makeText(getContext(), mFAQs.size(), Toast.LENGTH_SHORT).show();
        mFAQsAdapter = new FAQsSectionAdapter(mFAQs, getContext());
        mFAQsRecycler.setAdapter(mFAQsAdapter);
        mFAQsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
