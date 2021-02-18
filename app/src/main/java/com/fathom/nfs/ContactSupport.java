package com.fathom.nfs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ContactSupport extends Fragment {


    private Button contactUsButton;
    private ImageButton backButton;
    private NavController mNavController;
    private ConstraintLayout contactLayout;

    public ContactSupport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactUsButton = view.findViewById(R.id.ContactUsButton);
        backButton = view.findViewById(R.id.backButtonContactUS);
        contactLayout = view.findViewById(R.id.contactUsLayout);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        ViewCompat.setLayoutDirection(contactLayout, ViewCompat.LAYOUT_DIRECTION_LTR);


        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent email = new Intent(Intent.ACTION_SENDTO);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"nfs.fthm.app@gmail.com"});
//                email.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
//                email.putExtra(Intent.EXTRA_TEXT, "I want to report about");
//                email.setType("message/rfc822");

                startActivity( new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:nfs.fthm.app@gmail.com")));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNavController.navigateUp();
            }
        });

    }
}