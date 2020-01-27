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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.ViewModels.HelpLineViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpLineDescription extends Fragment {

    private LinearLayout helpLineDescription;
    private ImageButton backButton;
    private ArrayList<HelpLinesDataModel> helpLineItems;
    private ImageView helpLineImage;
    private TextView helpLineText;
    private int position;


    public HelpLineDescription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_line_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helpLineDescription = view.findViewById(R.id.helpLineDescription);
        backButton = view.findViewById(R.id.backButtonToHelpLines);
        helpLineImage = view.findViewById(R.id.helpLineImage);
        helpLineText = view.findViewById(R.id.helpLineText);

        ViewCompat.setLayoutDirection(helpLineDescription, ViewCompat.LAYOUT_DIRECTION_LTR);

        HelpLineViewModel model = new ViewModelProvider(requireActivity()).get(HelpLineViewModel.class);
        position = model.getPositionOfItems();
        model.getmHelpLines().observe(getViewLifecycleOwner(), new Observer<List<HelpLinesDataModel>>() {
            @Override
            public void onChanged(List<HelpLinesDataModel> helpLinesDataModels) {

                HelpLinesDataModel helpLine = helpLinesDataModels.get(position);
                helpLineImage.setImageResource(helpLine.getDescriptionImage());
                helpLineText.setText(helpLine.getHelpLineDescription());


            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_helpLineDescription_to_helpLinesFragment);
            }
        });
    }
}
