package edu.ecu.cs.pirateplaces;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;


public class PirateMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMaps;
    private PirateBase base;
    private List<PiratePlace> mPiratePlaceList;
    private BitmapDescriptor iconFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pirate_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        base = PirateBase.getPirateBase(this);
        mPiratePlaceList = base.getPiratePlaces();
        iconFactory = BitmapDescriptorFactory.defaultMarker();
    }


    @Override
    public void onMapReady(GoogleMap googleMaps) {
        mMaps = googleMaps;
        mMaps.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            LatLngBounds.Builder mBuilder = new LatLngBounds.Builder();
            @Override
            public void onMapLoaded() {
                for (PiratePlace place: mPiratePlaceList){
                    LatLng location = new LatLng(place.getLatitude(), place.getLongitude());



                    mBuilder.include(location);

                    mMaps.addMarker(new MarkerOptions().icon(iconFactory).position(location));

                }
                LatLngBounds bounds = mBuilder.build();
                int margin = getResources().getDimensionPixelSize(R.dimen.location_bound);
                CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
                mMaps.animateCamera(update);

            }
        });
    }
}
