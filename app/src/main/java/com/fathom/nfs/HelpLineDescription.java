package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpLineDescription extends Fragment {

    private LinearLayout helpLineDescription;
    private ImageButton backButton;


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

        ViewCompat.setLayoutDirection(helpLineDescription, ViewCompat.LAYOUT_DIRECTION_LTR);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_helpLineDescription_to_helpLinesFragment);
            }
        });
    }
}
