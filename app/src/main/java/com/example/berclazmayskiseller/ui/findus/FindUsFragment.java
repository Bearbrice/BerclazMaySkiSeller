package com.example.berclazmayskiseller.ui.findus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.berclazmayskiseller.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindUsFragment extends Fragment {

    GoogleMap mMap;

    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findus, container, false);

        Button button_verbier = view.findViewById(R.id.button_verbier);
        Button button_anzere = view.findViewById(R.id.button_anzere);

        //VERBIER BTN SETTINGS
        button_verbier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.container_frame_back);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        mMap.clear(); //clear old markers

                        //Smooth zoom, angle,...
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(46.093634, 7.233007))
                                .zoom(15)
                                .bearing(0)
                                .tilt(0)
                                .build();

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);

                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(46.093634, 7.233007))
                                .title("SkiSeller Verbier")
                                .snippet("079 328 65 48")
                                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_products_black_24dp)));
                        marker.showInfoWindow();
                    }
                });
            }
        });

        //ANZERE BTN SETTINGS
        button_anzere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.container_frame_back);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        mMap.clear(); //clear old markers

                        //Smooth zoom, angle,...
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(46.294862, 7.395471))
                                .zoom(15)
                                .bearing(0)
                                .tilt(0)
                                .build();

                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);

                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(46.294862, 7.395471))
                                .title("SkiSeller Anzère")
                                .snippet("079 328 65 48")
                                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_products_black_24dp)));
                        marker.showInfoWindow();
                    }
                });
            }
        });

        return view;
    }

    //MAP MARKER SETTINGS
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
