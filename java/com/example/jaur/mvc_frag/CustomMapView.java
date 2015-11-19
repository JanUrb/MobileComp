package com.example.jaur.mvc_frag;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import org.osmdroid.views.MapView;

/**
 * Created by jaur on 10.11.2015.
 */
public class CustomMapView extends MapView {

    private final static String TAG = "jaur6386CustomMapView";

    public CustomMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v(TAG, "CustomMapView()");
    }

    //TODO: Marker


}