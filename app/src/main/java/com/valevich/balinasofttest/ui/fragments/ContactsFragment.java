package com.valevich.balinasofttest.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.utils.StubConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_contacts)
public class ContactsFragment extends Fragment implements OnMapReadyCallback {

    @ViewById(R.id.map)
    MapView mMapView;

    private GoogleMap mGoogleMap;

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @AfterViews
    void setUpViews() {
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        showCompaniesLocation();
        showUserLocation();
    }

    private void setUpMap() {
        mMapView.onCreate(null);
        mMapView.setClickable(false);
        mMapView.getMapAsync(this);
    }

    private void showCompaniesLocation() {
        List<String> addresses = StubConstants.STUB_ADDRESSES;
        List<Double> latitudes = StubConstants.STUB_LATITUDES;
        List<Double> longitudes = StubConstants.STUB_LONGITUDES;
        List<LatLng> markerPositions = new ArrayList<>(4);

        for(int i = 0; i<addresses.size(); i++) {
            String address = addresses.get(i);
            double lat = latitudes.get(i);
            double lon = longitudes.get(i);
            LatLng position = new LatLng(lat,lon);
            mGoogleMap.addMarker(new MarkerOptions().position(position).title(address));

            markerPositions.add(position);
        }
        zoomToFitMarkers(markerPositions);
    }

    private void showUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }// TODO: 13.08.2016 MOVE
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void zoomToFitMarkers(List<LatLng> markerPositions) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng position : markerPositions) {
            builder.include(position);
        }
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);

        mGoogleMap.moveCamera(cu);
    }

}
