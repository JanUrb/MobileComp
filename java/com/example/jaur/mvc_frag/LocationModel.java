package com.example.jaur.mvc_frag;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by jaur on 19.11.2015.
 *
 * Die Fragemnts implementieren Observer, registrieren sich wenn sie gestartet werden und deregistrieren sich wenn ihr life cycle über ist.
 */
public class LocationModel extends Observable {

    private static final String TAG = "jaur6386LocationModel";
    private List<Location> oldLocations;
    private Location currentLocation = null;

    public LocationModel() {
        Log.d(TAG, "LocationModel()");

        this.oldLocations = new ArrayList<>();
    }


    public float getSpeed() {
        Log.v(TAG, "getSpeed");
        if (!oldLocations.isEmpty()) { //wenn die liste nicht leer ist, also mehr als ein Punkt vorhanden (einer in der liste, einer in currentLocation)
            if (currentLocation.hasSpeed()) {
                return currentLocation.getSpeed();
            } else {

                //laut doku ist getTime() ungenau -> es wird empfohlen getElapsedRealTimeNanos() zu verwenden, welches aber API 17 erfordert
                long dtime = (currentLocation.getTime() - oldLocations.get(oldLocations.size() - 1).getTime()) / 1000; //time in sec
                float distance = currentLocation.distanceTo(oldLocations.get(oldLocations.size() - 1)); //entfernung in metern

                Log.d(TAG, "dTime: " + dtime + "\n" +
                                "curr getTime: " + currentLocation.getTime() + "\n" +
                                "oldLoc getTime: " + oldLocations.get(oldLocations.size() - 1).getTime() + "\n" +
                                "distanceTo: " + currentLocation.distanceTo(oldLocations.get(oldLocations.size() - 1)) + "\n" +
                                "speed: " + distance / dtime
                );

                return distance / dtime;
            }
        }
        return 0.0f;
    }

    public Location getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation");
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        Log.v(TAG, "setCurrentLocation");
        //Beim ersten Aufruf ist currentLocation null
        if (this.currentLocation != null) {
            oldLocations.add(this.currentLocation);
        }
        this.currentLocation = currentLocation;
        this.notifyObservers();

    }

    public float getCourse() {
        Log.v(TAG, "getCourse: " + currentLocation.getBearing());
        if (!oldLocations.isEmpty()) {
            if (currentLocation.hasBearing()) {
                return currentLocation.getBearing();
            } else {
                return currentLocation.bearingTo(oldLocations.get(oldLocations.size() - 1));
            }
        }
        return 0.0f;
    }

    /**
     * setChanged muss sonst immer in der setCurrentLocation Methode gesetzt werden.
     */
    @Override
    public void notifyObservers() {
        Log.v(TAG, "Current Observer Count: " + this.countObservers());
        this.setChanged();
        super.notifyObservers();
    }
}
