package com.example.mob_dev_portfolio;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private ImageButton searchButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Places.initialize(getActivity(), getString(R.string.my_map_api_key));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastLocation();

        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getActivity());
                autocompleteLauncher.launch(intent);
            }
        });


        return view;
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapFragment.this);
                        showNearbyTherapists();

                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;

        LatLng home = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        myMap.addMarker(new MarkerOptions().position(home).title("My Location"));
        myMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(home, 10));

        showNearbyTherapists();

    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        if (result) {
            getLastLocation();
        } else {
            Toast.makeText(getActivity(), "Location Permission is denied, Please allow the permission", Toast.LENGTH_SHORT).show();
        }
    });

    private ActivityResultLauncher<Intent> autocompleteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    LatLng placeLatLng = place.getLatLng();
                    String placeName = place.getName();
                    String placeAddress = place.getAddress();

                    myMap.addMarker(new MarkerOptions().position(placeLatLng).title(placeName).snippet(placeAddress));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 15));
                } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(result.getData());
                    Toast.makeText(getActivity(), "Error getting nearby places: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }
    );

    private void showNearbyTherapists() {
        if (currentLocation != null) {
            String apiKey = getString(R.string.my_map_api_key);
            Places.initialize(getActivity(), apiKey);
            PlacesClient placesClient = Places.createClient(getActivity());
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

            // Set the search query to look for therapists
            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
            String query = "therapist"; // or "mental health"
            RectangularBounds bounds = RectangularBounds.newInstance(

                    new LatLng(currentLocation.getLatitude() - 0.05, currentLocation.getLongitude() - 0.05),
                    new LatLng(currentLocation.getLatitude() + 0.05, currentLocation.getLongitude() + 0.05)
            );

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFields)
                    .build(getActivity());
            autocompleteLauncher.launch(intent);


            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                return;
            }
            Task<FindCurrentPlaceResponse> task = placesClient.findCurrentPlace(request);
            task.addOnSuccessListener((response) -> {
                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                    Place place = placeLikelihood.getPlace();
                    LatLng placeLatLng = place.getLatLng();
                    String placeName = place.getName();
                    String placeAddress = place.getAddress();
                    myMap.addMarker(new MarkerOptions().position(placeLatLng).title(placeName).snippet(placeAddress));
                }
            });
            task.addOnFailureListener((exception) -> {
                Toast.makeText(getActivity(), "Error getting nearby places: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
