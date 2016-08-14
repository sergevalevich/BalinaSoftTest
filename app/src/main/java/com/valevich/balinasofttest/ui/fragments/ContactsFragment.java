package com.valevich.balinasofttest.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.valevich.balinasofttest.R;
import com.valevich.balinasofttest.eventbus.EventBus;
import com.valevich.balinasofttest.eventbus.events.ContactSelectedEvent;
import com.valevich.balinasofttest.ui.recyclerview.adapters.ContactsAdapter;
import com.valevich.balinasofttest.utils.StubConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@EFragment(R.layout.fragment_contacts)
public class ContactsFragment extends Fragment implements OnMapReadyCallback {

    @ViewById(R.id.map)
    MapView mMapView;

    @ViewById(R.id.contacts_list)
    RecyclerView mRecyclerView;

    @Bean
    ContactsAdapter mContactsAdapter;

    @Bean
    EventBus mEventBus;

    private GoogleMap mGoogleMap;

    @Override
    public void onResume() {
        super.onResume();
        if(mMapView != null) mMapView.onResume();
    }

    @Override
    public void onPause() {
        if(mMapView != null) mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        if(mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                e.printStackTrace();//ignoring exception
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mMapView != null) mMapView.onLowMemory();
    }

    @Subscribe
    public void onContactSelected(ContactSelectedEvent event) {
        showSelectedContactInfo(event.getContact());
    }

    @AfterViews
    void setUpViews() {
        setUpMap();
        setUpRecyclerView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        showCompaniesLocation();
        showUserLocation();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactsAdapter.initAdapter();
        mRecyclerView.setAdapter(mContactsAdapter);
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
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void zoomToFitMarkers(List<LatLng> markerPositions) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng position : markerPositions) {
            builder.include(position);
        }
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 10);
        mGoogleMap.animateCamera(cu);
    }

    private void showSelectedContactInfo(Map.Entry<String,String> contact) {

        String info = String.format(
                Locale.getDefault(),
                StubConstants.CONTACT_INFO_FORMAT,
                contact.getKey(),contact.getValue());

        Toast.makeText(getContext(),info,Toast.LENGTH_LONG)
                .show();

    }

}
