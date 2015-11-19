package com.example.jaur.mvc_frag;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.util.GeoPoint;

import java.util.Observable;
import java.util.Observer;


/**
 * Fragment, welches die Map implementiert.
 *
 * @author jaur
 */
public class MapFragment extends Fragment implements Observer {

    private final static String TAG = "jaur6386MapFragment";
    private CustomMapView mapView;
    private LocationModel lm;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");
        this.lm = ((MainActivity) getActivity()).getLocationModel();
        lm.addObserver(this);
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //TODO: Jedes mal neu zu initialisieren ist ne Verschwendung von Zeit + Resourcen
        this.mapView = (CustomMapView) view.findViewById(R.id.mapView); //typecast auf custom map view
        mapView.setMultiTouchControls(true);
        mapView.setUseDataConnection(true);
        mapView.getController().setZoom(15);

        //erst wenn Daten vom Model geholt werden, wird der mapView auf das Ziel gezoomt

        return view;
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        lm.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "update: " + lm.getCurrentLocation());
        mapView.getController().setCenter(new GeoPoint(lm.getCurrentLocation().getLatitude(), lm.getCurrentLocation().getLongitude()));
    }
}
