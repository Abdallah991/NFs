package com.fathom.nfs;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationProvider;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.fathom.nfs.DoctorsDetails.doctorFullName;
import static com.fathom.nfs.DoctorsDetails.lat;
import static com.fathom.nfs.DoctorsDetails.longt;
import static com.fathom.nfs.R.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorLocation extends Fragment implements OnMapReadyCallback {

    private LinearLayout doctorLocationContent;
    private ImageButton backButton;
    private Button openGoogleMaps;
    private TextView doctorNameLocation;



    public DoctorLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_doctor_location, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorLocationContent = view.findViewById(id.doctorLocationContent);
        backButton = view.findViewById(id.backButtonToDoctors);
        openGoogleMaps = view.findViewById(id.googleMaps);
        doctorNameLocation = view.findViewById(id.doctorNameLocation);

        doctorNameLocation.setText(doctorFullName);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });

        openGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                "geo:<lat>,<long>?q=<lat>,<long>(Label+Name)"
//                "geo:26.172521,50.547269"
//                "geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")")
                double myLatitude = lat;
                double myLongitude = longt;
                String labelLocation = "Jorgesys @ Bucharest";
                Uri gmmIntentUri = Uri.parse("geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });

        ViewCompat.setLayoutDirection(doctorLocationContent, ViewCompat.LAYOUT_DIRECTION_LTR);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        double myLatitude = lat;
        double myLongitude = longt;
        LatLng sydney = new LatLng(lat, longt);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Isa Town Mall").icon(BitmapDescriptorFactory.fromResource(drawable.pin)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }
}
