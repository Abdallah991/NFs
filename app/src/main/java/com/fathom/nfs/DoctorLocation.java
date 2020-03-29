package com.fathom.nfs;


import android.app.Activity;
import android.location.Location;
import android.location.LocationProvider;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.fathom.nfs.R.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorLocation extends Fragment implements OnMapReadyCallback {

    private LinearLayout doctorLocationContent;
    private LatLng doctorLocation;
    private ImageButton backButton;



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

        ViewCompat.setLayoutDirection(doctorLocationContent, ViewCompat.LAYOUT_DIRECTION_LTR);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng sydney = new LatLng(26.172521, 50.547269);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Isa Town Mall").icon(BitmapDescriptorFactory.fromResource(drawable.pin)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }
}
