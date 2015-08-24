package com.example.sifiso.tnbappsuites;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.listener.MarkerInfoWindowAdapterListener;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.util.Util;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TNBMapsActivity extends FragmentActivity {

    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    List<ReportedissueDTO> issueLogged;
    List<Marker> markers = new ArrayList<Marker>();
    private HashMap<Marker, ReportedissueDTO> mMarkersHashMap;
    private Context mCtx;
    private TextView MC_issues;
    private ProgressBar MC_progress;
    private ImageView MC_mapType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnbmaps);
        mCtx = getApplicationContext();
        mMarkersHashMap = new HashMap<Marker, ReportedissueDTO>();
        issueLogged = (List<ReportedissueDTO>) getIntent().getSerializableExtra("issueLogged");
        setUpMapIfNeeded();
        setFields();
    }

    private void setFields() {
        MC_issues = (TextView) findViewById(R.id.MC_issues);
        MC_progress = (ProgressBar) findViewById(R.id.MC_progress);
        MC_progress.setVisibility(View.GONE);
        MC_issues.setText(issueLogged.size() + "");
        MC_mapType = (ImageView) findViewById(R.id.MC_maptype);
        MC_mapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.flashOnce(MC_mapType, 300, new Util.UtilAnimationListener() {
                    @Override
                    public void onAnimationEnded() {
                        switch (googleMap.getMapType()) {
                            case GoogleMap.MAP_TYPE_NORMAL:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case GoogleMap.MAP_TYPE_SATELLITE:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case GoogleMap.MAP_TYPE_TERRAIN:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            case GoogleMap.MAP_TYPE_HYBRID:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                break;
                        }

                    }
                });
            }
        });
        MC_progress = (ProgressBar) findViewById(R.id.MC_progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        MarkerInfoWindowAdapterListener mListener;

        public MarkerInfoWindowAdapter(MarkerInfoWindowAdapterListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.on_view_map, null);

            final ReportedissueDTO myMarker = mMarkersHashMap.get(marker);
            ImageView ALI_logo = (ImageView) v.findViewById(R.id.ALI_logo);
            TextView ALI_status = (TextView) v.findViewById(R.id.ALI_status);
            TextView ALI_issueName = (TextView) v.findViewById(R.id.ALI_issueName);
            TextView AL_date = (TextView) v.findViewById(R.id.AL_date);
            int rID = mCtx.getResources().getIdentifier(myMarker.getIcon(), "drawable", mCtx.getPackageName());
            ALI_logo.setImageResource(rID);
            ALI_status.setText("Status: " + myMarker.getStatusreportedissueList().get(0).getStatusName());
            ALI_issueName.setText(myMarker.getIssueName());
            AL_date.setText(Util.getLongDate(new Date()));
            return v;
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
                setUpMap();
                setIssuesOnMap();
            }
        }
    }

    static String LOG = TNBMapsActivity.class.getSimpleName();

    private void setIssuesOnMap() {
        googleMap.clear();
        int index = 0;
        for (ReportedissueDTO es : issueLogged) {
            BitmapDescriptor desc = BitmapDescriptorFactory.fromResource(R.mipmap.status_icon);
            index++;
            Log.d(LOG, "" + index + " " + es.getMunicipalityID());
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(es.getLatitude(), es.getLongitude())).icon(desc)
                    .snippet(es.getReviews());
            //markerOptions.
            final Marker m = googleMap.addMarker(markerOptions);
            mMarkersHashMap.put(m, es);
            markers.add(m);
            googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(new MarkerInfoWindowAdapterListener() {
                @Override
                public void onMapView(ReportedissueDTO evaluation) {

                }
            }));
        }
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //ensure that all markers in bounds
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }

                LatLngBounds bounds = builder.build();
                int padding = 1; // offset from edges of the map in pixels

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                //   txtCount.setText("" + markers.size());
                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 1.0f));
                googleMap.animateCamera(cu);
            }
        });
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void setUpMap() {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
